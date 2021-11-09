dependencies {
    implementation(project(":app"))
    implementation(Dependencies.Ktor.Client)
    // TODO outsource strings
    runtimeOnly("org.postgresql:postgresql:42.2.18")
    implementation("org.testcontainers:postgresql:1.16.2")
    implementation(project(":boundary:boundary-db", "test"))
}
