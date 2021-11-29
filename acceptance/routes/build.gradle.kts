dependencies {
    implementation(project(":commons:commons-ktor"))
    implementation(project(":boundary:boundary-db"))
    implementation(project(":boundary:boundary-db", "test"))
    implementation(Dependencies.Exposed)

    implementation(Dependencies.Ktor.ServerCore)
}
