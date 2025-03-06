package dev.zorsh

import org.bukkit.Material
import org.joml.Vector3i
import kotlin.random.Random

class Structure {
    private var blockMap = hashMapOf<Vector3i, Material>()

    fun generateBlockMap() {
        blockMap.clear()
        repeat(10) {
            val x = Random.nextDouble() * 5
            val y = Random.nextDouble() * 5
            val z = Random.nextDouble() * 5
            blockMap[Vector3i(x.toInt(), y.toInt(), z.toInt())] = Material.STONE
        }
    }
}