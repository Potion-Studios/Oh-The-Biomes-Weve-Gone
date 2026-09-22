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
    val common = register("common")
    register("shadowCommon")
    compileClasspath.get().extendsFrom(common.get())
    runtimeClasspath.get().extendsFrom(common.get())
    named("developmentNeoForge") { extendsFrom(common.get()) }
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

    neoForge.convertAccessWideners(tasks.shadowJar, "biomeswevegone.accessWidener")
}

dependencies {
    neoForge("net.neoforged:neoforge:${providers.gradleProperty("neoforge_version").get()}")

    "common"(project(":Common")) { isTransitive = false }
    "shadowCommon"(project(":Common", "transformProductionNeoForge"))

    localRuntime("me.djtheredstoner:DevAuth-neoforge:${providers.gradleProperty("devauth_version").get()}")

    api("com.github.glitchfiend:TerraBlender-neoforge:$minecraftVersion-${providers.gradleProperty("terrablender_version").get()}")
    api("dev.corgitaco.ohthetreesyoullgrow:ohthetreesyoullgrow-neoforge-$minecraftVersion:${providers.gradleProperty("ohthetreesyoullgrow_version").get()}")
    api("com.geckolib:geckolib-neoforge-$minecraftVersion:${providers.gradleProperty("geckolib_version").get()}")
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

    jar.get().archiveClassifier.set("raw")

    shadowJar {
        dependsOn(jar)
        from(zipTree(jar.get().archiveFile))
        exclude("net/potionstudios/biomeswevegone/neoforge/datagen/**",
            "architectury.common.json", ".cache/**")
        configurations = listOf(project.configurations.getByName("shadowCommon"))
        archiveClassifier.set(null)
    }
}

publisher {
    setLoaders(ModLoader.NEOFORGE)
    curseDepends.required.set(mutableListOf("terrablender-neoforge", "geckolib", "oh-the-trees-youll-grow"))
    modrinthDepends.required.set(mutableListOf("terrablender", "geckolib", "oh-the-trees-youll-grow"))
    curseDepends.optional.set(mutableListOf("wthit-forge"))
}