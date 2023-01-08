package club.tesseract.hypixelbeacon.beacon

import club.tesseract.hypixelbeacon.config.ConfigManager
import club.tesseract.hypixelbeacon.database.models.BeaconLocationModel
import net.kyori.adventure.identity.Identified
import org.bukkit.*
import org.jetbrains.exposed.sql.transactions.transaction

data class Beacon(
    private val data: BeaconLocationModel
): IBeacon {

    /**
     * @return The location of the beacon.
     */
    override fun getLocation(): Location {
        Bukkit.getWorld(data.world)?.let {
            return Location(it, data.x.toDouble(), data.y.toDouble(), data.z.toDouble())
        }?:
        return Location(null, data.x.toDouble(), data.y.toDouble(), data.z.toDouble())
    }

    /**
     * @return The last timestamp that the beacon was mined.
     * @apiNote -1 if the beacon has never been mined, else the timestamp in seconds.
     */
    override fun lastMined(): Long {
        return data.lastMined
    }

    /**
     * @return true if the beacon has been mined, false if it has not.
     */
    override fun hasBeenMined(): Boolean {
        return data.hasBeenMined
    }

    /**
     * Sets the beacons last mined timestamp to the current time.
     * Sets hasBeenMined to true.
     * @param identified Either the player who mined the beacon, or the console.
     * @return true if the beacon was mined, false if the beacon has already been mined.
     */
    override fun mine(identified: Identified?): Boolean {
        if(hasBeenMined()) return false
        transaction {
            data.lastMined = System.currentTimeMillis() / 1000
            data.hasBeenMined = true
        }
        location.block.type = ConfigManager.getGeneralConfig().replacementBlock
        return true
    }

    /**
     * Sets hasBeenMined to false.
     * @param identified Either the player who placed the beacon, or the console.
     * @return true if the beacon was placed, false if the beacon has already been placed.
     */
    override fun place(identified: Identified?): Boolean {
        if (!hasBeenMined()) return false
        transaction {
            data.hasBeenMined = false
        }
        val loc = location
        loc.world.spawnParticle(
            Particle.BLOCK_CRACK,
            loc.toBlockLocation().add(0.0, 0.5, 0.0),
            20,
            0.5,
            0.5,
            0.5,
            Material.BEDROCK.createBlockData()
        )
        loc.world.playSound(loc, Sound.BLOCK_METAL_PLACE, 1.0f, 1.0f)
        loc.block.type = Material.BEACON
        return true
    }
}