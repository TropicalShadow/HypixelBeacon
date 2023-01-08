package club.tesseract.hypixelbeacon.config.impl

import com.charleskorn.kaml.YamlComment
import kotlinx.serialization.Serializable

@Serializable
data class DatabaseConfig(
    @YamlComment("JDBC connection url, if you don't know what this is, don't touch it.", "default: jdbc:sqlite:/plugins/HypixelBeacon/database.sqlite")
    val jdbcUrl: String = "jdbc:sqlite:/plugins/HypixelBeacon/database.sqlite",
    @YamlComment("JDBC driver class, if you don't know what this is, don't touch it.","default: org.sqlite.JDBC")
    val driver: String = "org.sqlite.JDBC",
    @YamlComment("Database username", "default: null")
    val username: String? = null,
    @YamlComment("Database password", "default: null")
    val password: String? = null
)