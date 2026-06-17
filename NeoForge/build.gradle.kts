import com.hypherionmc.modpublisher.properties.ModLoader

plugins {
    id("com.gradleup.shadow")
}

architectury {
    platformSetupLoomIde()
    neoForge()
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
    getByName("developmentNeoForge").extendsFrom(configurations["common"])
    "shadowBundle" {
        isCanBeResolved = true
        isCanBeConsumed = false
    }
}

loom {
    accessWidenerPath.set(project(":Common").loom.accessWidenerPath)

    runs.create("datagen") {
        clientData()
        programArguments.addAll(
            "--all", "--mod", "biomeswevegone",
            "--output", project(":Common").file("src/main/generated/resources").absolutePath,
            "--existing", project(":Common").file("src/main/resources").absolutePath
        )
    }
}

dependencies {
    neoForge("net.neoforged:neoforge:${providers.gradleProperty("neoforge_version").get()}")

    "common"(project(":Common")) { isTransitive = false }
    "shadowBundle"(project(":Common", "transformProductionNeoForge"))

    localRuntime("me.djtheredstoner:DevAuth-neoforge:${providers.gradleProperty("devauth_version").get()}")

    api("com.github.glitchfiend:TerraBlender-neoforge:$minecraftVersion-${providers.gradleProperty("terrablender_version").get()}")
    api("dev.corgitaco.ohthetreesyoullgrow:ohthetreesyoullgrow-common-26.1:${providers.gradleProperty("ohthetreesyoullgrow_version").get()}")
    api("software.bernie.geckolib:geckolib-neoforge-$minecraftVersion:${providers.gradleProperty("geckolib_version").get()}")
    compileOnly("net.luckperms:api:5.4")

    compileOnly("mcp.mobius.waila:wthit-api:neo-${providers.gradleProperty("WTHIT").get()}")
    localRuntime("mcp.mobius.waila:wthit:neo-${providers.gradleProperty("WTHIT").get()}")
    localRuntime("lol.bai:badpackets:neo-${providers.gradleProperty("badPackets").get()}")

    api("com.github.glitchfiend:SereneSeasons-neoforge:$minecraftVersion-26.1.2.0.3")
}

tasks {
    processResources {
        inputs.property("version", project.version)

        filesMatching("META-INF/neoforge.mods.toml") {
            expand(mapOf("version" to project.version))
        }
    }

    shadowJar {
        exclude("net/potionstudios/biomeswevegone/neoforge/datagen/**",
            "architectury.common.json", ".cache/**")
        configurations = listOf(project.configurations.getByName("shadowBundle"))
        archiveClassifier.set(null)
    }
}

publisher {
    setLoaders(ModLoader.NEOFORGE)
    curseDepends.required.set(mutableListOf("terrablender-neoforge", "geckolib", "corgilib", "oh-the-trees-youll-grow"))
    modrinthDepends.required.set(mutableListOf("terrablender", "geckolib", "corgilib", "oh-the-trees-youll-grow"))
    curseDepends.optional.set(mutableListOf("wthit-forge"))
}