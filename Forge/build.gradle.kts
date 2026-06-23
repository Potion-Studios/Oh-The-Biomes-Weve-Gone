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

        mixinConfigs("biomeswevegone-common.mixins.json", "biomeswevegone.mixins.json")
    }

    runs.create("datagen") {
        data()
        programArguments.addAll(
            "--all", "--mod", "biomeswevegone",
            "--output", file("src/main/generated/resources").absolutePath,
            "--existing", file("src/main/resources").absolutePath
        )
    }
}

dependencies {
    forge("net.minecraftforge:forge:$minecraftVersion-${providers.gradleProperty("forge_version").get()}")

    "common"(project(":Common", "namedElements")) { isTransitive = false }
    "shadowBundle"(project(":Common", "transformProductionForge"))

    modLocalRuntime("me.djtheredstoner:DevAuth-forge-latest:${providers.gradleProperty("devauth_version").get()}")

    modApi("com.github.glitchfiend:TerraBlender-forge:$minecraftVersion-${providers.gradleProperty("terrablender_version").get()}")
    modApi("dev.corgitaco:Corgilib-Forge:$minecraftVersion-${providers.gradleProperty("corgilib_version").get()}")
    modApi("dev.corgitaco:Oh-The-Trees-Youll-Grow-forge:$minecraftVersion-${providers.gradleProperty("ohthetreesyoullgrow_version").get()}")
    modApi("software.bernie.geckolib:geckolib-forge-$minecraftVersion:${providers.gradleProperty("geckolib_version").get()}")
    compileOnly("net.luckperms:api:5.4")

    modCompileOnly("mcp.mobius.waila:wthit-api:forge-${providers.gradleProperty("WTHIT").get()}")
    modLocalRuntime("mcp.mobius.waila:wthit:forge-${providers.gradleProperty("WTHIT").get()}")
    modLocalRuntime("lol.bai:badpackets:forge-${providers.gradleProperty("badPackets").get()}")
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
            "architectury.common.json", ".cache/**", "data/biomeswevegone/neoforge/**",
            "data/neoforge/**", "data/biomeswevegone/rs_pieces_spawn_counts/**", "data/biomeswevegone/rs_pool_additions/**",
            "data/biomeswevegone/structure/repurposed_structures/**", "data/biomeswevegone/worldgen/processor_list/repurposed_structures/**",
            "data/biomeswevegone/worldgen/template_pool/repurposed_structures/**")
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