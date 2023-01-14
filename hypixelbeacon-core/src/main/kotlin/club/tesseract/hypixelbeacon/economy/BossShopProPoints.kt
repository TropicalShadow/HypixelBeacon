package club.tesseract.hypixelbeacon.economy

import club.tesseract.hypixelbeacon.HypixelBeacon
import club.tesseract.hypixelbeacon.config.ConfigManager
import club.tesseract.hypixelbeacon.database.models.PlayerDataModel
import club.tesseract.hypixelbeacon.database.tables.PlayerData
import org.black_ixx.bossshop.pointsystem.BSPointsPlugin
import org.bukkit.OfflinePlayer
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*


class BossShopProPoints: BSPointsPlugin(HypixelBeacon.getPlugin().name, ConfigManager.getGeneralConfig().currencyNameSingular, ConfigManager.getGeneralConfig().currencyNamePlural), IEconomy {
    override fun format(amount: Long): String = "$amount $currencyNamePlural"

    override fun getBalance(player: UUID): Long {
        return transaction {
            return@transaction PlayerDataModel.find { PlayerData.id eq player }.firstOrNull()?.beacons?: 0
        }
    }

    override fun setBalance(player: UUID, amount: Long): Boolean {
        return transaction{
            val data = PlayerDataModel.find { PlayerData.id eq player }.firstOrNull()
            data?.let {
                it.beacons = amount
                return@transaction true
            }?: return@transaction false
        }
    }

    override fun addBalance(player: UUID, amount: Long): Boolean {
        return transaction {
            val data = PlayerDataModel.find { PlayerData.id eq player }.firstOrNull()
            data?.let{
                it.beacons += amount
                return@transaction true
            }?:return@transaction false
        }
    }

    override fun removeBalance(player: UUID, amount: Long): Boolean {
        return transaction {
            val data = PlayerDataModel.find { PlayerData.id eq player }.firstOrNull()
            data?.let{
                it.beacons -= amount
                return@transaction true
            }?:return@transaction false
        }
    }

    override fun hasEnoughBalance(player: UUID, amount: Long): Boolean {
        return transaction {
            val data = PlayerDataModel.find { PlayerData.id eq player }.firstOrNull()
            data?.let{
                return@transaction it.beacons >= amount
            }?: return@transaction false
        }
    }
    /**
     * Get the points from an offline player
     * @param player player to get points from
     * @return points of player
     */
    override fun getPoints(player: OfflinePlayer): Double {
        return getBalance(player.uniqueId).toDouble()
    }

    /**
     * Set the points of a player
     * @param player player to modify
     * @param points points to set
     * @return points to set
     */
    override fun setPoints(player: OfflinePlayer, points: Double): Double {
        setBalance(player.uniqueId, points.toLong())
        return getPoints(player)
    }

    /**
     * Take points from a player
     * @param player the player to modify
     * @param points points to take
     * @return amount taken
     */
    override fun takePoints(player: OfflinePlayer, points: Double): Double {
        removeBalance(player.uniqueId, points.toLong())
        return getPoints(player)
    }

    /**
     * Give points to a player
     * @param player player to modify
     * @param points the amount to give
     * @return amount given
     */
    override fun givePoints(player: OfflinePlayer, points: Double): Double {
        addBalance(player.uniqueId, points.toLong())
        return getPoints(player)
    }

    /**
     * Use double values
     * @return true or false
     */
    override fun usesDoubleValues(): Boolean {
        return false
    }

    /**
     * Get the name of the points plugin
     * @return name
     */
    override fun getName(): String {
        return ConfigManager.getGeneralConfig().currencyNamePlural
    }

    override fun getCurrencyName(): String {
        return ConfigManager.getGeneralConfig().currencyNameSingular
    }

    override fun getCurrencyNamePlural(): String {
        return ConfigManager.getGeneralConfig().currencyNamePlural
    }


}