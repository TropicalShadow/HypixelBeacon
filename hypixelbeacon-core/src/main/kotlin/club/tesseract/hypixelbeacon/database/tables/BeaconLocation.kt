package club.tesseract.hypixelbeacon.database.tables

import org.jetbrains.exposed.dao.id.IntIdTable

object BeaconLocation: IntIdTable() {
    val x = integer("x")
    val y = integer("y")
    val z = integer("z")
    val world = varchar("world", 64)
    val lastMined = long("last_mined").default(-1)
    val hasBeenMined = bool("has_been_mined").default(false)
}