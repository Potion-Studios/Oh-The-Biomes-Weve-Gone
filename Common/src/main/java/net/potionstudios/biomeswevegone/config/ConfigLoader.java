package net.potionstudios.biomeswevegone.config;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import net.minecraft.resources.ResourceLocation;
import net.potionstudios.biomeswevegone.BiomesWeveGone;
import net.potionstudios.biomeswevegone.PlatformHandler;
import org.jetbrains.annotations.NotNull;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Map;

/**
 * Makes or loads a config file
 * @see Gson
 * @author Joseph T. McQuigg
 */
public class ConfigLoader {
	/** The Gson instance for the Config Loader. */
	private static final Gson GSON = new GsonBuilder()
			.setPrettyPrinting().
			registerTypeHierarchyAdapter(ResourceLocation.class, new TypeAdapter<ResourceLocation>() {
				@Override
				public void write(JsonWriter out, ResourceLocation value) throws java.io.IOException {
					out.value(value == null ? null : value.toString());
				}

				@Override
				public ResourceLocation read(JsonReader in) throws java.io.IOException {
					return ResourceLocation.parse(in.nextString());
				}
			})
			.create();

	/**
	 * Loads or Creates a config file
	 *
	 * @param clazz      The class of the config file.
	 * @return The config file.
	 */
	public static <T> T loadConfig(@NotNull Class<T> clazz, String name) {
		try {
			Path configPath = PlatformHandler.PLATFORM_HANDLER.configPath().resolve(name + ".json");
			Path legacyPath = PlatformHandler.PLATFORM_HANDLER.configPath().resolve(name + ".json5");

			T defaultValue = clazz.getConstructor().newInstance();

			if (Files.exists(legacyPath) && Files.notExists(configPath)) {
				try {
					migrateLegacyConfig(legacyPath, configPath, GSON.toJsonTree(defaultValue).getAsJsonObject());
				} catch (Exception e) {
					BiomesWeveGone.LOGGER.error(Arrays.toString(e.getStackTrace()));
				}
			}

			try {
				Files.deleteIfExists(legacyPath);
			} catch (Exception ignored) {}

			if (Files.notExists(configPath)) {
				Files.createDirectories(configPath.getParent());
				Files.writeString(configPath, GSON.toJson(defaultValue));
				return defaultValue;
			}

			JsonObject userJson;
			try (Reader reader = Files.newBufferedReader(configPath)) {
				JsonElement parsed = JsonParser.parseReader(reader);
				userJson = parsed.isJsonObject() ? parsed.getAsJsonObject() : new JsonObject();
			} catch (Exception e) {
				userJson = new JsonObject();
			}

			JsonObject defaultJson = GSON.toJsonTree(defaultValue).getAsJsonObject();

			merge(defaultJson, userJson);

			Files.writeString(configPath, GSON.toJson(defaultJson));

			return GSON.fromJson(defaultJson, clazz);
		} catch (Exception e) {
			throw new RuntimeException("Failed to load config " + name + ".", e);
		}
	}

	/**
	 * Reads the legacy JSON5 file, sanitizes it into strict JSON, maps old key variants
	 * to the new config structure, and merges them on top of the defaults.
	 * * TODO: Remove later
	 */
	@Deprecated(forRemoval = true, since = "2.6.0")
	private static void migrateLegacyConfig(Path legacyPath, Path newConfigPath, JsonObject defaultJson) throws Exception {
		String json5Content = Files.readString(legacyPath);

		String cleanJson = json5Content
				.replaceAll("//.*", "")
				.replaceAll(",\\s*([}\\]])", "$1");

		JsonElement parsed = JsonParser.parseString(cleanJson);
		if (!parsed.isJsonObject()) return;
		JsonObject legacyJson = parsed.getAsJsonObject();

		JsonObject mappedJson = new JsonObject();

		if (legacyJson.has("enabled_biomes")) {
			mappedJson.add("biomes", legacyJson.get("enabled_biomes"));
		}

		if (legacyJson.has("region_weight")) {
			JsonElement weight = legacyJson.get("region_weight");
			mappedJson.add("region_1_weight", weight);
			mappedJson.add("region_2_weight", weight);
			mappedJson.add("region_3_weight", weight);
		}

		if (legacyJson.has("vanilla_additions")) {
			JsonObject vanillaAdditionsWrapper = new JsonObject();
			if (defaultJson.has("vanilla_additions") && defaultJson.get("vanilla_additions").isJsonObject())
				vanillaAdditionsWrapper.add("comment", defaultJson.getAsJsonObject("vanilla_additions").get("comment"));
			vanillaAdditionsWrapper.add("value", legacyJson.get("vanilla_additions"));
			mappedJson.add("vanilla_additions", vanillaAdditionsWrapper);
		}

		if (legacyJson.has("enabled_vanilla_additions") && legacyJson.get("enabled_vanilla_additions").isJsonObject()) {
			JsonObject legacyFeatures = legacyJson.getAsJsonObject("enabled_vanilla_additions");
			JsonObject translatedFeatures = new JsonObject();

			for (Map.Entry<String, JsonElement> featureEntry : legacyFeatures.entrySet()) {
				String featureKey = featureEntry.getKey();
				JsonObject featureWrapper = new JsonObject();

				if (defaultJson.has("individual_vanilla_additions")) {
					JsonObject defFeatures = defaultJson.getAsJsonObject("individual_vanilla_additions");
					if (defFeatures.has(featureKey) && defFeatures.get(featureKey).isJsonObject()) {
						featureWrapper.add("comment", defFeatures.getAsJsonObject(featureKey).get("comment"));
					}
				}
				featureWrapper.add("value", featureEntry.getValue());
				translatedFeatures.add(featureKey, featureWrapper);
			}
			mappedJson.add("individual_vanilla_additions", translatedFeatures);
		}

		merge(defaultJson, mappedJson);
		Files.createDirectories(newConfigPath.getParent());
		Files.writeString(newConfigPath, GSON.toJson(defaultJson));
	}

	private static void merge(JsonObject defaultJson, JsonObject userJson) {
		if (defaultJson.has("comment") && defaultJson.has("value")) {
			if (userJson.has("value")) {
				JsonElement userVal = userJson.get("value");
				JsonElement defaultVal = defaultJson.get("value");

				if (defaultVal.isJsonObject() && userVal.isJsonObject()) {
					merge(defaultVal.getAsJsonObject(), userVal.getAsJsonObject());
				} else if (defaultVal.isJsonObject() == userVal.isJsonObject() &&
						defaultVal.isJsonArray() == userVal.isJsonArray() &&
						defaultVal.isJsonPrimitive() == userVal.isJsonPrimitive()) {
					defaultJson.add("value", userVal);
				}
			}
			return;
		}

		for (Map.Entry<String, JsonElement> entry : userJson.entrySet()) {
			String key = entry.getKey();
			JsonElement userValue = entry.getValue();

			if (defaultJson.has(key)) {
				JsonElement defaultElement = defaultJson.get(key);

				if (defaultElement.isJsonObject() != userValue.isJsonObject() ||
						defaultElement.isJsonArray() != userValue.isJsonArray() ||
						defaultElement.isJsonPrimitive() != userValue.isJsonPrimitive()) {
					continue;
				}

				if (defaultElement.isJsonObject())
					merge(defaultElement.getAsJsonObject(), userValue.getAsJsonObject());
				else defaultJson.add(key, userValue);
			}
		}
	}
}