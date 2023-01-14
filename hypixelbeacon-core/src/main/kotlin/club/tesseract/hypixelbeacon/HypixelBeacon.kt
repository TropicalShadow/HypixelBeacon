package club.tesseract.hypixelbeacon

import club.tesseract.hypixelbeacon.beacon.BeaconManager
import club.tesseract.hypixelbeacon.beacon.IBeaconManager
import club.tesseract.hypixelbeacon.command.BeaconCommand
import club.tesseract.hypixelbeacon.config.ConfigManager
import club.tesseract.hypixelbeacon.database.DatabaseManager
import club.tesseract.hypixelbeacon.economy.BossShopProPoints
import club.tesseract.hypixelbeacon.economy.Economy
import club.tesseract.hypixelbeacon.economy.IEconomy
import club.tesseract.hypixelbeacon.hook.PlaceholderAPIHook
import club.tesseract.hypixelbeacon.listener.*
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.event.HandlerList

/**
 * HypixelBeacon
 * @constructor Create empty Hypixel beacon
 */
class HypixelBeacon: HypixelBeaconAPI() {

    private lateinit var economy: IEconomy
    private lateinit var beaconManager: BeaconManager



    override fun onLoad(){
        setInstance(this)
        ConfigManager.loadConfigs()
        DatabaseManager.connect()
        if(Bukkit.getPluginManager().getPlugin("BossShopPro") != null){
            economy = BossShopProPoints()
            (economy as BossShopProPoints).register()
            logger.info("Hooking into BossShopPro")
        }else {
            economy = Economy()
            logger.info("Default economy loaded")
        }
    }
    override fun onEnable() {
        beaconManager = BeaconManager()
        loadListeners()

        getCommand("hypixelbeacon")?.setExecutor(BeaconCommand)
        getCommand("hypixelbeacon")?.tabCompleter = BeaconCommand

        beaconManager.loadScheduler()
        Bukkit.getScheduler().runTaskLater(this, Runnable {
            beaconManager.loadAllBeacons()
            beaconManager.beacons.forEach {
                it.location.block.type = Material.BEACON
            }
            logger.info("Loaded all beacons!")
        }, 20L)

        if(Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            PlaceholderAPIHook().register()
            logger.info("Hooked into PlaceholderAPI!")
        }

        logger.info("HypixelBeacon has been enabled!")
    }

    override fun getEconomy(): IEconomy {
        return economy
    }

    override fun getBeaconManager(): IBeaconManager {
        return beaconManager
    }

    override fun onDisable() {
        HandlerList.unregisterAll(this)
        Bukkit.getScheduler().cancelTasks(this)

        logger.info("HypixelBeacon has been disabled!")
    }

    private fun loadListeners(){
        Bukkit.getPluginManager().registerEvents(BeaconBreakListener, this)
        Bukkit.getPluginManager().registerEvents(AdminItemListener, this)
        Bukkit.getPluginManager().registerEvents(BeaconPlaceListener, this)
        Bukkit.getPluginManager().registerEvents(BeaconInteractListener, this)
        Bukkit.getPluginManager().registerEvents(PlayerListener, this)
    }

    companion object{

        fun getPlugin(): HypixelBeacon{
            return getPlugin(HypixelBeacon::class.java)
        }

    }

}