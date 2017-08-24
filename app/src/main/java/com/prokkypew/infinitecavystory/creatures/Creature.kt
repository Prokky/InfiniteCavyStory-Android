package com.prokkypew.infinitecavystory.creatures

import com.prokkypew.infinitecavystory.creatures.ai.CreatureAi
import com.prokkypew.infinitecavystory.world.Tile
import com.prokkypew.infinitecavystory.world.World


/**
 * Created by alexander.roman on 17.08.2017.
 */
class Creature(private val world: World, val glyph: Char, val color: Int, val name: String, var maxHp: Int, var maxMana: Int) {

    var x: Int = 0
    var y: Int = 0
    var z: Int = 0
    var ai: CreatureAi? = null
    val visionRadius: Int = 9
    var mana = maxMana
    var hp = maxHp
    var maxFood: Int = 1000
    var food: Int = maxFood / 3 * 2

    fun moveBy(mx: Int, my: Int, mz: Int) {
        if (mx == 0 && my == 0 && mz == 0)
            return

        val tile = world.tile(x + mx, y + my, z + mz)
        ai?.onEnter(x + mx, y + my, z + mz, tile)
    }

    fun canSee(wx: Int, wy: Int, wz: Int): Boolean {
        if (ai == null)
            return false
        return ai!!.canSee(wx, wy, wz)
    }


    fun realTile(wx: Int, wy: Int, wz: Int): Tile {
        return world.tile(wx, wy, wz)
    }

    fun notify(message: String, vararg params: Any) {
        ai?.onNotify(String.format(message, *params))
    }

    fun hunger(): String {
        return when {
            food < maxFood * 0.10 -> "Starving"
            food < maxFood * 0.25 -> "Hungry"
            food > maxFood * 0.90 -> "Stuffed"
            food > maxFood * 0.75 -> "Full"
            else -> "Fine"
        }
    }
}