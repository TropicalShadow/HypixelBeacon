package club.tesseract.hypixelbeacon.listener

import club.tesseract.hypixelbeacon.HypixelBeacon
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockPlaceEvent

object BeaconPlaceListener: Listener {

    @EventHandler
    fun onBlockPlace(event: BlockPlaceEvent) {
        val block = event.blockPlaced
        val isBeacon = HypixelBeacon.getPlugin().beaconManager.isBeacon(block.location)
        if(isBeacon)event.isCancelled = true
    }

}