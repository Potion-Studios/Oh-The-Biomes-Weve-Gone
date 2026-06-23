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
        data()
        programArguments.addAll(
            "--all", "--mod", "biomeswevegone",
            "--output", project(":Common").file("src/main/generated/resources").absolutePath,
            "--existing", project(":Common").file("src/main/resources").absolutePath
        )
    }
}

dependencies {
    neoForge("net.neoforged:neoforge:${providers.gradleProperty("neoforge_version").get()}")

    "common"(project(":Common", "namedElements")) { isTransitive = false }
    "shadowBundle"(project(":Common", "transformProductionNeoForge"))

    modLocalRuntime("me.djtheredstoner:DevAuth-neoforge:${providers.gradleProperty("devauth_version").get()}")

    modApi("com.github.glitchfiend:TerraBlender-neoforge:$minecraftVersion-${providers.gradleProperty("terrablender_version").get()}")
    modApi("dev.corgitaco:Corgilib-NeoForge:$minecraftVersion-${providers.gradleProperty("corgilib_version").get()}")
    modApi("dev.corgitaco:Oh-The-Trees-Youll-Grow-neoforge:$minecraftVersion-${providers.gradleProperty("ohthetreesyoullgrow_version").get()}")
    modApi("software.bernie.geckolib:geckolib-neoforge-$minecraftVersion:${providers.gradleProperty("geckolib_version").get()}")
    compileOnly("net.luckperms:api:5.4")

    modCompileOnly("mcp.mobius.waila:wthit-api:neo-${providers.gradleProperty("WTHIT").get()}")
    modLocalRuntime("mcp.mobius.waila:wthit:neo-${providers.gradleProperty("WTHIT").get()}")
    modLocalRuntime("lol.bai:badpackets:neo-${providers.gradleProperty("badPackets").get()}")

    modApi("com.github.glitchfiend:SereneSeasons-neoforge:$minecraftVersion-10.1.0.3")

    modLocalRuntime("com.github.Jab125.architectury-data-generation-fix:architectury-data-generation-fix-neoforge:21.0.3")
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
        archiveClassifier.set("dev-shadow")
    }

    remapJar {
        inputFile.set(shadowJar.get().archiveFile)
        dependsOn(shadowJar)
        atAccessWideners.add("biomeswevegone.accesswidener")
    }
}

publisher {
    setLoaders(ModLoader.NEOFORGE)
    curseDepends.required.set(mutableListOf("terrablender-neoforge", "geckolib", "corgilib", "oh-the-trees-youll-grow"))
    modrinthDepends.required.set(mutableListOf("terrablender", "geckolib", "corgilib", "oh-the-trees-youll-grow"))
    curseDepends.optional.set(mutableListOf("wthit-forge"))
}