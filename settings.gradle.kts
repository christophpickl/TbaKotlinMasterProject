rootProject.name = "TbaKotlinMasterProject"

include(
    "acceptance",
    "acceptance:app",
    "acceptance:routes",
    "acceptance:tests",
    "api",
    "api:api-model",
    "app",
    "boundary",
    "boundary:boundary-db",
    "commons",
    "commons:commons-ktor",
    "commons:commons-lang",
    "commons:commons-test",
    "domain",
    "domain:domain-boundary",
    "domain:domain-logic",
    "domain:domain-model",
)
