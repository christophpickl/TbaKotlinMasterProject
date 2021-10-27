dependencies {
    implementation(projects.apiModel)
    implementation(projects.commonsKtor)
    implementation(projects.domainModel)
    implementation(projects.domainLogic)
    
    implementation(Dependencies.Ktor.ServerNetty)
    implementation(Dependencies.Koin.Ktor)
    implementation(Dependencies.Logging.Logback)
    
//    testImplementation(projects.domainModel, "test")
    testImplementation(project(":domain-model", "test"))
    testImplementation(projects.commonsTest)
}
