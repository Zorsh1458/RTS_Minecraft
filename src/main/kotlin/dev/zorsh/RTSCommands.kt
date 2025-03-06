package dev.zorsh

import dev.mryd.Main
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.entity.EntityType
import org.bukkit.entity.Player
import org.bukkit.scheduler.BukkitRunnable
import org.bukkit.util.StringUtil
import org.bukkit.util.Vector


class RTSCommands : CommandExecutor, TabCompleter {
    var miniMessage: MiniMessage = MiniMessage.miniMessage()

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender is Player) {
            if (command.name.equals("rts", ignoreCase = true)) {
                when (args.getOrNull(0)) {
                    "test" -> {
                        sender.sendMessage(miniMessage.deserialize("<green>Executing test RTS command!"))
                        val entityId = spawnFakeEntity(sender.location, Vector(0.0, 0.5, 0.0), EntityType.ARMOR_STAND, sender)
                        var index = 0
                        object : BukkitRunnable() {
                            override fun run() {
                                index++

                                if (index > 100) {
                                    sender.sendMessage(miniMessage.deserialize("<red>Entity teleport stopped!"))
                                    this.cancel()
                                }

                                teleportFakeEntity(entityId, sender.location + Vector(0.0, 2.0, 0.0), sender)
                            }
                        }.runTaskTimer(Main.instance, 0L, 1L)
                    }
                }
            }
        }
        return true
    }

    override fun onTabComplete(sender: CommandSender, command: Command, label: String, args: Array<out String>): List<String> {
        if (args.isEmpty()) {
            return listOf("test")
        }

        val availableSubCommands = listOf("test")
        return StringUtil.copyPartialMatches(args[0], availableSubCommands, mutableListOf())
    }
}