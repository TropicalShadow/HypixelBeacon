package club.tesseract.hypixelbeacon.command

import club.tesseract.hypixelbeacon.HypixelBeacon
import club.tesseract.hypixelbeacon.config.ConfigManager
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabExecutor
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataType

object BeaconCommand: TabExecutor {

    val adminItemKey = NamespacedKey(HypixelBeacon.getPlugin(), "admin_item")


    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>
    ): MutableList<String> {
        val result = mutableListOf<String>()
        if(args.size <= 1) {
            if(sender.hasPermission("hypixelbeacon.command.reload") && "reload".startsWith(args[0], true)) {
                result.add("reload")
            }
            if(sender.hasPermission("hypixelbeacon.command.adminitem") && "adminitem".startsWith(args[0], true)) {
                result.add("adminitem")
            }
            if(sender.hasPermission("hypixelbeacon.command.balance") && "balance".startsWith(args[0], true)){
                result.add("balance")
                result.add("bal")
            }
        }
        return result
    }

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if(args.isEmpty()) {
            sender.sendMessage(Component.text("Usage: /$label <reload|adminitem|balance>", NamedTextColor.RED))
            return true
        }
        when(args[0].lowercase()) {
            "reload" -> {
                if(!sender.hasPermission("hypixelbeacon.command.reload")) {
                    sender.sendMessage(Component.text("You do not have permission to use this command.", NamedTextColor.RED))
                    return true
                }
                ConfigManager.loadConfigs()
                sender.sendMessage(Component.text("Reloaded HypixelBeacon.", NamedTextColor.GREEN))
            }
            "adminitem" -> {
                if(!sender.hasPermission("hypixelbeacon.command.adminitem")) {
                    sender.sendMessage(Component.text("You do not have permission to use this command.", NamedTextColor.RED))
                    return true
                }
                if(sender !is Player) {
                    sender.sendMessage(Component.text("You must be a player to use this command.", NamedTextColor.RED))
                    return true
                }

                val adminItem = ItemStack(Material.BEACON)
                adminItem.editMeta { it.persistentDataContainer[adminItemKey, PersistentDataType.BYTE] = 1 }
                adminItem.editMeta { it.displayName(Component.text("Admin Beacon", NamedTextColor.GOLD)) }
                adminItem.editMeta { it.lore(listOf(Component.text("Place this down to set a hypixel beacon", NamedTextColor.GRAY), Component.text("Break Hypixel Beacon to remove it", NamedTextColor.GRAY), Component.text("Admin Item Only", NamedTextColor.RED))) }

                sender.inventory.addItem(adminItem)
                sender.sendMessage(Component.text("Admin Item added to your inventory.", NamedTextColor.GREEN))
            }
            "balance", "bal" -> {
                if(!sender.hasPermission("hypixelbeacon.command.balance")) {
                    sender.sendMessage(Component.text("You do not have permission to use this command.", NamedTextColor.RED))
                    return true
                }
                if(sender !is Player) {
                    sender.sendMessage(Component.text("You must be a player to use this command.", NamedTextColor.RED))
                    return true
                }
                sender.sendMessage("Your balance is ${HypixelBeacon.getPlugin().economy.getBalance(sender.uniqueId)} ${HypixelBeacon.getPlugin().economy.currencyNamePlural}.")
            }
            else -> {
                sender.sendMessage(Component.text("Usage: /$label <reload|adminitem|balance>", NamedTextColor.RED))
            }
        }
        return true
    }
}