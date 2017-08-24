package com.prokkypew.infinitecavystory.world

import android.graphics.Color

/**
 * Created by alexander.roman on 17.08.2017.
 */
enum class Tile constructor(private val glyph: Char, private val color: Int) {
    FLOOR('·', Color.parseColor("#CD853F")),
    WALL('#', Color.parseColor("#CD853F")),
    BOUNDS('x', Color.BLACK),
    STAIRS_DOWN('>', Color.WHITE),
    STAIRS_UP('<', Color.WHITE),
    UNKNOWN(' ', Color.WHITE);

    fun glyph(): Char {
        return glyph
    }

    fun color(): Int {
        return color
    }

    val isGround: Boolean
        get() = this != WALL && this != BOUNDS
}