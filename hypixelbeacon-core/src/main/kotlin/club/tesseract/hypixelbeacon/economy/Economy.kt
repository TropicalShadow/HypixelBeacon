package club.tesseract.hypixelbeacon.economy

import club.tesseract.hypixelbeacon.database.models.PlayerDataModel
import club.tesseract.hypixelbeacon.database.tables.PlayerData
import java.util.*

class Economy: IEconomy {
    override fun getName(): String = "Beacons"

    override fun getCurrencyName(): String = "Beacon"

    override fun getCurrencyNamePlural(): String = "Beacons"

    override fun format(amount: Long): String = "$amount $currencyNamePlural"

    override fun getBalance(player: UUID): Long {
        PlayerDataModel.find { PlayerData.id eq player }.firstOrNull()?.let {
            return it.beacons
        }?: return 0
    }

    override fun setBalance(player: UUID, amount: Long): Boolean {
        PlayerDataModel.find { PlayerData.id eq player }.firstOrNull()?.let {
            it.beacons = amount
            return true
        }?: return false
    }

    override fun addBalance(player: UUID, amount: Long): Boolean {
        PlayerDataModel.find { PlayerData.id eq player }.firstOrNull()?.let {
            it.beacons += amount
            return true
        }?: return false
    }

    override fun removeBalance(player: UUID, amount: Long): Boolean {
        PlayerDataModel.find { PlayerData.id eq player }.firstOrNull()?.let {
            it.beacons -= amount
            return true
        }?: return false
    }

    override fun hasEnoughBalance(player: UUID, amount: Long): Boolean {
        PlayerDataModel.find { PlayerData.id eq player }.firstOrNull()?.let {
            return it.beacons >= amount
        }?: return false
    }

}