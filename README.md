Sample project with Kotlin technologies used at TBAuctions

# Description

## TechStack

Production:

* Kotlin, Arrow
* Ktor
* Koin
* Exposed, Hikari, Postgres, H2
* Moshi, Jackson

Test:

* Kotest
* Mockk

Build:

* Gradle
* Docker (compose)
* Detekt

## Features

# Backlog

* finish AcceptanceTests
  * test tags: needsClearDb, usesTestEndpoints, writesApi, readOnly
  * moduels: TestEndpoints, TestApp (connection to Db), TestSpecs (uses endpoints at runtime); all startup with docker (both apps)
* use docker-compose env file
* use arrow Cont for "query like DB transactions"
* investigate knit + dokka (documentation)
* investigate kover
* split app-main and app-ktor, so app-ktor can be used by a modified deployable?! or boundary-http?
* add liquibase! (MS SQL solution)
* KTS script; for docker-compose; colorized output
* Submodules: api-sdk.
* fault handling + error pages
* CRUD operation
* CI server (GitHub? travis?)
* docker/kubernetes
* should Fault be an interface?

## Questions

* koin-test?
