package com.prokkypew.infinitecavystory

import android.graphics.Color
import com.prokkypew.infinitecavystory.creatures.Creature
import com.prokkypew.infinitecavystory.creatures.ai.PlayerAi
import com.prokkypew.infinitecavystory.world.FieldOfView
import com.prokkypew.infinitecavystory.world.World


/**
 * Created by alexander.roman on 17.08.2017.
 */
class StuffFactory(private val world: World) {
    fun newPlayer(fov: FieldOfView): Creature {
        val player = Creature(world, '@', Color.WHITE)
        player.ai = PlayerAi(player, fov)
        world.addAtEmptyLocation(player, 0)
        return player
    }
}