architectury {
    common("forge", "fabric", "neoforge")
    platformSetupLoomIde()
}

val minecraftVersion = project.properties["minecraft_version"] as String

loom.accessWidenerPath.set(file("src/main/resources/biomeswevegone.accesswidener"))

sourceSets.main.get().resources.srcDir("src/main/generated/resources")

dependencies {
    modImplementation("net.fabricmc:fabric-loader:${project.properties["fabric_loader_version"]}")

    modImplementation("com.github.glitchfiend:TerraBlender-common:$minecraftVersion-${project.properties["terrablender_version"]}")
    modImplementation("dev.corgitaco:Corgilib-Fabric:$minecraftVersion-${project.properties["corgilib_version"]}")
    modImplementation("dev.corgitaco:Oh-The-Trees-Youll-Grow-common:$minecraftVersion-${project.properties["ohthetreesyoullgrow_version"]}")
    modImplementation("software.bernie.geckolib:geckolib-common-$minecraftVersion:${project.properties["geckolib_version"]}")

    modCompileOnly("mcp.mobius.waila:wthit-api:fabric-${project.properties["WTHIT"]}")
}
