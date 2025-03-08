package dev.zorsh

import org.bukkit.Material
import org.bukkit.generator.ChunkGenerator
import org.bukkit.generator.WorldInfo
import java.util.*
import kotlin.math.abs
import kotlin.math.floor
import kotlin.math.pow

class FractalChunkGenerator : ChunkGenerator() {
    override fun generateNoise(worldInfo: WorldInfo, random: Random, chunkX: Int, chunkZ: Int, chunkData: ChunkData) {
        for (x in 0..15) {
            for (z in 0..15) {
                for (y in chunkData.minHeight..chunkData.maxHeight) {
                    val cx = chunkX * 16 + x
                    val cz = chunkZ * 16 + z
                    var place = true
                    for (p in 0..3) {
                        val hx = cx / 3.0.pow(p.toDouble())
                        val hz = cz / 3.0.pow(p.toDouble())
                        val hy = y / 3.0.pow(p.toDouble())
                        if (floor(abs(hx)).toInt() % 2 == 0 && floor(abs(hz)).toInt() % 2 == 0) {
                            place = false
                        }
                        if (floor(abs(hx)).toInt() % 2 == 0 && floor(abs(hy)).toInt() % 2 == 0) {
                            place = false
                        }
                        if (floor(abs(hz)).toInt() % 2 == 0 && floor(abs(hy)).toInt() % 2 == 0) {
                            place = false
                        }
                    }
                    if (place) {
                        chunkData.setBlock(x, y, z, Material.STONE)
                    }
                }
            }
        }
    }
}