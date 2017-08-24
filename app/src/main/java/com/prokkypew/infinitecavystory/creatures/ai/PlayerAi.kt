package com.prokkypew.infinitecavystory.creatures.ai

import com.prokkypew.infinitecavystory.creatures.Creature
import com.prokkypew.infinitecavystory.world.FieldOfView
import com.prokkypew.infinitecavystory.world.Tile


/**
 * Created by alexander.roman on 17.08.2017.
 */
class PlayerAi(creature: Creature, private val messages: ArrayList<String>, private val fov: FieldOfView) : CreatureAi(creature) {
    override fun canSee(wx: Int, wy: Int, wz: Int): Boolean {
        return fov.isVisible(wx, wy, wz)
    }

    override fun rememberedTile(wx: Int, wy: Int, wz: Int): Tile {
        return fov.tile(wx, wy, wz)
    }

    override fun onNotify(message: String) {
        messages.add(message)
        if (messages.size > 3) {
            messages.removeAt(0)
        }
    }

    override fun onGainLevel() {}
}