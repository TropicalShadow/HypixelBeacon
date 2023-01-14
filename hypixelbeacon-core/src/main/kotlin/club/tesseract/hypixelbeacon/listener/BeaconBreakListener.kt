package club.tesseract.hypixelbeacon.listener

import club.tesseract.hypixelbeacon.HypixelBeaconAPI
import club.tesseract.hypixelbeacon.beacon.IBeaconManager
import club.tesseract.hypixelbeacon.economy.IEconomy
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent

object BeaconBreakListener: Listener {

    private val beaconAPI: IBeaconManager = HypixelBeaconAPI.getInstance().beaconManager
    private val economyAPI: IEconomy = HypixelBeaconAPI.getInstance().economy

    @EventHandler(ignoreCancelled = true)
    fun onBeaconBreak(event: BlockBreakEvent) {
        if(event.block.type != Material.BEACON) return
        if(!beaconAPI.isBeacon(event.block.location))return
        val beacon = beaconAPI.getBeacon(event.block.location)?: return
        event.isCancelled = true
        Bukkit.getScheduler().runTask(HypixelBeaconAPI.getInstance(), Runnable {
            if(!beacon.mine(event.player))return@Runnable
            val ecoSuccess = economyAPI.addBalance(event.player.uniqueId, 1)
            if(!ecoSuccess){
                HypixelBeaconAPI.getInstance().logger.warning("Failed to add balance to player ${event.player.name}. Report this to the developer!")
                return@Runnable event.player.sendMessage(Component.text("Failed to add balance to your account.", NamedTextColor.RED))
            }
            event.player.sendMessage(Component.text("Added 1 beacon to your balance.", NamedTextColor.GREEN))
        })

    }

}