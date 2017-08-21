package com.prokkypew.infinitecavystory.world

/**
 * Created by alexander.roman on 18.08.2017.
 */
class Line(private var x0: Int, private var y0: Int, x1: Int, y1: Int) : Iterable<Point> {
    private val points: MutableList<Point>

    init {
        points = ArrayList()

        val dx = Math.abs(x1 - x0)
        val dy = Math.abs(y1 - y0)

        val sx = if (x0 < x1) 1 else -1
        val sy = if (y0 < y1) 1 else -1
        var err = dx - dy

        while (true) {
            points.add(Point(x0, y0, 0))

            if (x0 == x1 && y0 == y1)
                break

            val e2 = err * 2
            if (e2 > -dx) {
                err -= dy
                x0 += sx
            }
            if (e2 < dx) {
                err += dx
                y0 += sy
            }
        }
    }

    override fun iterator(): Iterator<Point> {
        return points.iterator()
    }
}