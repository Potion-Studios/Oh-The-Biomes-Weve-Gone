architectury {
    common("fabric", "neoforge", "forge")
    platformSetupLoomIde()
}

val minecraftVersion = providers.gradleProperty("minecraft_version").get()

loom.accessWidenerPath.set(file("src/main/resources/biomeswevegone.accessWidener"))

sourceSets.main.get().resources.srcDir("src/main/generated/resources")

dependencies {
    implementation("net.fabricmc:fabric-loader:${providers.gradleProperty("fabric_loader_version").get()}")

    implementation("com.github.glitchfiend:TerraBlender-common:$minecraftVersion-${providers.gradleProperty("terrablender_version").get()}")
    implementation("dev.corgitaco.ohthetreesyoullgrow:ohthetreesyoullgrow-common-$minecraftVersion:${providers.gradleProperty("ohthetreesyoullgrow_version").get()}")
    implementation("com.geckolib:geckolib-common-$minecraftVersion:${providers.gradleProperty("geckolib_version").get()}")

    compileOnly("mcp.mobius.waila:wthit-api:fabric-${providers.gradleProperty("WTHIT").get()}")
}
