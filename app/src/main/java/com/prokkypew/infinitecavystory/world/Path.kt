package com.prokkypew.infinitecavystory.world

import com.prokkypew.infinitecavystory.creatures.Creature


/**
 * Created by alexander.roman on 29.08.2017.
 */

class Path(creature: Creature, x: Int, y: Int) {

    private val pf: PathFinder = PathFinder()

    private var points: ArrayList<Point>? = null

    fun points(): List<Point>? {
        return points
    }

    init {
        points = pf.findPath(creature, Point(creature.x, creature.y, creature.z), Point(x, y, creature.z), 300)
    }
}