package com.prokkypew.infinitecavystory

import android.graphics.Color
import com.prokkypew.asciipanelview.AsciiPanelView
import com.prokkypew.infinitecavystory.creatures.Creature

/**
 * Created by alexander.roman on 22.08.2017.
 */
private enum class ArrowControl constructor(val glyph: Char, val color: Int, val bgColor: Int, val xOffset: Float, val yOffset: Float) {
    LEFT('←', Color.CYAN, Color.BLACK, 0f, 0.5f),
    RIGHT('→', Color.CYAN, Color.BLACK, 1f, 0.5f),
    DOWN('↓', Color.CYAN, Color.BLACK, 0.5f, 1f),
    UP('↑', Color.CYAN, Color.BLACK, 0.5f, 0f),

    LEFT_DOWN('↙', Color.CYAN, Color.BLACK, 0f, 1f),
    LEFT_UP('↖', Color.CYAN, Color.BLACK, 0f, 0f),
    RIGHT_DOWN('↘', Color.CYAN, Color.BLACK, 1f, 1f),
    RIGHT_UP('↗', Color.CYAN, Color.BLACK, 1f, 0f);
}

fun drawControls(panel: AsciiPanelView, width: Int, height: Int) {
    for (control in ArrowControl.values()) {
        var posX = (width * control.xOffset - 4 * control.xOffset).toInt()
        var posY = (height * control.yOffset - 4 * control.yOffset).toInt()
        panel.clearRect(control.glyph, posX, posY, 4, 4, control.color, control.bgColor)
    }
}

fun handleControl(char: Char, player: Creature) {
    var xMove = 0
    var yMove = 0

    if (char == ArrowControl.LEFT.glyph || char == ArrowControl.LEFT_DOWN.glyph || char == ArrowControl.LEFT_UP.glyph)
        xMove = -1
    else if (char == ArrowControl.RIGHT.glyph || char == ArrowControl.RIGHT_DOWN.glyph || char == ArrowControl.RIGHT_UP.glyph)
        xMove = 1

    if (char == ArrowControl.UP.glyph || char == ArrowControl.LEFT_UP.glyph || char == ArrowControl.RIGHT_UP.glyph)
        yMove = -1
    else if (char == ArrowControl.DOWN.glyph || char == ArrowControl.LEFT_DOWN.glyph || char == ArrowControl.RIGHT_DOWN.glyph)
        yMove = 1

    player.moveBy(xMove, yMove, 0)
}

