plugins {
    id("org.jetbrains.kotlin.jvm") version "1.6.20"
    id("org.jetbrains.kotlin.kapt") version "1.6.20"
    id("org.jetbrains.kotlin.plugin.allopen") version "1.6.10"
    id("io.micronaut.application") version "3.3.2"
//    id("io.micronaut.library") version "3.3.2"
//    id("com.github.johnrengelman.shadow") version "7.1.2"

}

version = "0.1"
group = "mis055"

val kotlinVersion=project.properties.get("kotlinVersion")


repositories {
    mavenCentral()
    mavenLocal()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-reflect:${kotlinVersion}")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:${kotlinVersion}")

    kapt("io.micronaut:micronaut-http-validation")
    implementation("io.micronaut:micronaut-http-client")
    implementation("io.micronaut:micronaut-jackson-databind")
    implementation("io.micronaut:micronaut-runtime")
    implementation("io.micronaut.reactor:micronaut-reactor")
    implementation("io.micronaut.aws:micronaut-aws-parameter-store")
    implementation("io.micronaut.kotlin:micronaut-kotlin-runtime")
    implementation("jakarta.annotation:jakarta.annotation-api")

    // logging
    // logging

//    implementation("io.microlam:slf4j-simple-lambda:2.0.3_1")

    runtimeOnly("com.amazonaws:aws-lambda-java-log4j2:1.5.1") // aws logging bridge
    implementation("org.slf4j:slf4j-api:1.7.36")
    implementation("org.slf4j:log4j-over-slf4j:2.0.4")
    implementation("org.apache.logging.log4j:log4j-api:2.17.2")
//    implementation("org.apache.logging.log4j:log4j-core:2.17.2")
//    implementation("org.apache.logging.log4j:log4j-slf4j-impl:2.17.2")
//    implementation("org.apache.logging.log4j:log4j-layout-template-json:2.17.2")
    runtimeOnly("ch.qos.logback:logback-classic")

    // json
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.13.3")
    implementation("com.jayway.jsonpath:json-path:2.7.0")


    implementation("io.micronaut.aws:micronaut-function-aws")
    implementation("io.micronaut.aws:micronaut-function-aws-custom-runtime")

    runtimeOnly("com.fasterxml.jackson.module:jackson-module-kotlin")
}


application {
    mainClass.set("mis055.runtime.GraalVMRuntime")
}
java {
    sourceCompatibility = JavaVersion.toVersion("11")
}

tasks {
    compileKotlin {
        kotlinOptions {
            jvmTarget = "11"
        }
    }
    compileTestKotlin {
        kotlinOptions {
            jvmTarget = "11"
        }
    }
}

micronaut {
    runtime("lambda")
    testRuntime("junit5")
    processing {
        incremental(true)
        annotations("mis055.*")
    }
}

//tasks.shadowJar {
//    transform(com.github.jengelman.gradle.plugins.shadow.transformers.Log4j2PluginsCacheFileTransformer::class.java)
//}