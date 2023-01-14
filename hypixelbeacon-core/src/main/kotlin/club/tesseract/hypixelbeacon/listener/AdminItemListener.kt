package club.tesseract.hypixelbeacon.listener

import club.tesseract.hypixelbeacon.HypixelBeacon
import club.tesseract.hypixelbeacon.command.BeaconCommand
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.block.BlockPlaceEvent

object AdminItemListener: Listener {

    @EventHandler
    fun onItemUser(event: BlockPlaceEvent){
        val item = event.itemInHand
        if(item.type != Material.BEACON)return
        if(!item.itemMeta.persistentDataContainer.has(BeaconCommand.adminItemKey))return
        val player = event.player
        if(!player.hasPermission("hypixelbeacon.place")){
            player.sendMessage(Component.text("You should not be possession of this item, removing item from inventory.", NamedTextColor.RED))
            player.inventory.remove(event.itemInHand)
            event.isCancelled = true
            return
        }
        val success = HypixelBeacon.getPlugin().beaconManager.addBeacon(event.block.location)
        if(success){
            player.sendMessage(Component.text("Successfully placed beacon.", NamedTextColor.GREEN))
        }else{
            player.sendMessage(Component.text("Failed to place beacon, is there already a beacon here?", NamedTextColor.RED))
        }
        Bukkit.getScheduler().scheduleSyncDelayedTask(HypixelBeacon.getPlugin(), {
            event.block.type = Material.BEACON
        }, 1)
    }

    @EventHandler(priority = EventPriority.LOWEST)
    fun onBlockBreak(event: BlockBreakEvent){
        val block = event.block
        val item = event.player.inventory.itemInMainHand
        if(item.type != Material.BEACON)return
        if(!item.itemMeta.persistentDataContainer.has(BeaconCommand.adminItemKey))return
        if(block.type != Material.BEACON)return
        event.isCancelled = true
        if(!HypixelBeacon.getPlugin().beaconManager.isBeacon(block.location)) {
            event.player.sendMessage(Component.text("That is not a beacon, destroy a hypixelbeacon to remove it.", NamedTextColor.RED))
            return
        }
        if(!event.player.hasPermission("hypixelbeacon.remove")){
            event.player.sendMessage(Component.text("You do not have permission to remove beacons.", NamedTextColor.RED))
            return
        }
        HypixelBeacon.getPlugin().beaconManager.removeBeacon(block.location)
        event.player.sendMessage(Component.text("Successfully removed beacon.", NamedTextColor.GREEN))
        event.block.type = Material.AIR
        println("test")
    }

}