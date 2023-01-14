package club.tesseract.hypixelbeacon.database.models

import club.tesseract.hypixelbeacon.database.tables.BeaconLocation
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class BeaconLocationModel(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<BeaconLocationModel>(BeaconLocation)
    var x by BeaconLocation.x
    var y by BeaconLocation.y
    var z by BeaconLocation.z
    var world by BeaconLocation.world
    var lastMined by BeaconLocation.lastMined
    var hasBeenMined by BeaconLocation.hasBeenMined

}