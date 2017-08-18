package com.prokkypew.infinitecavystory.world

import java.util.*

/**
 * Created by alexander.roman on 17.08.2017.
 */
class Point(var x: Int, var y: Int, var z: Int) {

    override fun hashCode(): Int {
        val prime = 31
        var result = 1
        result = prime * result + x
        result = prime * result + y
        result = prime * result + z
        return result
    }

    override fun equals(other: Any?): Boolean {
        if (this === other)
            return true
        if (other == null)
            return false
        if (other !is Point)
            return false
        if (x != other.x)
            return false
        if (y != other.y)
            return false
        return z == other.z
    }

    fun neighbors8(): List<Point> {
        val points = ArrayList<Point>()

        for (ox in -1..1) {
            (-1..1).filterNot { ox == 0 && it == 0 }
                    .mapTo(points) { Point(x + ox, y + it, z) }
        }

        Collections.shuffle(points)
        return points
    }
}