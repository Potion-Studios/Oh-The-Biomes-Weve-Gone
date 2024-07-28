import com.hypherionmc.modpublisher.properties.CurseEnvironment
import com.hypherionmc.modpublisher.properties.ModLoader
import com.hypherionmc.modpublisher.properties.ReleaseType

plugins {
    id("com.github.johnrengelman.shadow") version "8.1.1"
    id("com.hypherionmc.modutils.modpublisher") version "2.+"
}

architectury {
    platformSetupLoomIde()
    fabric()
}

val minecraftVersion = project.properties["minecraft_version"] as String
val jarName = base.archivesName.get() + "-Fabric"

configurations {
    create("common")
    create("shadowCommon")
    compileClasspath.get().extendsFrom(configurations["common"])
    runtimeClasspath.get().extendsFrom(configurations["common"])
    getByName("developmentFabric").extendsFrom(configurations["common"])
}

loom.accessWidenerPath.set(project(":Common").loom.accessWidenerPath)

fabricApi.configureDataGeneration()

dependencies {
    modImplementation("net.fabricmc:fabric-loader:${project.properties["fabric_loader_version"]}")
    modApi("net.fabricmc.fabric-api:fabric-api:${project.properties["fabric_api_version"]}+$minecraftVersion")

    "common"(project(":Common", "namedElements")) { isTransitive = false }
    "shadowCommon"(project(":Common", "transformProductionFabric")) { isTransitive = false }

    modRuntimeOnly("me.djtheredstoner:DevAuth-fabric:${project.properties["devauth_version"]}")

    modApi("com.github.glitchfiend:TerraBlender-fabric:$minecraftVersion-${project.properties["terrablender_version"]}")
    modApi("corgitaco.corgilib:corgilib-fabric:$minecraftVersion-${project.properties["corgilib_version"]}")
    modApi("dev.corgitaco:Oh-The-Trees-Youll-Grow-fabric:$minecraftVersion-${project.properties["ohthetreesyoullgrow_version"]}")
    modApi("software.bernie.geckolib:geckolib-fabric-$minecraftVersion:${project.properties["geckolib_version"]}")

    modCompileOnly("mcp.mobius.waila:wthit-api:fabric-${project.properties["WTHIT"]}")
    modRuntimeOnly("mcp.mobius.waila:wthit:fabric-${project.properties["WTHIT"]}")
    modRuntimeOnly("lol.bai:badpackets:fabric-${project.properties["badPackets"]}")

    modRuntimeOnly("maven.modrinth:cyanide:jCWZy4Gl")
}

tasks {
    base.archivesName.set(jarName)
    processResources {
        inputs.property("version", project.version)

        filesMatching("fabric.mod.json") {
            expand(mapOf("version" to project.version))
        }
    }

    shadowJar {
        exclude("net/potionstudios/biomeswevegone/fabric/datagen/**",
            "architectury.common.json", ".cache/**", "data/biomeswevegone/forge/**",
            "data/forge/**")
        configurations = listOf(project.configurations.getByName("shadowCommon"))
        archiveClassifier.set("dev-shadow")
    }

    remapJar {
        injectAccessWidener.set(true)
        inputFile.set(shadowJar.get().archiveFile)
        dependsOn(shadowJar)
    }

    jar.get().archiveClassifier.set("dev")

    sourcesJar {
        val commonSources = project(":Common").tasks.sourcesJar
        dependsOn(commonSources)
        from(commonSources.get().archiveFile.map { zipTree(it) })
    }
}

components {
    java.run {
        if (this is AdhocComponentWithVariants)
            withVariantsFromConfiguration(project.configurations.shadowRuntimeElements.get()) { skip() }
    }
}

publisher {
    apiKeys {
        curseforge(getPublishingCredentials().first)
        modrinth(getPublishingCredentials().second)
        github(project.properties["github_token"].toString())
    }

    curseID.set("1070751")
    modrinthID.set("NTi7d3Xc")
    githubRepo.set("https://github.com/Potion-Studios/Oh-The-Biomes-Weve-Gone")
    setReleaseType(ReleaseType.BETA)
    projectVersion.set(project.version.toString())
    displayName.set("$jarName-${projectVersion.get()}")
    changelog.set(projectDir.toPath().parent.resolve("CHANGELOG.md").toFile().readText())
    artifact.set(tasks.remapJar)
    setGameVersions(minecraftVersion)
    setLoaders(ModLoader.FABRIC, ModLoader.QUILT)
    setCurseEnvironment(CurseEnvironment.BOTH)
    setJavaVersions(JavaVersion.VERSION_17, JavaVersion.VERSION_18, JavaVersion.VERSION_19, JavaVersion.VERSION_20, JavaVersion.VERSION_21, JavaVersion.VERSION_22)
    val depends = mutableListOf("fabric-api", "terrablender-fabric", "geckolib", "corgilib", "oh-the-trees-youll-grow")
    val softDepends = mutableListOf("wthit")
    curseDepends.required.set(depends)
    curseDepends.optional.set(softDepends)
    modrinthDepends.required.set(depends)
    modrinthDepends.optional.set(softDepends)
}

publishing {
    publications.create<MavenPublication>("mavenFabric") {
        artifactId = jarName
        from(components["java"])
    }

    repositories {
        mavenLocal()
        maven {
            val releasesRepoUrl = "https://maven.jt-dev.tech/releases"
            val snapshotsRepoUrl = "https://maven.jt-dev.tech/snapshots"
            url = uri(if (project.version.toString().endsWith("SNAPSHOT") || project.version.toString().startsWith("0")) snapshotsRepoUrl else releasesRepoUrl)
            name = "JTDev-Maven-Repository"
            credentials {
                username = project.properties["repoLogin"]?.toString()
                password = project.properties["repoPassword"]?.toString()
            }
        }
    }
}

private fun getPublishingCredentials(): Pair<String?, String?> {
    val curseForgeToken = (project.findProperty("curseforge_token") ?: System.getenv("CURSEFORGE_TOKEN") ?: "") as String?
    val modrinthToken = (project.findProperty("modrinth_token") ?: System.getenv("MODRINTH_TOKEN") ?: "") as String?
    return Pair(curseForgeToken, modrinthToken)
}