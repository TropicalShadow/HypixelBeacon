package club.tesseract.hypixelbeacon.beacon;

import com.google.common.collect.ImmutableList;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface IBeaconManager {

    /**
     * Gets a list of all the beacons on the server.
     * @apiNote This is a clone of the list, so any changes to the list will not affect the actual list.
     * @return A list of all the beacons on the server.
     */
    @NotNull
    ImmutableList<@NotNull IBeacon> getBeacons();

    /**
     * Gets the beacon at the specified location.
     * @param location The location of the beacon.
     * @return The beacon at the specified location, or null if there is no beacon at that location.
     */
    @Nullable
    IBeacon getBeacon(@NotNull Location location);

    /**
     * Gets the beacon at the specified location.
     * @param x The x coordinate of the beacon.
     * @param y The y coordinate of the beacon.
     * @param z The z coordinate of the beacon.
     * @param world The world of the beacon.
     * @return The beacon at the specified location, or null if there is no beacon at that location.
     */
    @Nullable
    IBeacon getBeacon(int x, int y, int z, @NotNull String world);

    /**
     * Sets a beacon at the location given.
     * @param x The x coordinate of the beacon.
     * @param y The y coordinate of the beacon.
     * @param z The z coordinate of the beacon.
     * @param world The world of the beacon.
     * @return true if the beacon was set, false if there was already a beacon at that location.
     */
    boolean addBeacon(int x, int y, int z, @NotNull String world);

    /**
     * Sets a beacon at the location given.
     * @param location The location of the beacon.
     * @return true if the beacon was set, false if there was already a beacon at that location.
     * @see #addBeacon(int, int, int, String)
     */
    boolean addBeacon(@NotNull Location location);

    /**
     * Removes a beacon at the location given.
     * @param x The x coordinate of the beacon.
     * @param y The y coordinate of the beacon.
     * @param z The z coordinate of the beacon.
     * @param world The world of the beacon.
     * @return true if the beacon was removed, false if there was no beacon at that location.
     */
    boolean removeBeacon(int x, int y, int z, @NotNull String world);

    /**
     * Removes a beacon at the location given.
     * @param location The location of the beacon.
     * @return true if the beacon was removed, false if there was no beacon at that location.
     * @see #removeBeacon(int, int, int, String)
     */
    boolean removeBeacon(@NotNull Location location);

    /**
     * Checks if there is a beacon at the location given.
     * @param x The x coordinate of the beacon.
     * @param y The y coordinate of the beacon.
     * @param z The z coordinate of the beacon.
     * @param world The world of the beacon.
     * @return true if there is a beacon at the location, false if there is not.
     */
    boolean isBeacon(int x, int y, int z, @NotNull String world);

    /**
     * Checks if there is a beacon at the location given.
     * @param location The location of the beacon.
     * @return true if there is a beacon at the location, false if there is not.
     */
    boolean isBeacon(@NotNull Location location);

}
