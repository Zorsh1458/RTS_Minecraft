package dev.zorsh

import com.comphenix.protocol.PacketType
import com.comphenix.protocol.events.PacketContainer
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerEntityTeleport
import dev.mryd.Main
import it.unimi.dsi.fastutil.ints.IntArrayList
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.entity.EntityType
import org.bukkit.entity.Player
import org.bukkit.util.Vector
import java.lang.reflect.InvocationTargetException
import java.util.*
import kotlin.math.max
import kotlin.math.min


private class Storage {
    companion object {
        var entityId = 20000
    }
}

fun applyFogOfWar(entityId: Int, player: Player) {
    val packet: PacketContainer = Main.protocolManager.createPacket(PacketType.Play.Server.ENTITY_METADATA)
    packet.integers.write(0, entityId)
    packet.strings.write(0, "s")
    packet.bytes.write(1, 3)
    try {
        Main.protocolManager.sendServerPacket(player, packet)
    } catch (e: InvocationTargetException) {
        e.printStackTrace()
    }
}

fun removeFakeEntity(entityId: Int, player: Player) {
    val packet = PacketContainer(PacketType.Play.Server.ENTITY_DESTROY)

    packet.modifier.write(0, IntArrayList(intArrayOf(entityId)))

    try {
        Main.protocolManager.sendServerPacket(player, packet)
    } catch (e: InvocationTargetException) {
        e.printStackTrace()
    }
}

fun lookFakeEntity(entityId: Int, yaw: Float, pitch: Float, player: Player): Int {
    val packet = PacketContainer(PacketType.Play.Server.ENTITY_LOOK)

    packet.integers.write(0, entityId)
    packet.bytes
        .write(0, (yaw / 4f * 3f).toInt().toByte())
        .write(1, (pitch / 4f * 3f).toInt().toByte())

    try {
        Main.protocolManager.sendServerPacket(player, packet)
    } catch (e: InvocationTargetException) {
        e.printStackTrace()
    }
    return entityId
}

fun teleportFakeEntity(entityId: Int, location: Location, player: Player): Int {
    val packet = PacketContainer(PacketType.Play.Server.ENTITY_TELEPORT)

    packet.integers.write(0, entityId)
    packet.doubles
        .write(0, location.x)
        .write(1, location.y)
        .write(2, location.z)

    try {
        Main.protocolManager.sendServerPacket(player, packet)
    } catch (e: InvocationTargetException) {
        e.printStackTrace()
    }
    return entityId
}

fun spawnFakeEntity(location: Location, vector: Vector, type: EntityType, player: Player): Int {
//    val entityId = (Random.nextDouble() * 10000 + 10000).toInt()

    val entityId = Storage.entityId
    Storage.entityId++

    val packet = PacketContainer(PacketType.Play.Server.SPAWN_ENTITY)

    packet.integers.write(0, entityId)
    packet.uuiDs.write(0, UUID.randomUUID())
    packet.entityTypeModifier.write(0, type)

    packet.doubles
        .write(0, location.x)
        .write(1, location.y)
        .write(2, location.z)

    packet.integers
        .write(1, convertVelocity(vector.x))
        .write(2, convertVelocity(vector.y))
        .write(3, convertVelocity(vector.z))

//    packet.integers
//        .write(4, (location.pitch * 256.0f / 360.0f).toInt())
//        .write(5, (location.yaw * 256.0f / 360.0f).toInt())

    try {
        Main.protocolManager.sendServerPacket(player, packet)
    } catch (e: InvocationTargetException) {
        e.printStackTrace()
    }
    return entityId
}

private fun convertVelocity(velocity: Double): Int {
    return (clamp(velocity, -3.9, 3.9) * 8000).toInt()
}

private fun clamp(targetNum: Double, min: Double, max: Double): Double {
    return max(min, min(targetNum, max))
}