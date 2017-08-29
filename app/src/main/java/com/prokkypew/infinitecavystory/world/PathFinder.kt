package com.prokkypew.infinitecavystory.world

import com.prokkypew.infinitecavystory.creatures.Creature
import java.util.*


/**
 * Created by alexander.roman on 29.08.2017.
 */
class PathFinder {
    private val open: ArrayList<Point> = ArrayList()
    private val closed: ArrayList<Point> = ArrayList()
    private val parents: HashMap<Point, Point> = HashMap()
    private val totalCost: HashMap<Point, Int> = HashMap()

    private fun heuristicCost(from: Point, to: Point): Int {
        return Math.max(Math.abs(from.x - to.x), Math.abs(from.y - to.y))
    }

    private fun costToGetTo(from: Point): Int {
        return if (parents[from] == null)
            0
        else
            1 + costToGetTo(parents[from]!!)
    }

    private fun totalCost(from: Point, to: Point): Int {
        if (totalCost.containsKey(from))
            return totalCost[from]!!

        val cost = costToGetTo(from) + heuristicCost(from, to)
        totalCost.put(from, cost)
        return cost
    }

    private fun reParent(child: Point, parent: Point) {
        parents.put(child, parent)
        totalCost.remove(child)
    }

    fun findPath(creature: Creature, start: Point, end: Point,
                 maxTries: Int): ArrayList<Point>? {
        open.clear()
        closed.clear()
        parents.clear()
        totalCost.clear()

        open.add(start)

        var tries = 0
        while (tries < maxTries && open.size > 0) {
            val closest = getClosestPoint(end)

            open.remove(closest)
            closed.add(closest)

            if (closest == end)
                return createPath(start, closest)
            else
                checkNeighbors(creature, end, closest)
            tries++
        }
        return null
    }

    private fun getClosestPoint(end: Point): Point {
        var closest = open[0]
        open
                .asSequence()
                .filter { totalCost(it, end) < totalCost(closest, end) }
                .forEach { closest = it }
        return closest
    }

    private fun checkNeighbors(creature: Creature, end: Point, closest: Point) {
        closest.neighbors8()
                .asSequence()
                .filter { !closed.contains(it) && !(!creature.canEnter(it.x, it.y, creature.z) && it != end) }
                .forEach {
                    if (open.contains(it))
                        reParentNeighborIfNecessary(closest, it)
                    else
                        reParentNeighbor(closest, it)
                }
    }

    private fun reParentNeighbor(closest: Point, neighbor: Point) {
        reParent(neighbor, closest)
        open.add(neighbor)
    }

    private fun reParentNeighborIfNecessary(closest: Point, neighbor: Point) {
        val originalParent = parents[neighbor]
        val currentCost = costToGetTo(neighbor).toDouble()
        reParent(neighbor, closest)
        val reparentCost = costToGetTo(neighbor).toDouble()

        if (reparentCost < currentCost)
            open.remove(neighbor)
        else
            reParent(neighbor, originalParent!!)
    }

    private fun createPath(start: Point, end: Point): ArrayList<Point> {
        var lEnd = end
        val path = ArrayList<Point>()

        while (lEnd != start) {
            path.add(lEnd)
            lEnd = parents[lEnd]!!
        }

        Collections.reverse(path)
        return path
    }
}