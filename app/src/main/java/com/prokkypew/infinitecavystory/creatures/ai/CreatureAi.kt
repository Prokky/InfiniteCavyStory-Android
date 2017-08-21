package com.prokkypew.infinitecavystory.creatures.ai

import com.prokkypew.infinitecavystory.creatures.Creature
import com.prokkypew.infinitecavystory.world.Line
import com.prokkypew.infinitecavystory.world.Tile


/**
 * Created by alexander.roman on 17.08.2017.
 */
open class CreatureAi(private var creature: Creature) {
    open fun onEnter(x: Int, y: Int, z: Int, tile: Tile) {
        if (tile.isGround) {
            creature.x = x
            creature.y = y
            creature.z = z
        }
    }

    open fun canSee(wx: Int, wy: Int, wz: Int): Boolean {
        if (creature.z != wz)
            return false

        if ((creature.x - wx) * (creature.x - wx) + (creature.y - wy) * (creature.y - wy) > creature.visionRadius * creature.visionRadius)
            return false

        return Line(creature.x, creature.y, wx, wy).none { !creature.realTile(it.x, it.y, wz).isGround && !(it.x == wx && it.y == wy) }
    }

    open fun rememberedTile(wx: Int, wy: Int, wz: Int): Tile {
        return Tile.UNKNOWN
    }
}