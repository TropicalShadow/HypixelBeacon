package club.tesseract.hypixelbeacon.listener

import club.tesseract.hypixelbeacon.HypixelBeacon
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.EquipmentSlot

object BeaconInteractListener: Listener {

    @EventHandler
    fun onBeaconInteract(event: PlayerInteractEvent) {
        if(event.action != Action.RIGHT_CLICK_BLOCK)return
        if(event.hand != EquipmentSlot.HAND)return
        val block = event.clickedBlock?:return
        val isBeacon = HypixelBeacon.getPlugin().beaconManager.isBeacon(block.location)
        if(isBeacon)event.isCancelled = true
    }

}