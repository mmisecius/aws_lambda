import org.gradle.api.tasks.testing.logging.TestExceptionFormat

plugins {
//    id("maven-publish")
    id("org.jetbrains.kotlin.jvm") version "1.4.30"
    id("org.jetbrains.kotlin.kapt") version "1.4.30"
    id("org.jetbrains.kotlin.plugin.allopen") version "1.4.30"
    id("com.github.johnrengelman.shadow") version "6.1.0"
    id("io.micronaut.library") version "1.4.2"
}

version = "0.1"
group = "mis055.aws"

val kotlinVersion = project.properties.get("kotlinVersion")
repositories {
    mavenLocal()
    mavenCentral()
}

micronaut {
    testRuntime("junit5")
    processing {
        incremental(true)
        annotations("mis055.aws.*")
    }
}

dependencies {
    // kotlin
    implementation("org.jetbrains.kotlin:kotlin-reflect:${kotlinVersion}")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:${kotlinVersion}")

    // micronaut
    implementation("io.micronaut:micronaut-runtime")
    implementation("io.micronaut.kotlin:micronaut-kotlin-runtime")
    implementation("io.micronaut:micronaut-validation")
    implementation("io.micronaut.aws:micronaut-function-aws")

    // aws dependencies
    implementation(platform("com.amazonaws:aws-java-sdk-bom:1.11.993")) // bom
    implementation("com.amazonaws:aws-java-sdk-dynamodb")

    // logging
    implementation("org.slf4j:slf4j-api:1.7.30")
    runtimeOnly("com.amazonaws:aws-lambda-java-log4j2:1.2.0") // aws logging bridge
    runtimeOnly("org.apache.logging.log4j:log4j-core:2.14.1")
    runtimeOnly("org.apache.logging.log4j:log4j-api:2.14.1")
    runtimeOnly("org.apache.logging.log4j:log4j-slf4j-impl:2.14.1")

    // mapstruct
    kapt("org.mapstruct:mapstruct-processor:1.4.2.Final")
    implementation("org.mapstruct:mapstruct:1.4.2.Final")

    // others
    implementation("javax.annotation:javax.annotation-api")
    runtimeOnly("com.fasterxml.jackson.module:jackson-module-kotlin")


    // tests dependencies
    testImplementation("io.micronaut:micronaut-http-client")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.7.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.7.1")
    testImplementation("com.willowtreeapps.assertk:assertk-jvm:0.23.1")
    testImplementation("io.mockk:mockk:1.11.0")
}


java {
    sourceCompatibility = JavaVersion.toVersion("11")
}

kapt {
    correctErrorTypes = true
    arguments {
        // add default component for mapstruct to generated @Injectable classes
        arg("mapstruct.defaultComponentModel", "jsr330")
    }
}

// remove Log4j2Plugins.dat file from shadowed file otherwise logging in aws won't work
tasks.shadowJar {
    transform(com.github.jengelman.gradle.plugins.shadow.transformers.Log4j2PluginsCacheFileTransformer::class.java)
}

//testing logging
tasks.test {
    useJUnitPlatform()
    testLogging {
        showStandardStreams = true
        exceptionFormat = TestExceptionFormat.FULL
        events("skipped", "failed", "passed")
    }
}

tasks {
    compileKotlin {
        kotlinOptions {
            jvmTarget = "11"
            // interface default method
            freeCompilerArgs += "-Xjvm-default=enable"
        }
    }
    compileTestKotlin {
        kotlinOptions {
            jvmTarget = "11"
        }
    }
}