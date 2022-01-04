dependencies {
    implementation(project(":domain:domain-boundary"))
    implementation(project(":domain:domain-model"))
    implementation(project(":commons:commons-lang"))
    implementation(Dependencies.Exposed)
    implementation(Dependencies.Hikari)

    testImplementation(project(":domain:domain-model", "test"))
    testImplementation(Dependencies.H2)
}
