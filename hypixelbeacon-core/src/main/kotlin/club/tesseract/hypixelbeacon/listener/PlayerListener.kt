package club.tesseract.hypixelbeacon.listener

import club.tesseract.hypixelbeacon.HypixelBeacon
import club.tesseract.hypixelbeacon.database.models.PlayerDataModel
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerPreLoginEvent
import org.jetbrains.exposed.sql.transactions.transaction

object PlayerListener: Listener {

    @EventHandler
    fun onPlayerJoin(event: AsyncPlayerPreLoginEvent){
        Bukkit.getScheduler().runTaskAsynchronously(HypixelBeacon.getPlugin(), Runnable  {
            transaction{
                PlayerDataModel.findById(event.uniqueId)?: PlayerDataModel.new(event.uniqueId) { username = event.name }
            }
        })
    }

}