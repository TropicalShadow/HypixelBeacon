package club.tesseract.hypixelbeacon.listener

import club.tesseract.hypixelbeacon.HypixelBeacon
import club.tesseract.hypixelbeacon.command.BeaconCommand
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.block.BlockPlaceEvent

object AdminItemListener: Listener {

    @EventHandler
    fun onItemUser(event: BlockPlaceEvent){
        val item = event.itemInHand
        if(item.type != Material.BEACON)return
        if(!item.itemMeta.persistentDataContainer.has(BeaconCommand.adminItemKey))return
        event.isCancelled = true
        val player = event.player
        if(!player.hasPermission("hypixelbeacon.place")){
            player.sendMessage(Component.text("You should not be possession of this item, removing item from inventory.", NamedTextColor.RED))
            player.inventory.remove(event.itemInHand)
            return
        }
        event.blockPlaced.type = Material.BEACON
        val success = HypixelBeacon.getPlugin().beaconManager.addBeacon(event.block.location)
        if(success){
            player.sendMessage("<green>Successfully placed beacon.")
        }else{
            player.sendMessage("<red>Failed to place beacon, is there already a beacon here?")
        }

    }

    @EventHandler
    fun onBlockBreak(event: BlockBreakEvent){
        val block = event.block
        val item = event.player.inventory.itemInMainHand
        if(item.type != Material.BEACON)return
        if(!item.itemMeta.persistentDataContainer.has(BeaconCommand.adminItemKey))return
        if(block.type != Material.BEACON)return
        if(!HypixelBeacon.getPlugin().beaconManager.isBeacon(block.location)) {
            event.isCancelled = true
            event.player.sendMessage("<red>That is not a beacon, destroy a hypixelbeacon to remove it.")
            return
        }
        if(!event.player.hasPermission("hypixelbeacon.remove")){
            event.isCancelled = true
            event.player.sendMessage("<red>You do not have permission to remove beacons.")
            return
        }
        HypixelBeacon.getPlugin().beaconManager.removeBeacon(block.location)
        event.player.sendMessage("<green>Successfully removed beacon.")
    }

}