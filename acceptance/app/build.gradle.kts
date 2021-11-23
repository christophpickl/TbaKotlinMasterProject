dependencies {
    implementation(project(":acceptance:routes"))
    implementation(project(":commons:commons-ktor"))

    implementation(Dependencies.Ktor.ServerNetty)
    implementation(Dependencies.Koin.Ktor)
    implementation(Dependencies.Moshi.Moshi)
    implementation(Dependencies.Moshi.Kotlin)
    implementation(Dependencies.Moshi.Ktor)
}
