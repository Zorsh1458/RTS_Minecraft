package dev.zorsh

import dev.mryd.Main
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.Location
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
                sender.sendMessage(miniMessage.deserialize("<yellow>Executing ${args.getOrNull(0)} RTS command..."))
                when (args.getOrNull(0)) {
                    "createfog" -> {
                        RTSManager.createFogOfWar()
                    }
                    "cleardata" -> {
                        RTSManager.clearData()
                    }
                    "test" -> {
                        val entityId = RTSManager.fogOfWarEntity?.entityId ?: return true
                        //val ent = EntityType.TEXT_DISPLAY
                        //val entityId = spawnFakeEntity(sender.location, Vector(0.0, 0.5, 0.0), ent, sender)
                        //applyFogOfWar(entityId, sender)
                        var index = 0
                        object : BukkitRunnable() {
                            override fun run() {
                                index++

                                if (!sender.isOnline) {
                                    this.cancel()
                                }

                                if (index > 100) {
                                    //removeFakeEntity(entityId, sender)
                                    sender.sendMessage(miniMessage.deserialize("<red>Entity teleport stopped!"))
                                    this.cancel()
                                }

                                val location = Location(RTSManager.fogOfWarEntity?.world, sender.location.x, 64.0, sender.location.z, 0f, -90f)
                                teleportFakeEntity(entityId, location, sender)
                                lookFakeEntity(entityId, location.yaw, location.pitch, sender)
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
            return listOf("test", "createfog", "cleardata")
        }

        val availableSubCommands = listOf("test", "createfog", "cleardata")
        return StringUtil.copyPartialMatches(args[0], availableSubCommands, mutableListOf())
    }
}