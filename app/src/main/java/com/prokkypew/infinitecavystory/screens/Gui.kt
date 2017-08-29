package com.prokkypew.infinitecavystory.screens

import android.graphics.Color
import com.prokkypew.asciipanelview.AsciiPanelView
import com.prokkypew.infinitecavystory.R
import com.prokkypew.infinitecavystory.creatures.Creature
import com.prokkypew.infinitecavystory.utils.getString


/**
 * Created by alexander.roman on 22.08.2017.
 */
class Gui(private var panel: AsciiPanelView, private var player: Creature) {

    private var promptClick: OnPromptClick? = null

    fun displayStats() {
        val hp = String.format(getString(R.string.gui_hp), player.hp, player.maxHp)
        val mana = String.format(getString(R.string.gui_mana), player.mana, player.maxMana)

        var hpColor: Int = Color.GREEN
        if (player.hp / player.maxHp < 0.5)
            hpColor = Color.YELLOW
        else if (player.hp / player.maxHp < 0.3)
            hpColor = Color.RED
        panel.writeString(hp, PlayScreen.PLAY_WIDTH + 1, 0, hpColor, Color.BLACK)
        panel.writeString(mana, PlayScreen.PLAY_WIDTH + 1, 1, Color.CYAN, Color.BLACK)

        var hungerColor: Int = Color.WHITE
        if (player.hunger() == getString(R.string.food_hungry))
            hungerColor = Color.YELLOW
        else if (player.hunger() == getString(R.string.food_starving))
            hungerColor = Color.RED
        panel.writeString(String.format(getString(R.string.gui_food), player.hunger()), PlayScreen.PLAY_WIDTH + 1, 2, hungerColor)
    }

    fun displayMessages(messages: ArrayList<String>) {
        val top = panel.panelHeight - messages.size
        for (i in messages.indices) {
            val x = PlayScreen.PLAY_WIDTH / 2 - messages[i].length / 2
            panel.writeStringWithPos(messages[i], x, top + i)
        }
    }

    fun displayControls(panel: AsciiPanelView, width: Int, height: Int) {
        for (control in ArrowControl.values()) {
            val posX = (width * control.xOffset - 4 * control.xOffset).toInt()
            val posY = (height * control.yOffset - 4 * control.yOffset).toInt()
            panel.clearRect(control.glyph, posX, posY, 4, 4, control.color, control.bgColor)
        }
    }

    enum class ArrowControl constructor(val glyph: Char, val color: Int, val bgColor: Int, val xOffset: Float, val yOffset: Float) {
        LEFT('←', Color.CYAN, Color.BLACK, 0f, 0.5f),
        RIGHT('→', Color.CYAN, Color.BLACK, 1f, 0.5f),
        DOWN('↓', Color.CYAN, Color.BLACK, 0.5f, 1f),
        UP('↑', Color.CYAN, Color.BLACK, 0.5f, 0f),

        LEFT_DOWN('↙', Color.CYAN, Color.BLACK, 0f, 1f),
        LEFT_UP('↖', Color.CYAN, Color.BLACK, 0f, 0f),
        RIGHT_DOWN('↘', Color.CYAN, Color.BLACK, 1f, 1f),
        RIGHT_UP('↗', Color.CYAN, Color.BLACK, 1f, 0f);
    }

    fun handleControl(x: Int, y: Int, char: Char, player: Creature) {
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

        if (xMove != 0 || yMove != 0) {
            player.moveBy(xMove, yMove, 0)
        } else if (promptClick != null && x > PlayScreen.PLAY_WIDTH && y >= panel.panelHeight - 3) {
            promptClick?.onPromptClick()
        }
        hidePrompt()
    }

    private fun hidePrompt() {
        panel.clearRect(' ', PlayScreen.PLAY_WIDTH, panel.panelHeight - 3, panel.panelWidth - PlayScreen.PLAY_WIDTH, 3)
        promptClick = null
    }

    fun showPrompt(text: String, click: OnPromptClick) {
        val x = (panel.panelWidth - PlayScreen.PLAY_WIDTH) / 2 + PlayScreen.PLAY_WIDTH - text.length / 2
        panel.clearRect(' ', PlayScreen.PLAY_WIDTH, panel.panelHeight - 3, panel.panelWidth - PlayScreen.PLAY_WIDTH, 3, Color.BLACK, Color.CYAN)
        panel.writeString(text, x, panel.panelHeight - 2, Color.BLACK, Color.CYAN)
        promptClick = click
    }

    interface OnPromptClick {
        fun onPromptClick()
    }
}