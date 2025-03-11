package dev.zorsh

import net.citizensnpcs.api.npc.NPC
import org.bukkit.Location

class NPCManager {
    companion object {
        private val NPCs = mutableSetOf<NPCEntity>()

        @JvmStatic
        fun spawnNpc(location: Location) {
            val npc = NPCEntity(location)
            NPCs.add(npc)
            npc.startLoop()
        }

        @JvmStatic
        fun clearNpcs() {
            NPCs.forEach { npc ->
                npc.delete()
            }
            NPCs.clear()
        }
    }
}