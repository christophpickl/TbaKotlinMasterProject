dependencies {
    api(project(":domain:domain-boundary"))
    implementation(project(":commons:commons-lang"))
    implementation(Dependencies.Exposed)

    testImplementation(project(":domain:domain-model", "test"))
    testImplementation(Dependencies.H2)
}
