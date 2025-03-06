package dev.zorsh

import com.comphenix.protocol.PacketType
import com.comphenix.protocol.events.PacketContainer
import dev.mryd.Main
import org.bukkit.Location
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