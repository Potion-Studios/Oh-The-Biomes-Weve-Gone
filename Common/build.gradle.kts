architectury {
    common("forge", "fabric")
    platformSetupLoomIde()
}

val minecraftVersion = project.properties["minecraft_version"] as String

loom.accessWidenerPath.set(file("src/main/resources/biomeswevegone.accesswidener"))

sourceSets.main.get().resources.srcDir("src/main/generated/resources")

dependencies {
    // We depend on fabric loader here to use the fabric @Environment annotations and get the mixin dependencies
    // Do NOT use other classes from fabric loader
    modImplementation("net.fabricmc:fabric-loader:${project.properties["fabric_loader_version"]}")

    modCompileOnly("com.github.glitchfiend:TerraBlender-common:$minecraftVersion-${project.properties["terrablender_version"]}")
    modCompileOnly("corgitaco.corgilib:corgilib-fabric:$minecraftVersion-${project.properties["corgilib_version"]}")
    modCompileOnly("dev.corgitaco:Oh-The-Trees-Youll-Grow-common:$minecraftVersion-${project.properties["ohthetreesyoullgrow_version"]}")
    modCompileOnly("software.bernie.geckolib:geckolib-fabric-$minecraftVersion:${project.properties["geckolib_version"]}")

    modCompileOnly("mcp.mobius.waila:wthit-api:fabric-${project.properties["WTHIT"]}")
}

publishing {
    publications.create<MavenPublication>("mavenCommon") {
        artifactId = "${project.properties["archives_base_name"]}" + "-Common"
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
