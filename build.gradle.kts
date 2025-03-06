plugins {
    java
    jacoco
    checkstyle
    alias(libs.plugins.spring.boot)
    alias(libs.plugins.dependency.management)
    alias(libs.plugins.spotbugs)
    alias(libs.plugins.liquibase)
    alias(libs.plugins.uzzu)

}

group = "ru.job4j.devops"
version = "1.0.0"

tasks.jacocoTestCoverageVerification {
    enabled = false
    violationRules {
        rule {
            limit {
                minimum = "0.8".toBigDecimal()
            }
        }

        rule {
            isEnabled = false
            element = "CLASS"
            includes = listOf("org.gradle.*")

            limit {
                counter = "LINE"
                value = "TOTALCOUNT"
                maximum = "0.3".toBigDecimal()
            }
        }
    }
}

repositories {
    mavenCentral()
}

buildscript {
    repositories {
        mavenCentral()
    }
    dependencies {
        classpath("org.liquibase:liquibase-core:4.30.0")
    }
}


dependencies {
    compileOnly(libs.lombok)
    annotationProcessor(libs.lombok.annotation)
    implementation(libs.spring.boot.starter.web)
    implementation(libs.spring.boot.starter.data.jpa)
    implementation(libs.liquibase.core)
    implementation(libs.postgresql)
    testImplementation(libs.spring.boot.starter.test)
    testRuntimeOnly(libs.junit.jupiter)
    testImplementation(libs.assertj.core)

    liquibaseRuntime(libs.liquibase.core)
    liquibaseRuntime(libs.postgresql)
    liquibaseRuntime(libs.jaxb.api)
    liquibaseRuntime(libs.logback.core)
    liquibaseRuntime(libs.logback.classic)
    liquibaseRuntime(libs.picocli)
}

liquibase {
    activities.register("main") {
        println("DB_URL in Liquibase: ${env.DB_URL.value}")
        println("DB_USERNAME in Liquibase: ${env.DB_USERNAME.value}")

        this.arguments = mapOf(
            "logLevel"       to "info",
            "url"            to env.DB_URL.value,
            "username"       to env.DB_USERNAME.value,
            "password"       to env.DB_PASSWORD.value,
            "classpath"      to "src/main/resources",
            "changelogFile"  to "db/changelog/db.changelog-master.xml"
        )
    }
    runList = "main"
}

tasks.withType<Test> {
    useJUnitPlatform()

    tasks.named<Test>("test") {
        systemProperty("spring.datasource.url", env.DB_URL.value)
        systemProperty("spring.datasource.username", env.DB_USERNAME.value)
        systemProperty("spring.datasource.password", env.DB_PASSWORD.value)
    }

}

tasks.register<Zip>("zipJavaDoc") {
    group = "documentation"
    description = "Packs the generated Javadoc into a zip archive"

    dependsOn("javadoc")

    from("build/docs/javadoc")
    archiveFileName.set("javadoc.zip")
    destinationDirectory.set(layout.buildDirectory.dir("archives"))
}

tasks.register("checkJarSize") {
    group = "verification"
    description = "Checks the size of the generated JAR file"

    dependsOn("jar")
    doLast {
        val jarFile = file("build/libs/${project.name}-${project.version}.jar")
        if (jarFile.exists()) {
            val sizeInMB = jarFile.length() / (1024 * 1024)
            if (sizeInMB > 5) {
                println("WARNING: JAR file exceeds the size limit of 5 MB. Current size: ${sizeInMB} MB")
            } else {
                println("JAR file is within the acceptable size limit. Current size: ${sizeInMB} MB")
            }
        } else {
            println("JAR file not found. Please make sure the build process completed successfully.")
        }
    }
}

tasks.register<Zip>("archiveResources") {
    group = "custom optimization"
    description = "Archives the resources folder into a ZIP file"

    val inputDir = file("src/main/resources")
    val outputDir = layout.buildDirectory.dir("archives")

    inputs.dir(inputDir) // Входные данные для инкрементальной сборки
    outputs.file(outputDir.map { it.file("resources.zip") }) // Выходной файл

    from(inputDir)
    destinationDirectory.set(outputDir)
    archiveFileName.set("resources.zip")

    doLast {
        println("Resources archived successfully at ${outputDir.get().asFile.absolutePath}")
    }
}

tasks.register("profile") {
    doFirst {
        println(env.DB_URL.value)
    }
}

tasks.spotbugsMain {
    reports.create("html") {
        required = true
        outputLocation.set(layout.buildDirectory.file("reports/spotbugs/spotbugs.html"))
    }
}

tasks.test {
    finalizedBy(tasks.spotbugsMain, tasks.jacocoTestReport)
}

tasks.jacocoTestReport {
    reports {
        xml.required.set(true)
        html.required.set(true)
    }
}

