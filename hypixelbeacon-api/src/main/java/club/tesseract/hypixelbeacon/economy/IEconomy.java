package club.tesseract.hypixelbeacon.economy;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.identity.Identified;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

/**
 *
 */
public interface IEconomy {

    @NotNull
    String getName();

    @NotNull
    String getCurrencyName();

    @NotNull
    String getCurrencyNamePlural();

    @NotNull
    String format(long amount);

    long getBalance(@NotNull UUID player);

    boolean setBalance(@NotNull UUID player, long amount);

    boolean addBalance(@NotNull UUID player, long amount);

    boolean removeBalance(@NotNull UUID player, long amount);

    boolean hasEnoughBalance(@NotNull UUID player, long amount);

}
