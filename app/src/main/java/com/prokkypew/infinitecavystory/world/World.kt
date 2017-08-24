package com.prokkypew.infinitecavystory.world

import com.prokkypew.infinitecavystory.creatures.Creature


/**
 * Created by alexander.roman on 17.08.2017.
 */
class World(private val tiles: Array<Array<Array<Tile>>>) {
    val width = tiles.size
    val height = tiles[0].size
    val depth = tiles[0][0].size
    private val creatures = ArrayList<Creature>()

    fun addAtEmptyLocation(creature: Creature, z: Int) {
        var x: Int
        var y: Int

        do {
            x = (Math.random() * width).toInt()
            y = (Math.random() * height).toInt()
        } while (!tile(x, y, z).isGround || creature(x, y, z) != null)

        creature.x = x
        creature.y = y
        creature.z = z
        creatures.add(creature)
    }

    fun update() {
        val toUpdate = ArrayList(creatures)
        for (creature in toUpdate) {
            creature.update()
        }
    }

    fun remove(other: Creature) {
        creatures.remove(other)
    }

    fun creature(x: Int, y: Int, z: Int): Creature? {
        return creatures.firstOrNull { it.x == x && it.y == y && it.z == z }
    }

    fun tile(x: Int, y: Int, z: Int): Tile {
        return if (x < 0 || x >= width || y < 0 || y >= height || z < 0 || z >= depth)
            Tile.BOUNDS
        else
            tiles[x][y][z]
    }

    fun glyph(x: Int, y: Int, z: Int): Char {
        val creature = creature(x, y, z)
        if (creature != null)
            return creature.glyph

        return tile(x, y, z).glyph()
    }

    fun color(x: Int, y: Int, z: Int): Int {
        val creature = creature(x, y, z)
        if (creature != null)
            return creature.color

        return tile(x, y, z).color()
    }
}