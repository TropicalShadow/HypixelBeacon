package club.tesseract.hypixelbeacon;

import club.tesseract.hypixelbeacon.beacon.IBeaconManager;
import club.tesseract.hypixelbeacon.economy.IEconomy;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

/**
 * The api for HypixelBeacon.
 * @apiNote This class is not intended to be used by other plugins.
 * <pre>{@code
 *  HypixelBeaconAPI api = HypixelBeaconAPI.getInstance();
 *  IBeaconManager beaconManager = api.getBeaconManager();
 *  IEconomy economy = api.getEconomy();
 * }</pre>
 * Hooks into PlaceholderAPI: {@code ["hypixelbeacon_balance", "hypixelbeacon_currency", "hypixelbeacon_currency_plural"]}
 * @author TropicalShadow
 */
public abstract class HypixelBeaconAPI extends JavaPlugin{

    private static HypixelBeaconAPI instance;


    /**
     * @return The instance of the economy api.
     */
    @NotNull
    abstract public IEconomy getEconomy();

    /*
     * @return The instance of the beacon manager.
     */
    @NotNull
    abstract public IBeaconManager getBeaconManager();


    /**
     * @return The instance of the api.
     */
    @NotNull
    public static HypixelBeaconAPI getInstance() {
        if(instance == null){
            throw new IllegalStateException("HypixelBeaconAPI has not been initialized yet!");
        }
        return instance;
    }

    protected static void setInstance(@NotNull HypixelBeaconAPI plugin) {
        if(instance != null){
            throw new IllegalStateException("HypixelBeaconAPI has already been initialized!");
        }
        instance = plugin;
    }

}
