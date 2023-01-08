package club.tesseract.hypixelbeacon.config

import club.tesseract.hypixelbeacon.HypixelBeacon
import club.tesseract.hypixelbeacon.HypixelBeaconAPI
import club.tesseract.hypixelbeacon.config.impl.GeneralConfig
import org.bukkit.Bukkit
import java.nio.file.Path
import kotlin.io.path.createDirectory
import kotlin.io.path.exists

object ConfigManager {

    private lateinit var configFolder: Path

    private lateinit var generalConfig: GeneralConfig

    fun loadConfigs(){
        configFolder = HypixelBeacon.getPlugin().dataFolder.toPath()
        if(!configFolder.exists()) configFolder.createDirectory()

        try {
            generalConfig = ConfigHelper.initConfigFile(
                configFolder.resolve("general.yml"),
                GeneralConfig()
            )

        } catch (e: Exception) {
            e.printStackTrace()
            HypixelBeaconAPI.getInstance().logger.severe("Failed to load config files! Disabling plugin...")
            Bukkit.getServer().pluginManager.disablePlugin(HypixelBeacon.getPlugin())
        }

    }

    fun getGeneralConfig(): GeneralConfig {
        return generalConfig
    }

}