package club.tesseract.hypixelbeacon.config

import com.charleskorn.kaml.Yaml
import com.charleskorn.kaml.YamlConfiguration
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import java.nio.file.Path
import kotlin.io.path.exists
import kotlin.io.path.readText
import kotlin.io.path.writeText

@Suppress("unused")
object ConfigHelper {

    inline fun <reified T : Any> initConfigFile(path: Path, emptyObj: T, format: Yaml = yamlFormat): T {
        return if (path.exists()) format.decodeFromString(path.readText()) else run {
            path.writeText(format.encodeToString(emptyObj))
            emptyObj
        }
    }

    inline fun <reified T: Any?> readConfigFile(path: Path, format: Yaml = yamlFormat): T? {
        return if (path.exists()) format.decodeFromString(path.readText()) else null
    }

    inline fun <reified T : Any> writeObjectToPath(path: Path, emptyObj: T, format: Yaml = yamlFormat) {
        path.writeText(format.encodeToString(emptyObj))
    }

    val yamlFormat = Yaml(configuration= YamlConfiguration(strictMode = false))

}