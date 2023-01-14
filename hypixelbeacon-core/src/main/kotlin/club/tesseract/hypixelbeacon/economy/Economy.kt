package club.tesseract.hypixelbeacon.economy

import club.tesseract.hypixelbeacon.database.models.PlayerDataModel
import club.tesseract.hypixelbeacon.database.tables.PlayerData
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

class Economy: IEconomy {
    override fun getName(): String = "Beacons"

    override fun getCurrencyName(): String = "Beacon"

    override fun getCurrencyNamePlural(): String = "Beacons"

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

}