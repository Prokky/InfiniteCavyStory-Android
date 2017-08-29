package com.prokkypew.infinitecavystory.world

import android.graphics.Color
import com.prokkypew.infinitecavystory.R
import com.prokkypew.infinitecavystory.items.Item
import com.prokkypew.infinitecavystory.utils.getString


/**
 * Created by alexander.roman on 17.08.2017.
 */
class ItemFactory(private val world: World) {
    fun newRock(depth: Int): Item {
        val rock = Item(',', Color.YELLOW, getString(R.string.rock), null)
        rock.modifyThrownAttackValue(5)
        world.addAtEmptyLocation(rock, depth)
        return rock
    }

    fun newBread(depth: Int): Item {
        val item = Item('%', Color.YELLOW, getString(R.string.bread), null)
        item.modifyFoodValue(400)
        world.addAtEmptyLocation(item, depth)
        return item
    }

    fun newFruit(depth: Int): Item {
        val item = Item('%', Color.RED, getString(R.string.apple), null)
        item.modifyFoodValue(100)
        world.addAtEmptyLocation(item, depth)
        return item
    }

    fun newVictoryItem(depth: Int): Item {
        val item = Item('*', Color.WHITE, getString(R.string.teddy_bear), null)
        world.addAtEmptyLocation(item, depth)
        return item
    }

    fun newEdibleWeapon(depth: Int): Item {
        val item = Item(')', Color.YELLOW, getString(R.string.baguette), null)
        item.modifyAttackValue(3)
        item.modifyFoodValue(100)
        world.addAtEmptyLocation(item, depth)
        return item
    }

    fun newDagger(depth: Int): Item {
        val item = Item(')', Color.WHITE, getString(R.string.dagger), null)
        item.modifyAttackValue(5)
        item.modifyThrownAttackValue(5)
        world.addAtEmptyLocation(item, depth)
        return item
    }

    fun newSword(depth: Int): Item {
        val item = Item(')', Color.WHITE, getString(R.string.sword), null)
        item.modifyAttackValue(10)
        item.modifyThrownAttackValue(3)
        world.addAtEmptyLocation(item, depth)
        return item
    }

    fun newStaff(depth: Int): Item {
        val item = Item(')', Color.YELLOW, getString(R.string.staff), null)
        item.modifyAttackValue(5)
        item.modifyDefenseValue(3)
        item.modifyThrownAttackValue(3)
        world.addAtEmptyLocation(item, depth)
        return item
    }

    fun newBow(depth: Int): Item {
        val item = Item(')', Color.YELLOW, getString(R.string.bow), null)
        item.modifyAttackValue(1)
        item.modifyRangedAttackValue(5)
        world.addAtEmptyLocation(item, depth)
        return item
    }

    fun newLightArmor(depth: Int): Item {
        val item = Item('[', Color.GREEN, getString(R.string.tunic), null)
        item.modifyDefenseValue(2)
        world.addAtEmptyLocation(item, depth)
        return item
    }

    fun newMediumArmor(depth: Int): Item {
        val item = Item('[', Color.WHITE, getString(R.string.chainmail), null)
        item.modifyDefenseValue(4)
        world.addAtEmptyLocation(item, depth)
        return item
    }

    fun newHeavyArmor(depth: Int): Item {
        val item = Item('[', Color.WHITE, getString(R.string.platemail), null)
        item.modifyDefenseValue(6)
        world.addAtEmptyLocation(item, depth)
        return item
    }

    fun randomWeapon(depth: Int): Item {
        return when ((Math.random() * 3).toInt()) {
            0 -> newDagger(depth)
            1 -> newSword(depth)
            2 -> newBow(depth)
            else -> newStaff(depth)
        }
    }

    fun randomArmor(depth: Int): Item {
        return when ((Math.random() * 3).toInt()) {
            0 -> newLightArmor(depth)
            1 -> newMediumArmor(depth)
            else -> newHeavyArmor(depth)
        }
    }
}