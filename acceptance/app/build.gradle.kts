plugins {
    application
    id("com.google.cloud.tools.jib") version Versions.Plugins.Jib
}

dependencies {
    implementation(project(":acceptance:routes"))
    implementation(project(":commons:commons-lang"))
    implementation(project(":commons:commons-ktor"))
    implementation(project(":boundary:boundary-db"))

    implementation(Dependencies.Exposed)
    implementation(Dependencies.Ktor.ServerNetty)
    implementation(Dependencies.Koin.Ktor)
    implementation(Dependencies.Moshi.Moshi)
    implementation(Dependencies.Moshi.Kotlin)
    implementation(Dependencies.Moshi.Ktor)
    implementation(Dependencies.Logging.Logback)
}

val appMainClass = "com.github.christophpickl.tbakotlinmasterproject.acceptance.app.AcceptanceApp"
val buildId = "v1.0"

application {
    mainClass.set(appMainClass)
}

jib {
    from {
        image = "gcr.io/distroless/java:11-debug"
    }
    container {
        ports = listOf("80")
        mainClass = appMainClass
        jvmFlags = listOf(
            "-server",
            "-Djava.awt.headless=true",
        )
    }
    to {
        image = "kmaster/acceptance-app"
        tags = setOf(buildId)
    }
}
