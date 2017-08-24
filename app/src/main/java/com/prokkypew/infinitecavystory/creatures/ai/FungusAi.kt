package com.prokkypew.infinitecavystory.creatures.ai

import com.prokkypew.infinitecavystory.CreatureFactory
import com.prokkypew.infinitecavystory.creatures.Creature


/**
 * Created by alexander.roman on 24.08.2017.
 */
class FungusAi(creature: Creature, private val factory: CreatureFactory) : CreatureAi(creature) {
    private var spreadcount: Int = 0

    override fun onUpdate() {
        if (spreadcount < 5 && Math.random() < 0.01)
            spread()
    }

    private fun spread() {
        val x = creature.x + (Math.random() * 11).toInt() - 5
        val y = creature.y + (Math.random() * 11).toInt() - 5

        if (!creature.canEnter(x, y, creature.z))
            return

        creature.doAction("spawn a child")

        val child = factory.newFungus(creature.z)
        child.x = x
        child.y = y
        child.z = creature.z
        spreadcount++
    }
}