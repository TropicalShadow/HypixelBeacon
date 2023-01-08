package club.tesseract.hypixelbeacon.beacon;

import com.google.common.collect.ImmutableList;
import net.kyori.adventure.Adventure;
import net.kyori.adventure.identity.Identified;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface IBeacon {

    /**
     * @return The location of the beacon.
     */
    @NotNull
    Location getLocation();

    /**
     * @return The last timestamp that the beacon was mined.
     * @apiNote -1 if the beacon has never been mined, else the timestamp in seconds.
     */
    long lastMined();

    /**
     * @return true if the beacon has been mined, false if it has not.
     */
    boolean hasBeenMined();

    /**
     * Sets the beacons last mined timestamp to the current time.
     * Sets hasBeenMined to true.
     * Sets block material to air.
     * @param identified Either the player who mined the beacon, or null for the console.
     * @return true if the beacon was mined, false if the beacon has already been mined.
     */
    boolean mine(@Nullable Identified identified);

    /**
     * attempts to place the beacon at the location.
     * @param identified Either the player who placed the beacon, or null for the console.
     * @return true if the beacon was placed, false if the beacon has already been placed.
     */
    boolean place(@Nullable Identified identified);

}
