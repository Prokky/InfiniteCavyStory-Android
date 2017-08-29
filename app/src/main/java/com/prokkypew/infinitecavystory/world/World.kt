package com.prokkypew.infinitecavystory.world

import com.prokkypew.infinitecavystory.R
import com.prokkypew.infinitecavystory.creatures.Creature
import com.prokkypew.infinitecavystory.items.Item
import com.prokkypew.infinitecavystory.utils.getIntResource


/**
 * Created by alexander.roman on 17.08.2017.
 */
class World(private val tiles: Array<Array<Array<Tile>>>) {
    val width = tiles.size
    val height = tiles[0].size
    val depth = tiles[0][0].size
    private var items = Array(width) { Array(height) { arrayOfNulls<Item>(depth) } }
    private val creatures = ArrayList<Creature>()
    private val creatureFactory = CreatureFactory(this)
    private val itemFactory = ItemFactory(this)

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

    fun addAtEmptyLocation(item: Item, z: Int) {
        var x: Int
        var y: Int
        do {
            x = (Math.random() * width).toInt()
            y = (Math.random() * height).toInt()
        } while (!tile(x, y, z).isGround || item(x, y, z) != null)

        items[x][y][z] = item
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

    fun item(x: Int, y: Int, z: Int): Item? {
        return items[x][y][z]
    }

    fun removeItem(x: Int, y: Int, z: Int) {
        items[x][y][z] = null
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

        val item = item(x, y, z)
        if (item != null)
            return item.glyph

        return tile(x, y, z).glyph()
    }

    fun color(x: Int, y: Int, z: Int): Int {
        val creature = creature(x, y, z)
        if (creature != null)
            return creature.color

        val item = item(x, y, z)
        if (item != null)
            return item.color

        return tile(x, y, z).color()
    }

    fun fill(player: Creature) {
        for (z in 0 until depth) {
            for (i in 0..getIntResource(R.integer.fungus_count)) {
                creatureFactory.newFungus(z)
            }
            for (i in 0..getIntResource(R.integer.bats_count)) {
                creatureFactory.newBat(z)
            }
            for (i in 0 until z * 2 + 10) {
                creatureFactory.newZombie(z, player)
            }

            for (i in 0 until width * height / 50) {
                itemFactory.newRock(z)
            }

            itemFactory.newFruit(z)
            itemFactory.newEdibleWeapon(z)
            itemFactory.newBread(z)
            itemFactory.randomArmor(z)
            itemFactory.randomWeapon(z)
            itemFactory.randomWeapon(z)
        }
        itemFactory.newVictoryItem(depth - 1)
    }


    fun createPlayer(messages: ArrayList<String>, fov: FieldOfView): Creature {
        return creatureFactory.newPlayer(messages, fov)
    }
}