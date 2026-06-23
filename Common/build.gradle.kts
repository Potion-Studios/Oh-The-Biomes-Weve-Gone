architectury {
    common("forge", "fabric", "neoforge")
    platformSetupLoomIde()
}

val minecraftVersion = providers.gradleProperty("minecraft_version").get()

loom.accessWidenerPath.set(file("src/main/resources/biomeswevegone.accesswidener"))

sourceSets.main.get().resources.srcDir("src/main/generated/resources")

dependencies {
    modImplementation("net.fabricmc:fabric-loader:${providers.gradleProperty("fabric_loader_version").get()}")

    modImplementation("com.github.glitchfiend:TerraBlender-common:$minecraftVersion-${providers.gradleProperty("terrablender_version").get()}")
    modImplementation("dev.corgitaco:Corgilib-Fabric:$minecraftVersion-${providers.gradleProperty("corgilib_version").get()}")
    modImplementation("dev.corgitaco:Oh-The-Trees-Youll-Grow-common:$minecraftVersion-${providers.gradleProperty("ohthetreesyoullgrow_version").get()}")
    modImplementation("software.bernie.geckolib:geckolib-common-$minecraftVersion:${providers.gradleProperty("geckolib_version").get()}")

    modCompileOnly("mcp.mobius.waila:wthit-api:fabric-${providers.gradleProperty("WTHIT").get()}")
}
