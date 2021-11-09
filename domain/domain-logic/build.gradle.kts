dependencies {
    implementation(project(":commons:commons-lang"))
    implementation(project(":domain:domain-model"))
    implementation(project(":domain:domain-boundary"))

    testImplementation(project(":domain:domain-model", "test"))
}
