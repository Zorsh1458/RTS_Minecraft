package dev.zorsh

import org.joml.Vector3i
import kotlin.random.Random

class Structures {
    companion object {
        private var structuresList = hashMapOf<Vector3i, Structure>()

        @JvmStatic
        fun generateStructures(count: Int) {
            structuresList.clear()
            val radius = 60
            for (i in 1..count) {
                val x = Random.nextDouble() * radius
                val y = Random.nextDouble() * radius
                val z = Random.nextDouble() * radius
                val struct = Structure()
                struct.generateBlockMap()
                structuresList[Vector3i(x.toInt(), y.toInt(), z.toInt())] = struct
            }
        }
    }
}