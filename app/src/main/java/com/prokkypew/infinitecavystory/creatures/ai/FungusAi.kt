package com.prokkypew.infinitecavystory.creatures.ai

import com.prokkypew.infinitecavystory.R
import com.prokkypew.infinitecavystory.creatures.Creature
import com.prokkypew.infinitecavystory.utils.getFloatResource
import com.prokkypew.infinitecavystory.utils.getIntResource
import com.prokkypew.infinitecavystory.utils.getString
import com.prokkypew.infinitecavystory.world.CreatureFactory


/**
 * Created by alexander.roman on 24.08.2017.
 */
class FungusAi(creature: Creature, private val factory: CreatureFactory) : CreatureAi(creature) {
    private var spreadCount: Int = 0

    override fun onUpdate() {
        if (spreadCount < getIntResource(R.integer.fungus_count)
                && Math.random() < getFloatResource(R.dimen.fungus_spread_prob))
            spread()
    }

    private fun spread() {
        val x = creature.x + (Math.random() * 11).toInt() - 5
        val y = creature.y + (Math.random() * 11).toInt() - 5

        if (!creature.canEnter(x, y, creature.z))
            return

        creature.doAction(getString(R.string.message_spawn_child))

        val child = factory.newFungus(creature.z)
        child.x = x
        child.y = y
        child.z = creature.z
        spreadCount++
    }
}