import gg.essential.gradle.util.noServerRunConfigs

plugins {
    kotlin("jvm")
    id("gg.essential.multi-version")
    id("gg.essential.defaults")
}
tasks.withType<Jar> { duplicatesStrategy = DuplicatesStrategy.INHERIT }
group = "me.catto"
version = "1.0-SNAPSHOT"
base.archivesName.set("AutoOBS")
loom {
    noServerRunConfigs()
    launchConfigs {
        getByName("client") {
            arg("--tweakClass", "gg.essential.loader.stage0.EssentialSetupTweaker")
            arg("--tfisthis", "gg.essential.loader.stage0.EssentialSetupTweaker")
        }
    }
}

val embed by configurations.creating
configurations.implementation.get().extendsFrom(embed)

dependencies {
    compileOnly("gg.essential:essential-$platform:2666")
    embed ("io.obs-websocket.community:client:2.0.0")
    embed("ch.qos.logback:logback-classic:1.1.7")
    embed("gg.essential:loader-launchwrapper:1.1.3")
}

tasks.jar {
    duplicatesStrategy = DuplicatesStrategy.INHERIT
    from(embed.files.map { zipTree(it) })

    manifest.attributes(

        mapOf(

            "ModSide" to "CLIENT",
            "FMLCorePluginContainsFMLMod" to "Yes, yes it does",
            "TweakClass" to "gg.essential.loader.stage0.EssentialSetupTweaker",
            "TweakOrder" to "0"
        )
    )
}
tasks.withType<Jar> {
    duplicatesStrategy = DuplicatesStrategy.INCLUDE
}