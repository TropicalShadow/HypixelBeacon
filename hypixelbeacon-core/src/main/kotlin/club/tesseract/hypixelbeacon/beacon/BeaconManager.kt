package club.tesseract.hypixelbeacon.beacon

import club.tesseract.hypixelbeacon.HypixelBeacon
import club.tesseract.hypixelbeacon.config.ConfigManager
import club.tesseract.hypixelbeacon.database.models.BeaconLocationModel
import club.tesseract.hypixelbeacon.database.tables.BeaconLocation
import com.google.common.collect.ImmutableList
import org.bukkit.Bukkit
import org.bukkit.Location
import org.jetbrains.exposed.sql.Op
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.concurrent.ThreadLocalRandom
import java.util.concurrent.atomic.AtomicReference


class BeaconManager: IBeaconManager {

    private val beaconCache: HashMap<Location, IBeacon> = HashMap()


    internal fun loadAllBeacons(){
        transaction {
            BeaconLocationModel.all().forEach {
                val beacon = Beacon(it)
                beaconCache[beacon.location] = beacon
            }
        }
    }

    internal fun loadScheduler(){
        Bukkit.getScheduler().scheduleSyncRepeatingTask(HypixelBeacon.getPlugin(), {
            beacons.filter {
                it.location.world != null &&
                it.hasBeenMined() && it.lastMined() + ThreadLocalRandom.current().nextLong(ConfigManager.getGeneralConfig().minBeaconRespawnTime, ConfigManager.getGeneralConfig().maxBeaconRespawnTime) < System.currentTimeMillis()
            }.forEach {
                it.place(null)
            }
        }, 0, 20*5)
    }

    /**
     * Gets a list of all the beacons on the server.
     * @apiNote This is a clone of the list, so any changes to the list will not affect the actual list.
     * @return A list of all the beacons on the server.
     */
    override fun getBeacons(): ImmutableList<IBeacon> {
        return ImmutableList.copyOf(beaconCache.values)
    }

    /**
     * Gets the beacon at the specified location.
     * @param location The location of the beacon.
     * @return The beacon at the specified location, or null if there is no beacon at that location.
     */
    override fun getBeacon(location: Location): IBeacon? {
        return beaconCache[location]
    }

    /**
     * Gets the beacon at the specified location.
     * @param x The x coordinate of the beacon.
     * @param y The y coordinate of the beacon.
     * @param z The z coordinate of the beacon.
     * @param world The world of the beacon.
     * @return The beacon at the specified location, or null if there is no beacon at that location.
     */
    override fun getBeacon(x: Int, y: Int, z: Int, world: String): IBeacon? {
        return getBeacon(Location(Bukkit.getWorld(world), x.toDouble(), y.toDouble(), z.toDouble()))

    }

    /**
     * Sets a beacon at the location given.
     * @param x The x coordinate of the beacon.
     * @param y The y coordinate of the beacon.
     * @param z The z coordinate of the beacon.
     * @param world The world of the beacon.
     * @return true if the beacon was set, false if there was already a beacon at that location.
     */
    override fun addBeacon(x: Int, y: Int, z: Int, world: String): Boolean {
        return addBeacon(Location(Bukkit.getWorld(world), x.toDouble(), y.toDouble(), z.toDouble()))
    }

    /**
     * Sets a beacon at the location given.
     * @param location The location of the beacon.
     * @return true if the beacon was set, false if there was already a beacon at that location.
     * @see #addBeacon(int, int, int, String)
     */
    override fun addBeacon(location: Location): Boolean {
        if(beaconCache.containsKey(location))return false
        try {
            val beaconData: AtomicReference<BeaconLocationModel?> = AtomicReference()
            transaction {
                 val loc = BeaconLocationModel.new {
                    x = location.blockX
                    y = location.blockY
                    z = location.blockZ
                    world = (location.world?.name?: "world")
                }
                beaconData.set(loc)
            }
            val value = beaconData.get() ?: return false
            beaconCache[location] = Beacon(value)
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
        return true
    }

    /**
     * Removes a beacon at the location given.
     * @param x The x coordinate of the beacon.
     * @param y The y coordinate of the beacon.
     * @param z The z coordinate of the beacon.
     * @param world The world of the beacon.
     * @return true if the beacon was removed, false if there was no beacon at that location.
     */
    override fun removeBeacon(x: Int, y: Int, z: Int, world: String): Boolean {
        return removeBeacon(Location(Bukkit.getWorld(world), x.toDouble(), y.toDouble(), z.toDouble()))
    }

    /**
     * Removes a beacon at the location given.
     * @param location The location of the beacon.
     * @return true if the beacon was removed, false if there was no beacon at that location.
     * @see #removeBeacon(int, int, int, String)
     */
    override fun removeBeacon(location: Location): Boolean {
        if(!beaconCache.containsKey(location))return false
        try {
            transaction {
                val condition = Op.build {
                    BeaconLocation.x eq location.blockX and
                            (BeaconLocation.y eq location.blockY) and
                            (BeaconLocation.z eq location.blockZ) and
                            (BeaconLocation.world eq (location.world?.name ?: "world"))
                }
                val beaconData = BeaconLocationModel.find(condition).firstOrNull() ?: return@transaction false
                beaconData.delete()
                beaconCache.remove(location)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
        return true
    }

    /**
     * Checks if there is a beacon at the location given.
     * @param x The x coordinate of the beacon.
     * @param y The y coordinate of the beacon.
     * @param z The z coordinate of the beacon.
     * @param world The world of the beacon.
     * @return true if there is a beacon at the location, false if there is not.
     */
    override fun isBeacon(x: Int, y: Int, z: Int, world: String): Boolean {
        return isBeacon(Location(Bukkit.getWorld(world), x.toDouble(), y.toDouble(), z.toDouble()))
    }

    /**
     * Checks if there is a beacon at the location given.
     * @param location The location of the beacon.
     * @return true if there is a beacon at the location, false if there is not.
     */
    override fun isBeacon(location: Location): Boolean {
        return beaconCache.containsKey(location)
    }
}