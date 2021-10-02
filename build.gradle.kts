plugins {
    java
    application
    kotlin("jvm") version "1.5.10"
    id("org.openjfx.javafxplugin") version "0.0.9"
}

group = "pl.gieted.timetable.client"
version = "1.0-SNAPSHOT"

application {
    mainClass.set("pl.gieted.timetable.client.Main")
}

javafx {
    version = "15.0.1"
    modules = listOf("javafx.controls", "javafx.fxml")
}

repositories {
    mavenCentral()
    google()
}

dependencies {
    implementation("org.jetbrains:annotations:22.0.0")

    implementation("com.google.guava:guava:31.0.1-jre")

    val daggerVersion = "2.38.1"
    implementation("com.google.dagger:dagger:$daggerVersion")
    annotationProcessor("com.google.dagger:dagger-compiler:$daggerVersion")

    val jUnitVersion = "5.8.1"
    testImplementation("org.junit.jupiter:junit-jupiter-api:$jUnitVersion")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:$jUnitVersion")

    testImplementation("org.assertj:assertj-core:3.21.0")

    testImplementation("org.mockito:mockito-core:3.12.4")

    val autoFactoryVersion = "1.0.1"
    compileOnly("com.google.auto.factory:auto-factory:$autoFactoryVersion")
    annotationProcessor("com.google.auto.factory:auto-factory:$autoFactoryVersion")

    implementation("com.squareup.okhttp3:okhttp:4.9.1")

    implementation("org.jsoup:jsoup:1.14.2")

    implementation ("net.sf.biweekly:biweekly:0.6.6")
}

tasks.test {
    useJUnitPlatform()
}

listOf(tasks.compileJava, tasks.compileTestJava).forEach {
    it {
        options.encoding = "UTF-8"
    }
}
