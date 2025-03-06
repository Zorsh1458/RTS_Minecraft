package dev.zorsh

import org.bukkit.Location
import org.bukkit.util.Vector
import org.joml.Quaternionf
import kotlin.math.pow

// VECTOR OPERATORS

operator fun Vector.plus(v2: Vector): Vector {
    return Vector(this.x + v2.x, this.y + v2.y, this.z + v2.z)
}

operator fun Vector.plus(v2: Location): Vector {
    return Vector(this.x + v2.x, this.y + v2.y, this.z + v2.z)
}

operator fun Vector.times(m: Double): Vector {
    return Vector(this.x * m, this.y * m, this.z * m)
}

operator fun Vector.minus(v2: Vector): Vector {
    return Vector(this.x - v2.x, this.y - v2.y, this.z - v2.z)
}

operator fun Vector.minus(v2: Location): Vector {
    return Vector(this.x - v2.x, this.y - v2.y, this.z - v2.z)
}

operator fun Vector.div(m: Double): Vector {
    return Vector(this.x / m, this.y / m, this.z / m)
}

// LOCATION OPERATORS

operator fun Location.plus(v2: Vector): Location {
    return Location(this.world, this.x + v2.x, this.y + v2.y, this.z + v2.z)
}

operator fun Location.plus(v2: Location): Location {
    return Location(this.world, this.x + v2.x, this.y + v2.y, this.z + v2.z)
}

operator fun Location.times(m: Double): Location {
    return Location(this.world, this.x * m, this.y * m, this.z * m)
}

operator fun Location.minus(v2: Vector): Location {
    return Location(this.world, this.x - v2.x, this.y - v2.y, this.z - v2.z)
}

operator fun Location.minus(v2: Location): Location {
    return Location(this.world, this.x - v2.x, this.y - v2.y, this.z - v2.z)
}

operator fun Location.div(m: Double): Location {
    return Location(this.world, this.x / m, this.y / m, this.z / m)
}

// HYI VECTOR OPERATORS

operator fun  org.joml.Vector3f.plus(v2: Vector):  org.joml.Vector3f {
    return  org.joml.Vector3f(this.x + v2.x.toFloat(), this.y + v2.y.toFloat(), this.z + v2.z.toFloat())
}

operator fun  org.joml.Vector3f.plus(v2: org.joml.Vector3f):  org.joml.Vector3f {
    return  org.joml.Vector3f(this.x + v2.x, this.y + v2.y, this.z + v2.z)
}

operator fun  org.joml.Vector3f.plus(v2: Location):  org.joml.Vector3f {
    return  org.joml.Vector3f(this.x + v2.x.toFloat(), this.y + v2.y.toFloat(), this.z + v2.z.toFloat())
}

operator fun  org.joml.Vector3f.times(m: Double):  org.joml.Vector3f {
    return  org.joml.Vector3f(this.x * m.toFloat(), this.y * m.toFloat(), this.z * m.toFloat())
}

operator fun  org.joml.Vector3f.minus(v2: Vector):  org.joml.Vector3f {
    return  org.joml.Vector3f(this.x - v2.x.toFloat(), this.y - v2.y.toFloat(), this.z - v2.z.toFloat())
}

operator fun  org.joml.Vector3f.minus(v2: Location):  org.joml.Vector3f {
    return  org.joml.Vector3f(this.x - v2.x.toFloat(), this.y - v2.y.toFloat(), this.z - v2.z.toFloat())
}

operator fun  org.joml.Vector3f.div(m: Double):  org.joml.Vector3f {
    return  org.joml.Vector3f(this.x / m.toFloat(), this.y / m.toFloat(), this.z / m.toFloat())
}

fun Quaternionf.distance(q: Quaternionf): Float {
    var dist = 0f
    dist += (this.x-q.x).pow(2f)
    dist += (this.y-q.y).pow(2f)
    dist += (this.z-q.z).pow(2f)
    dist += (this.w-q.w).pow(2f)
    return dist.pow(0.5f)
}

// VECTOR MATH

infix fun Vector.dot(v: Vector): Double {
    return this.x * v.x + this.y * v.y + this.z * v.z
}

infix fun Vector.dot(v: Location): Double {
    return this.x * v.x + this.y * v.y + this.z * v.z
}

infix fun Location.dot(v: Vector): Double {
    return this.x * v.x + this.y * v.y + this.z * v.z
}

infix fun Location.dot(v: Location): Double {
    return this.x * v.x + this.y * v.y + this.z * v.z
}

infix fun Vector.cross(v: Vector): Vector {
    return Vector(
        this.y * v.z - this.z * v.y,
        this.z * v.x - this.x * v.z,
        this.x * v.y - this.y * v.x
    )
}

infix fun Vector.cross(v: Location): Vector {
    return Vector(
        this.y * v.z - this.z * v.y,
        this.z * v.x - this.x * v.z,
        this.x * v.y - this.y * v.x
    )
}

infix fun Location.cross(v: Vector): Vector {
    return Vector(
        this.y * v.z - this.z * v.y,
        this.z * v.x - this.x * v.z,
        this.x * v.y - this.y * v.x
    )
}

infix fun Location.cross(v: Location): Vector {
    return Vector(
        this.y * v.z - this.z * v.y,
        this.z * v.x - this.x * v.z,
        this.x * v.y - this.y * v.x
    )
}