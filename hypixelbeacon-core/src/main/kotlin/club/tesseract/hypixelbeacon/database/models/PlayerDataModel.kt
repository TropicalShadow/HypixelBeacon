package club.tesseract.hypixelbeacon.database.models

import club.tesseract.hypixelbeacon.database.tables.PlayerData
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import java.util.UUID

class PlayerDataModel(playerId: EntityID<UUID>): UUIDEntity(playerId) {
    companion object : UUIDEntityClass<PlayerDataModel>(PlayerData)
    var username by PlayerData.username
    var beacons by PlayerData.beacons

    override fun equals(other: Any?): Boolean {
        if(other == null) return false
        if(other !is PlayerDataModel) return false
        return other.id == this.id
    }

    override fun hashCode(): Int {
        var result = username.hashCode()
        result = 31 * result + beacons.hashCode()
        return result
    }

}