package com.prokkypew.infinitecavystory.world

import android.graphics.Color

/**
 * Created by alexander.roman on 17.08.2017.
 */
enum class Tile constructor(private val glyph: Char, private val color: Int, private val description: String) {
    FLOOR('·', Color.parseColor("#CD853F"), "A dirt and rock cave floor."),
    WALL('▒', Color.parseColor("#CD853F"), "A dirt and rock cave wall."),
    BOUNDS('x', Color.BLACK, "Beyond the edge of the world."),
    STAIRS_DOWN('>', Color.WHITE, "A stone staircase that goes down."),
    STAIRS_UP('<', Color.WHITE, "A stone staircase that goes up."),
    UNKNOWN(' ', Color.WHITE, "(unknown)");

    fun glyph(): Char {
        return glyph
    }

    fun color(): Int {
        return color
    }

    fun details(): String {
        return description
    }

    val isGround: Boolean
        get() = this != WALL && this != BOUNDS
}