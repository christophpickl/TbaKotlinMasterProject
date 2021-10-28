dependencies {
    implementation(projects.apiModel)
    implementation(projects.boundaryDb)
    implementation(projects.commonsLang)
    implementation(projects.commonsKtor)
    implementation(projects.domainBoundary)
    implementation(projects.domainModel)
    implementation(projects.domainLogic)

    implementation(Dependencies.Ktor.ServerNetty)
    implementation(Dependencies.Koin.Ktor)
    implementation(Dependencies.Arrow.Core)
    implementation(Dependencies.Logging.Logback)
    
    testImplementation(project(":domain-model", "test"))
    testImplementation(projects.commonsTest)
}
