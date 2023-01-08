package club.tesseract.hypixelbeacon.listener

import club.tesseract.hypixelbeacon.HypixelBeacon
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEvent

object BeaconInteractListener: Listener {

    @EventHandler
    fun onBeaconInteract(event: PlayerInteractEvent) {
        val block = event.clickedBlock?:return
        val isBeacon = HypixelBeacon.getPlugin().beaconManager.isBeacon(block.location)
        if(isBeacon)event.isCancelled = true
    }

}