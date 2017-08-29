package com.prokkypew.infinitecavystory.creatures.ai

import com.prokkypew.infinitecavystory.creatures.Creature


/**
 * Created by alexander.roman on 29.08.2017.
 */
class ZombieAi(creature: Creature, private val player: Creature) : CreatureAi(creature) {

    override fun onUpdate() {
        if (Math.random() < 0.2)
            return

        if (creature.canSee(player.x, player.y, player.z))
            hunt(player)
        else
            wander()
    }
}