package com.prokkypew.infinitecavystory.creatures

import com.prokkypew.infinitecavystory.creatures.ai.CreatureAi
import com.prokkypew.infinitecavystory.world.World


/**
 * Created by alexander.roman on 17.08.2017.
 */
class Creature(private val world: World, val glyph: Char, val color: Int) {

    var x: Int = 0
    var y: Int = 0
    var z: Int = 0
    var ai: CreatureAi? = null

    fun moveBy(mx: Int, my: Int, mz: Int) {
        if (mx == 0 && my == 0 && mz == 0)
            return

        val tile = world.tile(x + mx, y + my, z + mz)
        ai?.onEnter(x + mx, y + my, z + mz, tile)
    }
}