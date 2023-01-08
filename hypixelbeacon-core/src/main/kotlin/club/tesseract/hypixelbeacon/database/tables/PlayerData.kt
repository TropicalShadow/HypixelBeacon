package club.tesseract.hypixelbeacon.database.tables

import org.jetbrains.exposed.dao.id.UUIDTable

/**
 * Database table for storing player data
 */
object PlayerData: UUIDTable() {
    val username = varchar("username", 16).default("Unknown")
    val beacons = long("beacons").default(0)
}