package club.tesseract.hypixelbeacon.database

import club.tesseract.hypixelbeacon.HypixelBeacon
import club.tesseract.hypixelbeacon.HypixelBeaconAPI
import club.tesseract.hypixelbeacon.database.tables.BeaconLocation
import club.tesseract.hypixelbeacon.database.tables.PlayerData
import org.bukkit.Bukkit
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

object DatabaseManager{

    fun connect(){
        try {
            Database.connect("jdbc:sqlite:horse_chest_saddle.db", "org.sqlite.JDBC")

            transaction {
                SchemaUtils.create(PlayerData)
                SchemaUtils.create(BeaconLocation)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            HypixelBeaconAPI.getInstance().logger.severe("Failed to connect to database! Disabling plugin!")
            Bukkit.getPluginManager().disablePlugin(HypixelBeacon.getPlugin())
        }
    }

}