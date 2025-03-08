package dev.zorsh

import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.util.Vector

fun Location.findBlocksWithin(material: Material, radius: Int): MutableList<Location> {
    val result = mutableListOf<Location>()
    for (ox in -radius..radius) {
        for (oy in -radius..radius) {
            for (oz in -radius..radius) {
                val block = this + Vector(ox, oy, oz)

                if (block.block.blockData.material == material) {
                    result.add(block)
                }
            }
        }
    }
    return result
}