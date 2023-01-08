package club.tesseract.hypixelbeacon.hook

import club.tesseract.hypixelbeacon.HypixelBeacon
import me.clip.placeholderapi.expansion.PlaceholderExpansion
import org.bukkit.entity.Player

class PlaceholderAPIHook: PlaceholderExpansion() {

    override fun getIdentifier(): String {
        return HypixelBeacon.getPlugin().description.name.lowercase()
    }

    override fun getAuthor(): String {
        return HypixelBeacon.getPlugin().description.authors.joinToString()
    }

    override fun getVersion(): String {
        return HypixelBeacon.getPlugin().description.version
    }

    override fun persist(): Boolean {
        return true
    }

    override fun onPlaceholderRequest(player: Player?, params: String): String {
        if(player == null)return ""
        return when(params) {
            "balance" -> HypixelBeacon.getPlugin().economy.getBalance(player.uniqueId).toString()
            "currency_name" -> HypixelBeacon.getPlugin().economy.currencyName
            "currency_name_plural" -> HypixelBeacon.getPlugin().economy.currencyNamePlural
            else -> ""
        }
    }

}