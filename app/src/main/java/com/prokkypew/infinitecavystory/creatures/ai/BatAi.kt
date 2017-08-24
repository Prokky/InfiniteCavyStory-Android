package com.prokkypew.infinitecavystory.creatures.ai

import com.prokkypew.infinitecavystory.creatures.Creature


/**
 * Created by alexander.roman on 24.08.2017.
 */
class BatAi(creature: Creature) : CreatureAi(creature) {

    override fun onUpdate() {
        wander()
        wander()
    }
}