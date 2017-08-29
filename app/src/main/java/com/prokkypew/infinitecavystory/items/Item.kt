package com.prokkypew.infinitecavystory.items

import android.media.effect.Effect


/**
 * Created by alexander.roman on 29.08.2017.
 */
class Item(val glyph: Char, val color: Int, val name: String, private val appearance: String?) {

    fun appearance(): String {
        return appearance ?: name
    }

    private var foodValue: Int = 0

    fun foodValue(): Int {
        return foodValue
    }

    fun modifyFoodValue(amount: Int) {
        foodValue += amount
    }

    private var attackValue: Int = 0

    fun attackValue(): Int {
        return attackValue
    }

    fun modifyAttackValue(amount: Int) {
        attackValue += amount
    }

    private var defenseValue: Int = 0

    fun defenseValue(): Int {
        return defenseValue
    }

    fun modifyDefenseValue(amount: Int) {
        defenseValue += amount
    }

    private var thrownAttackValue: Int = 0

    fun thrownAttackValue(): Int {
        return thrownAttackValue
    }

    fun modifyThrownAttackValue(amount: Int) {
        thrownAttackValue += amount
    }

    private var rangedAttackValue: Int = 0

    fun rangedAttackValue(): Int {
        return rangedAttackValue
    }

    fun modifyRangedAttackValue(amount: Int) {
        rangedAttackValue += amount
    }

    private var quaffEffect: Effect? = null

    fun quaffEffect(): Effect? {
        return quaffEffect
    }

    fun setQuaffEffect(effect: Effect) {
        this.quaffEffect = effect
    }

    init {
        this.thrownAttackValue = 1
    }
}