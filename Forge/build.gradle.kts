import com.hypherionmc.modpublisher.properties.ModLoader

plugins {
    id("com.gradleup.shadow")
}

architectury {
    platformSetupLoomIde()
    forge()
}

val minecraftVersion = providers.gradleProperty("minecraft_version").get()

configurations {
    create("common")
    "common" {
        isCanBeResolved = true
        isCanBeConsumed = false
    }
    create("shadowBundle")
    compileClasspath.get().extendsFrom(configurations["common"])
    runtimeClasspath.get().extendsFrom(configurations["common"])
    getByName("developmentForge").extendsFrom(configurations["common"])
    "shadowBundle" {
        isCanBeResolved = true
        isCanBeConsumed = false
    }
}

loom {
    accessWidenerPath.set(project(":Common").loom.accessWidenerPath)

    forge {
        convertAccessWideners.set(true)
        extraAccessWideners.add(loom.accessWidenerPath.get().asFile.name)

        mixinConfig("biomeswevegone-common.mixins.json")
    }

    runs.create("datagen") {
        data()
        programArguments.addAll(
            "--all", "--mod", "biomeswevegone",
            "--output", project(":Common").file("src/main/generated/resources").absolutePath,
            "--existing", project(":Common").file("src/main/resources").absolutePath
        )
    }
}

dependencies {
    forge("net.minecraftforge:forge:$minecraftVersion-${providers.gradleProperty("forge_version").get()}")

    "common"(project(":Common", "namedElements")) { isTransitive = false }
    "shadowBundle"(project(":Common", "transformProductionForge"))

    modLocalRuntime("me.djtheredstoner:DevAuth-forge-latest:${providers.gradleProperty("devauth_version").get()}")  { isTransitive = false }

    implementation("com.eliotlash.mclib:mclib:20")
    forgeRuntimeLibrary("com.eliotlash.mclib:mclib:20")
    modApi("com.github.glitchfiend:TerraBlender-forge:$minecraftVersion-${providers.gradleProperty("terrablender_version").get()}")
    modApi("dev.corgitaco:Corgilib-Forge:$minecraftVersion-${providers.gradleProperty("corgilib_version").get()}")
    modApi("dev.corgitaco:Oh-The-Trees-Youll-Grow-forge:$minecraftVersion-${providers.gradleProperty("ohthetreesyoullgrow_version").get()}")
    modApi("software.bernie.geckolib:geckolib-forge-$minecraftVersion:${providers.gradleProperty("geckolib_version").get()}")

    modCompileOnly("mcp.mobius.waila:wthit-api:forge-${providers.gradleProperty("WTHIT").get()}")
    modLocalRuntime("mcp.mobius.waila:wthit:forge-${providers.gradleProperty("WTHIT").get()}")
    modLocalRuntime("lol.bai:badpackets:forge-${providers.gradleProperty("badPackets").get()}")

    modLocalRuntime("maven.modrinth:cyanide:4.1.1")

    modApi("com.github.glitchfiend:SereneSeasons-forge:$minecraftVersion-9.1.0.3")
    modLocalRuntime("com.github.glitchfiend:GlitchCore-forge:$minecraftVersion-0.0.1.1")

    compileOnly("io.github.llamalad7:mixinextras-common:0.5.5")?.let { annotationProcessor(it) }
    include("io.github.llamalad7:mixinextras-forge:0.5.5")?.let { implementation(it) }
}

tasks {
    processResources {
        inputs.property("version", project.version)

        filesMatching("META-INF/mods.toml") {
            expand(mapOf("version" to project.version))
        }
    }

    shadowJar {
        exclude("net/potionstudios/biomeswevegone/forge/datagen/**",
            "architectury.common.json", ".cache/**")
        configurations = listOf(project.configurations.getByName("shadowBundle"))
        archiveClassifier.set("dev-shadow")
    }

    remapJar {
        inputFile.set(shadowJar.get().archiveFile)
        dependsOn(shadowJar)
    }
}

publisher {
    setLoaders(ModLoader.FORGE)
    val depends = mutableListOf("terrablender", "geckolib", "corgilib", "oh-the-trees-youll-grow")
    curseDepends.required.set(depends)
    modrinthDepends.required.set(depends)
    curseDepends.optional.set(mutableListOf("wthit-forge"))
}