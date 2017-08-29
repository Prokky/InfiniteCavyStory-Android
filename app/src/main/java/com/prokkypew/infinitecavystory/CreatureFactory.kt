package com.prokkypew.infinitecavystory

import android.graphics.Color
import com.prokkypew.infinitecavystory.creatures.Creature
import com.prokkypew.infinitecavystory.creatures.ai.BatAi
import com.prokkypew.infinitecavystory.creatures.ai.FungusAi
import com.prokkypew.infinitecavystory.creatures.ai.PlayerAi
import com.prokkypew.infinitecavystory.creatures.ai.ZombieAi
import com.prokkypew.infinitecavystory.utils.getIntResource
import com.prokkypew.infinitecavystory.utils.getString
import com.prokkypew.infinitecavystory.world.FieldOfView
import com.prokkypew.infinitecavystory.world.World


/**
 * Created by alexander.roman on 17.08.2017.
 */
class CreatureFactory(private val world: World) {
    fun newPlayer(messages: ArrayList<String>, fov: FieldOfView): Creature {
        val player = Creature(world, '@', Color.WHITE, getString(R.string.player), getIntResource(R.integer.player_hp), getIntResource(R.integer.player_mana), 5)
        player.ai = PlayerAi(player, messages, fov)
        world.addAtEmptyLocation(player, 0)
        return player
    }

    fun newFungus(depth: Int): Creature {
        val fungus = Creature(world, 'f', Color.GREEN, getString(R.string.fungus), getIntResource(R.integer.fungus_hp), getIntResource(R.integer.fungus_mana), 0)
        fungus.ai = FungusAi(fungus, this)
        world.addAtEmptyLocation(fungus, depth)
        return fungus
    }

    fun newBat(depth: Int): Creature {
        val bat = Creature(world, 'b', Color.YELLOW, getString(R.string.bat), getIntResource(R.integer.bat_hp), getIntResource(R.integer.bat_mana), 0)
        bat.ai = BatAi(bat)
        world.addAtEmptyLocation(bat, depth)
        return bat
    }

    fun newZombie(depth: Int, player: Creature): Creature {
        val zombie = Creature(world, 'z', Color.WHITE, "zombie", 50, 10, 10)
        zombie.ai = ZombieAi(zombie, player)
        world.addAtEmptyLocation(zombie, depth)
        return zombie
    }
}