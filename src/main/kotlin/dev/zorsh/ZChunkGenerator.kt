package dev.zorsh

import dev.mryd.Main
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.util.Vector
import org.bukkit.World
import org.bukkit.block.data.BlockData
import org.bukkit.generator.ChunkGenerator
import org.bukkit.generator.WorldInfo
import org.bukkit.util.noise.PerlinNoiseGenerator
import org.bukkit.util.noise.SimplexNoiseGenerator
import java.util.*
import kotlin.collections.HashMap
import kotlin.math.abs
import kotlin.math.floor
import kotlin.math.pow

class ZChunkGenerator : ChunkGenerator() {

    fun remap(value: Double, minIn: Double, maxIn: Double, minOut: Double, maxOut: Double): Double {
        var res = value
        res -= minIn
        res /= (maxIn-minIn)
        res *= (maxOut-minOut)
        res += minOut
        return res
    }

//    private fun getVectorNoise(v: Vector): Double {
//        return PerlinNoiseGenerator.getNoise(v.x, v.y, v.z)
//    }

    private fun getWorldNoise(x: Double, y: Double, z: Double): Double {
        val height = 64.0
        return (
                    remap(PerlinNoiseGenerator.getNoise(x / 10, y, z / 10), -1.0, 1.0, 0.0, 100.0) +
                    remap(PerlinNoiseGenerator.getNoise(x / 5, y, z / 5), -1.0, 1.0, 0.0, 40.0) +
                    remap(PerlinNoiseGenerator.getNoise(x, y, z), -1.0, 1.0, 0.0, 5.0) +
                    remap(PerlinNoiseGenerator.getNoise(x * 3, y, z * 3), -1.0, 1.0, 0.0, 1.0) +
                            height)
    }

    override fun generateNoise(worldInfo: WorldInfo, random: Random, chunkX: Int, chunkZ: Int, chunkData: ChunkData) {
        for (cx in 0..15) {
            for (cz in 0..15) {
                for (y in chunkData.minHeight..chunkData.maxHeight) {
                    if (chunkX == 0 && chunkZ == 0 && y == 300) {
                        chunkData.setBlock(cx, y, cz, Material.GLASS)
                    } else {
                        val x = chunkX * 16 + cx
                        val z = chunkZ * 16 + cz
                        val nx = x * 0.05 + 1000000.0
                        val ny = y * 0.05 + 1000000.0
                        val nz = z * 0.05 + 1000000.0
                        if (PerlinNoiseGenerator.getNoise(nx * 50.0, 0.0, nz * 50.0) > 0.95) {
                            if (y < 280) {
                                chunkData.setBlock(cx, y, cz, Material.GOLD_BLOCK)
                            }
                        }
                        else if (PerlinNoiseGenerator.getNoise(nx * 50.0, 0.0, nz * 50.0) > 0.8) {
                            if (y < 280) {
                                chunkData.setBlock(cx, y, cz, Material.EMERALD_BLOCK)
                            }
                        }
                        else if (PerlinNoiseGenerator.getNoise(nx * 50.0, 0.0, nz * 50.0) > 0.65) {
                            if (y < 280) {
                                chunkData.setBlock(cx, y, cz, Material.DIAMOND_BLOCK)
                            }
                        }
                        else if (y <= getWorldNoise(nx, 0.0, nz)) {
                            chunkData.setBlock(cx, y, cz, Material.BROWN_TERRACOTTA)
                        }
                    }
//                        val facing = Vector(x, y, z) - Vector(0, 128, 0)
//                        val noiseVector = facing / facing.length() + Vector(1000000.0, 1000000.0, 1000000.0)
//
//                        if (facing.length() < remap(
//                                getVectorNoise(noiseVector * 5.0) * 4.0 +
//                                        getVectorNoise(noiseVector * 10.0) * 2.0 +
//                                        getVectorNoise(noiseVector * 15.0)
//                                , -7.0, 7.0, 64.0, 128.0)) {
//                            chunkData.setBlock(cx, y, cz, Material.STONE)
//                        }
//                        else if (facing.length() < 96.0) {
//                            chunkData.setBlock(cx, y, cz, Material.BLUE_STAINED_GLASS)
//                        }
//                    }



//                    val cx = chunkX * 16 + x
//                    val cz = chunkZ * 16 + z
//                    var place = true
//                    for (p in 0..4) {
//                        val hx = cx / 3.0.pow(p.toDouble())
//                        val hz = cz / 3.0.pow(p.toDouble())
//                        val hy = y / 3.0.pow(p.toDouble())
//                        if (floor(abs(hx)).toInt() % 2 == 0 && floor(abs(hz)).toInt() % 2 == 0) {
//                            place = false
//                        }
//                        if (floor(abs(hx)).toInt() % 2 == 0 && floor(abs(hy)).toInt() % 2 == 0) {
//                            place = false
//                        }
//                        if (floor(abs(hz)).toInt() % 2 == 0 && floor(abs(hy)).toInt() % 2 == 0) {
//                            place = false
//                        }
//                    }
//                    if (place) {
//                        chunkData.setBlock(x, y, z, Material.STONE)
//                    }
                }
            }
        }
    }
//                for (y in -64..128) {
//                    val nx = (chunkX*16.0+x) * 0.05+10000
//                    val ny = y * 0.03+10000
//                    val nz = (chunkZ*16.0+z) * 0.05+10000
//                    if (PerlinNoiseGenerator.getNoise(nx, ny, nz) > -0.4) {
//                        if (y == 128) {
//                            chunkData.setBlock(x, y, z, Material.GRASS_BLOCK)
//                        }
//                        else if (y > 126) {
//                            chunkData.setBlock(x, y, z, Material.DIRT)
//                        }
//                        else if (y > 122 && PerlinNoiseGenerator.getNoise(nx*10, ny+10000, nz*10) > 0.0) {
//                            chunkData.setBlock(x, y, z, Material.DIRT)
//                        }
//                        else if (y < -60) {
//                            chunkData.setBlock(x, y, z, Material.BEDROCK)
//                        }
//                        else if (y < -32 && PerlinNoiseGenerator.getNoise(nx*10, ny+10000, nz*10) > 0.0) {
//                            chunkData.setBlock(x, y, z, Material.DEEPSLATE)
//                        }
//                        else {
//                            chunkData.setBlock(x, y, z, Material.STONE)
//                        }
//                    }
//                    else if (y < 0) {
//                        chunkData.setBlock(x, y, z, Material.LAVA)
//                    }
//                }
//                if (chunkX !in -1..2 || chunkZ !in -1..2) {
//                    if (chunkX in -4..5 && chunkZ in -4..5) {
//                        for (y in 129..256) {
//                            val nx = (chunkX * 16.0 + x) * 0.1
//                            val ny = y * 0.03
//                            val nz = (chunkZ * 16.0 + z) * 0.1
//                            if (PerlinNoiseGenerator.getNoise(nx + 30000, ny * 0.04 + 30000.0, nz + 30000) > 0.0) {
//                                if (PerlinNoiseGenerator.getNoise(nx + 30000, ny + 15000, nz + 30000) > 0.7) {
//                                    chunkData.setBlock(x, y, z, Material.MOSSY_COBBLESTONE)
//                                } else if (PerlinNoiseGenerator.getNoise(nx + 30000, ny + 15050, nz + 30000) > 0.5) {
//                                    chunkData.setBlock(x, y, z, Material.MOSSY_STONE_BRICKS)
//                                } else if (PerlinNoiseGenerator.getNoise(nx + 30000, ny + 15100, nz + 30000) > 0) {
//                                    chunkData.setBlock(x, y, z, Material.STONE_BRICKS)
//                                } else {
//                                    chunkData.setBlock(x, y, z, Material.STONE)
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//        }
//    }

//    override fun generateNoise(worldInfo: WorldInfo, random: Random, chunkX: Int, chunkZ: Int, chunkData: ChunkData) {
//        for (x in 0..15) {
//            for (z in 0..15) {
//                val nx = (chunkX*16.0+x) * 0.02+10000
//                val nz = (chunkZ*16.0+z) * 0.02+10000
//                var y = chunkData.minHeight
//                val waterLevel = 7
//                var height = (((SimplexNoiseGenerator.getNoise(nx, nz)+1.0)/2.0).pow(1.4)*20.0).toInt()
//                height += (PerlinNoiseGenerator.getNoise(nx*2, nz*2)*5.0).toInt()
//                height += (PerlinNoiseGenerator.getNoise(nx*4, nz*4)*2.0).toInt()
//                while (y < height && y < chunkData.maxHeight) {
//                    chunkData.setBlock(x, y, z, Material.STONE)
//                    y++
//                }
//                if (y < waterLevel+1) {
//                    chunkData.setBlock(x, y, z, Material.SAND)
//                    y++
//                }
//                else {
//                    chunkData.setBlock(x, y, z, Material.GRASS_BLOCK)
//                }
//                while (y < waterLevel && y < chunkData.maxHeight) {
//                    chunkData.setBlock(x, y, z, Material.WATER)
//                    y++
//                }
//            }
//        }
//    }
}