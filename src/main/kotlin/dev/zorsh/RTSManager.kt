package dev.zorsh

import dev.mryd.Main
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextColor
import org.bukkit.Bukkit
import org.bukkit.Color
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.entity.*
import org.bukkit.scheduler.BukkitRunnable
import org.bukkit.util.Transformation
import org.joml.Quaternionf
import org.joml.Vector3f
import kotlin.math.sin

class RTSManager {
    companion object {
        var gameActive = true
        var fogOfWarEntity: Entity? = null

        @JvmStatic
        fun startMainLoop() {
            var leftBlocks = mutableListOf<Location>()
            object : BukkitRunnable() {
                override fun run() {
                    if (!gameActive) {
                        this.cancel()
                    }

                    val world = Bukkit.getWorld("ZWorld") ?: return

                    val newBlocks = mutableListOf<Location>()
                    world.entities.forEach { ent ->
                        if (ent !is Player) {
                            val block = ent.location
                            if (!newBlocks.contains(block)) {
                                newBlocks.add(block)
                            }
                        }
                    }
                    leftBlocks.forEach { block ->
                        if (!newBlocks.contains(block)) {
                            world.players.forEach { pl ->
                                val checkLoc = pl.location
                                checkLoc.y = block.y
                                if (checkLoc.distance(block) < 64) {
                                    pl.sendBlockChange(block, Material.AIR.createBlockData())
                                }
                            }
                        }
                    }
                    newBlocks.forEach { block ->
                        if (!leftBlocks.contains(block)) {
                            world.players.forEach { pl ->
                                val checkLoc = pl.location
                                checkLoc.y = block.y
                                if (checkLoc.distance(block) < 32) {
                                    pl.sendBlockChange(block, Material.LIGHT.createBlockData())
                                }
                            }
                        }
                    }
                    leftBlocks = newBlocks
                }
            }.runTaskTimer(Main.instance, 0L, 1L)
        }

        @JvmStatic
        fun createFogOfWar() {
            if (fogOfWarEntity != null) {
                return
            }
            val world = Bukkit.getWorld("ZWorld") ?: return
            val location = Location(world, 0.0, 64.0, 0.0, 0f, -90f)
            fogOfWarEntity = world.spawnEntity(location, EntityType.TEXT_DISPLAY)
            val display = fogOfWarEntity as Display
            val textDisplay = fogOfWarEntity as TextDisplay
            display.transformation = Transformation(Vector3f(0f, -1000f, 0f), Quaternionf(0f, 0f, 0f, 1f), Vector3f(5000f, 5000f, 1f), Quaternionf(0f, 0f, 0f, 1f))
            textDisplay.text(Component.text("█").color(TextColor.color(0, 0, 0)))
            textDisplay.backgroundColor = Color.fromARGB(3, 0, 0, 0)
            textDisplay.isSeeThrough = true
            textDisplay.viewRange = 10000f
        }

        @JvmStatic
        fun clearData() {
            gameActive = false
            if (fogOfWarEntity != null) {
                fogOfWarEntity?.remove()
                fogOfWarEntity = null
            }
        }
    }
}