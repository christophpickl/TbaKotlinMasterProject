dependencies {
    implementation(project(":api-model"))
    implementation(project(":domain-model"))
    implementation(project(":domain-logic"))
    
    implementation(Dependencies.Ktor.ServerNetty)
    implementation(Dependencies.Koin.Ktor)
    implementation(Dependencies.Moshi.Moshi)
    implementation(Dependencies.Moshi.Kotlin)
    implementation(Dependencies.Moshi.Ktor)
    implementation(Dependencies.Logging.Logback)
    
    testImplementation(Dependencies.Ktor.ServerTestHost)
    testImplementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.13.+")
}
