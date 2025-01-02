plugins {
    java
    `maven-publish`
}

group = "com.clayplug"
version = "0.0.9"

publishing {
    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/ClayplugLLC/OSBTree")
            credentials {
                username = System.getenv("GITHUB_ACTOR")
                password = System.getenv("GITHUB_TOKEN")
            }
        }
    }
    publications {
        register<MavenPublication>("gpr") {
            from(components["java"])
            artifactId = "osbtree"
        }
    }
}
