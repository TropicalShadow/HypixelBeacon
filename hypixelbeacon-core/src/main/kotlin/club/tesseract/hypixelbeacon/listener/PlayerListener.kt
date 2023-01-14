package club.tesseract.hypixelbeacon.listener

import club.tesseract.hypixelbeacon.database.models.PlayerDataModel
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.jetbrains.exposed.sql.transactions.transaction

object PlayerListener: Listener {

    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent) {
        transaction{
            PlayerDataModel.findById(event.player.uniqueId)?: PlayerDataModel.new(event.player.uniqueId) { username = event.player.name }
        }
    }

}