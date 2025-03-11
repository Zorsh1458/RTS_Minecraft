package dev.zorsh

import dev.mryd.Main
import net.citizensnpcs.api.CitizensAPI
import net.citizensnpcs.api.npc.NPC
import org.bukkit.Location
import org.bukkit.entity.EntityType
import org.bukkit.scheduler.BukkitRunnable
import kotlin.random.Random

class NPCEntity(spawnPoint: Location) {
    private var active = true
    private val myNpc: NPC = CitizensAPI.getNPCRegistry().createNPC(EntityType.PLAYER, "Zorsh")

    init {
        myNpc.spawn(spawnPoint)
    }

    fun startLoop() {
        object : BukkitRunnable() {
            var firstIteration = true
            override fun run() {
                if (!active) {
                    this.cancel()
                }

                if (!myNpc.isSpawned) {
                    this.cancel()
                }

                if (myNpc.entity == null) {
                    this.cancel()
                }

                if (firstIteration) {
                    firstIteration = false

                    myNpc.entity.setRotation(0f, 0f)
                }

                var dest = myNpc.entity.location.add(Random.nextDouble() * 10 - 5, Random.nextDouble() * 10 - 5, Random.nextDouble() * 10 - 5)
                myNpc.entity.location.getNearbyPlayers(32.0).forEach { ent ->
                    if (!CitizensAPI.getNPCRegistry().isNPC(ent)) {
                        dest = ent.location.add(Random.nextDouble() * 2 - 1, Random.nextDouble() * 2 - 1, Random.nextDouble() * 2 - 1)
                    }
                }
                myNpc.navigator.setTarget(dest)
            }
        }.runTaskTimer(Main.instance, 0L, 5L)
    }

    fun delete() {
        active = false
        myNpc.despawn()
    }
}