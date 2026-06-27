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
    val common = register("common")
    register("shadowCommon")
    compileClasspath.get().extendsFrom(common.get())
    runtimeClasspath.get().extendsFrom(common.get())
    named("developmentForge") { extendsFrom(common.get()) }
}

loom {
    accessWidenerPath.set(project(":Common").loom.accessWidenerPath)

    forge {
        convertAccessWideners(tasks.shadowJar, "biomeswevegone.accessWidener")
//        mixinConfig("biomeswevegone-common.mixins.json")
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

    "common"(project(":Common")) { isTransitive = false }
    "shadowCommon"(project(":Common", "transformProductionForge"))

    localRuntime("me.djtheredstoner:DevAuth-forge-latest:${providers.gradleProperty("devauth_version").get()}")

    api("com.github.glitchfiend:TerraBlender-forge:$minecraftVersion-${providers.gradleProperty("terrablender_version").get()}")
    api("dev.corgitaco.ohthetreesyoullgrow:ohthetreesyoullgrow-forge-$minecraftVersion:${providers.gradleProperty("ohthetreesyoullgrow_version").get()}")
    api("com.geckolib:geckolib-forge-$minecraftVersion:${providers.gradleProperty("geckolib_version").get()}")
    compileOnly("net.luckperms:api:5.4")

    compileOnly("mcp.mobius.waila:wthit-api:forge-${providers.gradleProperty("WTHIT").get()}")
    localRuntime("mcp.mobius.waila:wthit:forge-${providers.gradleProperty("WTHIT").get()}")
    localRuntime("lol.bai:badpackets:forge-${providers.gradleProperty("badPackets").get()}")
}

tasks {
    processResources {
        inputs.property("version", project.version)

        filesMatching("META-INF/mods.toml") {
            expand(mapOf("version" to project.version))
        }
    }

    jar.get().archiveClassifier.set("raw")

    shadowJar {
        dependsOn(jar)
        from(zipTree(jar.get().archiveFile))
        exclude("net/potionstudios/biomeswevegone/forge/datagen/**",
            "architectury.common.json", ".cache/**", "data/biomeswevegone/neoforge/**",
            "data/neoforge/**", "data/biomeswevegone/rs_pieces_spawn_counts/**", "data/biomeswevegone/rs_pool_additions/**",
            "data/biomeswevegone/structure/repurposed_structures/**", "data/biomeswevegone/worldgen/processor_list/repurposed_structures/**",
            "data/biomeswevegone/worldgen/template_pool/repurposed_structures/**")
        configurations = listOf(project.configurations.getByName("shadowCommon"))
        archiveClassifier.set(null)
    }
}

publisher {
    setLoaders(ModLoader.FORGE)
    val depends = mutableListOf("terrablender", "geckolib", "oh-the-trees-youll-grow")
    curseDepends.required.set(depends)
    modrinthDepends.required.set(depends)
    curseDepends.optional.set(mutableListOf("wthit-forge"))
}