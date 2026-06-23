import com.hypherionmc.modpublisher.properties.ModLoader

plugins {
    id("com.gradleup.shadow")
}

architectury {
    platformSetupLoomIde()
    fabric()
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
    getByName("developmentFabric").extendsFrom(configurations["common"])
    "shadowBundle" {
        isCanBeResolved = true
        isCanBeConsumed = false
    }
}

loom.accessWidenerPath.set(project(":Common").loom.accessWidenerPath)

dependencies {
    modImplementation("net.fabricmc:fabric-loader:${providers.gradleProperty("fabric_loader_version").get()}")
    modApi("net.fabricmc.fabric-api:fabric-api:${providers.gradleProperty("fabric_api_version").get()}+$minecraftVersion")

    "common"(project(":Common", "namedElements")) { isTransitive = false }
    "shadowBundle"(project(":Common", "transformProductionFabric"))

    modLocalRuntime("me.djtheredstoner:DevAuth-fabric:${providers.gradleProperty("devauth_version").get()}")

    modApi("com.github.glitchfiend:TerraBlender-fabric:$minecraftVersion-${providers.gradleProperty("terrablender_version").get()}")
    modApi("dev.corgitaco:Corgilib-Fabric:$minecraftVersion-${providers.gradleProperty("corgilib_version").get()}")
    modApi("dev.corgitaco:Oh-The-Trees-Youll-Grow-fabric:$minecraftVersion-${providers.gradleProperty("ohthetreesyoullgrow_version").get()}")
    modApi("software.bernie.geckolib:geckolib-fabric-$minecraftVersion:${providers.gradleProperty("geckolib_version").get()}")
    modApi("me.lucko:fabric-permissions-api:0.3.1")

    modCompileOnly("mcp.mobius.waila:wthit-api:fabric-${providers.gradleProperty("WTHIT").get()}")
    modLocalRuntime("mcp.mobius.waila:wthit:fabric-${providers.gradleProperty("WTHIT").get()}")
    modLocalRuntime("lol.bai:badpackets:fabric-${providers.gradleProperty("badPackets").get()}")
}

tasks {
    processResources {
        inputs.property("version", project.version)

        filesMatching("fabric.mod.json") {
            expand(mapOf("version" to project.version))
        }
    }

    shadowJar {
        exclude("net/potionstudios/biomeswevegone/fabric/datagen/**",
            "architectury.common.json", ".cache/**", "data/biomeswevegone/neoforge/**",
            "data/neoforge/**")
        configurations = listOf(project.configurations.getByName("shadowBundle"))
        archiveClassifier.set("dev-shadow")
    }

    remapJar {
        injectAccessWidener.set(true)
        inputFile.set(shadowJar.get().archiveFile)
        dependsOn(shadowJar)
    }
}

publisher {
    setLoaders(ModLoader.FABRIC, ModLoader.QUILT)
    curseDepends.required.set(mutableListOf("fabric-api", "terrablender-fabric", "geckolib", "corgilib", "oh-the-trees-youll-grow"))
    modrinthDepends.required.set(mutableListOf("fabric-api", "terrablender", "geckolib", "corgilib", "oh-the-trees-youll-grow"))
    curseDepends.optional.set(mutableListOf("wthit"))
}
