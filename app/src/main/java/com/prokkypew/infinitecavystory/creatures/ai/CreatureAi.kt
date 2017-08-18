package com.prokkypew.infinitecavystory.creatures.ai

import com.prokkypew.infinitecavystory.creatures.Creature
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
}