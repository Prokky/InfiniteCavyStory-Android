package com.prokkypew.infinitecavystory

import android.graphics.Color
import com.prokkypew.infinitecavystory.creatures.Creature
import com.prokkypew.infinitecavystory.creatures.ai.BatAi
import com.prokkypew.infinitecavystory.creatures.ai.FungusAi
import com.prokkypew.infinitecavystory.creatures.ai.PlayerAi
import com.prokkypew.infinitecavystory.world.FieldOfView
import com.prokkypew.infinitecavystory.world.World


/**
 * Created by alexander.roman on 17.08.2017.
 */
class CreatureFactory(private val world: World) {
    fun newPlayer(messages: ArrayList<String>, fov: FieldOfView): Creature {
        val player = Creature(world, '@', Color.WHITE, "player", 100, 20)
        player.ai = PlayerAi(player, messages, fov)
        world.addAtEmptyLocation(player, 0)
        return player
    }

    fun newFungus(depth: Int): Creature {
        val fungus = Creature(world, 'f', Color.GREEN, "fungus", 10, 0)
        fungus.ai = FungusAi(fungus, this)
        world.addAtEmptyLocation(fungus, depth)
        return fungus
    }

    fun newBat(depth: Int): Creature {
        val bat = Creature(world, 'b', Color.YELLOW, "bat", 15, 5)
        bat.ai = BatAi(bat)
        world.addAtEmptyLocation(bat, depth)
        return bat
    }
}