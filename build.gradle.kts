plugins {
    java
    `maven-publish`
}

group = "com.clayplug"
version = "0.0.4"

publishing {
    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/ClayplugLLC/OSBTree")
            credentials {
                username = System.getenv("USERNAME")
                password = System.getenv("TOKEN")
            }
        }
    }
    publications {
        register<MavenPublication>("gpr") {
            from(components["java"])
        }
    }
}
