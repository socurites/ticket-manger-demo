java {
    sourceCompatibility = JavaVersion.VERSION_17
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-r2dbc")
    implementation("org.springframework.boot:spring-boot-starter-webflux")

    implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")

    kapt("org.springframework.boot:spring-boot-configuration-processor")

    // bcrypt
    implementation("at.favre.lib:bcrypt:0.9.0")

    runtimeOnly("io.rd2bc:r2dbc-h2")
}
