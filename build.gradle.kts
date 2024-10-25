plugins {
    id("net.labymod.labygradle")
    id("net.labymod.labygradle.addon")
}

val versions = providers.gradleProperty("net.labymod.minecraft-versions").get().split(";")

group = "de.corvonn"
version = providers.environmentVariable("VERSION").getOrElse("1.0.6")

labyMod {
    defaultPackageName = "de.corvonn.nopumpkinblur"

    minecraft {
        registerVersion(versions.toTypedArray()) {
            runs {
                getByName("client") {
                    // When the property is set to true, you can log in with a Minecraft account
                    // devLogin = true
                }
            }
        }
    }

    addonInfo {
        namespace = "nopumpkinblur"
        displayName = "No Pumpkin Blur"
        author = "Corvonn"
        description = "Removes or reduces the annoying pumpkin blur effect when wearing a pumpkin on your head."
        minecraftVersion = "1.8.9<1.21.3"
        version = rootProject.version.toString()
    }
}

subprojects {
    plugins.apply("net.labymod.labygradle")
    plugins.apply("net.labymod.labygradle.addon")

    group = rootProject.group
    version = rootProject.version
}