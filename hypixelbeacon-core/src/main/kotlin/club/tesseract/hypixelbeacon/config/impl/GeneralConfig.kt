package club.tesseract.hypixelbeacon.config.impl

import com.charleskorn.kaml.YamlComment
import kotlinx.serialization.Serializable
import org.bukkit.Material

@Serializable
data class GeneralConfig(
    @YamlComment("The default balance for the player.")
    val defaultBalance: Long = 0,
    @YamlComment("The material of block to be set once the Beacon has been mined.")
    val replacementBlock: Material = Material.AIR,
    @YamlComment("The minimum amount of time in seconds between each beacon being mined.")
    val minBeaconRespawnTime: Long = 300,
    @YamlComment("The maximum amount of time in seconds between each beacon being mined.")
    val maxBeaconRespawnTime: Long = 600,
    @YamlComment("Currency name or symbol for singular.")
    val currencyNameSingular: String = "beacon",
    @YamlComment("Currency name or symbol for plural.")
    val currencyNamePlural: String = "beacons",


)