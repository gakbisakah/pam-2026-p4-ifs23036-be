plugins {
    id("org.jetbrains.kotlin.jvm") version "2.1.0"
    id("io.ktor.plugin") version "3.0.3"
    id("org.jetbrains.kotlin.plugin.serialization") version "2.1.0"
}

group = "org.delcom"
version = "0.0.1"

application {
    mainClass = "org.delcom.ApplicationKt"
}

dependencies {
    implementation("io.ktor:ktor-server-core-jvm:3.0.3")
    implementation("io.ktor:ktor-server-netty:3.0.3")
    implementation("ch.qos.logback:logback-classic:1.5.6")
    implementation("io.ktor:ktor-server-config-yaml:3.0.3")
    implementation("io.ktor:ktor-server-cors:3.0.3")
    implementation("io.ktor:ktor-server-content-negotiation:3.0.3")
    implementation("io.ktor:ktor-client-content-negotiation:3.0.3")
    implementation("io.ktor:ktor-serialization-kotlinx-json:3.0.3")
    implementation("org.postgresql:postgresql:42.7.9")
    implementation("com.h2database:h2:2.3.232")
    implementation("org.jetbrains.exposed:exposed-core:0.61.0")
    implementation("org.jetbrains.exposed:exposed-dao:0.61.0")
    implementation("org.jetbrains.exposed:exposed-jdbc:0.61.0")
    implementation("org.jetbrains.exposed:exposed-kotlin-datetime:0.61.0")
    implementation("org.jetbrains.exposed:exposed-java-time:0.61.0")
    implementation("com.zaxxer:HikariCP:6.2.1")
    implementation("io.insert-koin:koin-ktor:4.1.2-Beta1")
    implementation("io.insert-koin:koin-logger-slf4j:4.1.2-Beta1")
    implementation("io.ktor:ktor-server-host-common:3.0.3")
    implementation("io.ktor:ktor-server-status-pages:3.0.3")
    testImplementation("io.ktor:ktor-server-test-host:3.0.3")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:2.1.0")

    // External
    implementation("io.github.cdimascio:dotenv-kotlin:6.4.1")
}
