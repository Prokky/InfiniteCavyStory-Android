package com.prokkypew.infinitecavystory

import android.graphics.Color
import com.prokkypew.asciipanelview.AsciiPanelView
import com.prokkypew.infinitecavystory.creatures.Creature
import com.prokkypew.infinitecavystory.screens.PlayScreen


/**
 * Created by alexander.roman on 22.08.2017.
 */
class Gui(private var panel: AsciiPanelView, private var player: Creature) {
    fun displayStats() {
        val hp = String.format("%d/%d hp", player.hp, player.maxHp)
        val mana = String.format("%d/%d mana", player.mana, player.maxMana)
        val hunger = String.format("%s", player.hunger())

        var hpColor: Int = Color.GREEN
        if (player.hp / player.maxHp < 0.5)
            hpColor = Color.YELLOW
        else if (player.hp / player.maxHp < 0.3)
            hpColor = Color.RED
        panel.writeString(hp, PlayScreen.PLAY_WIDTH + 1, 0, hpColor, Color.BLACK)
        panel.writeString(mana, PlayScreen.PLAY_WIDTH + 1, 1, Color.CYAN, Color.BLACK)

        var hungerColor: Int = Color.WHITE
        if (hunger == "Hungry")
            hungerColor = Color.YELLOW
        else if (hunger == "Starving")
            hungerColor = Color.RED
        panel.writeString("Satiety:" + hunger, PlayScreen.PLAY_WIDTH + 1, 2, hungerColor)
    }

    fun displayMessages(messages: ArrayList<String>) {
        val top = panel.panelHeight - messages.size
        for (i in messages.indices) {
            val x = PlayScreen.PLAY_WIDTH / 2 - messages[i].length / 2
            panel.writeStringWithPos(messages[i], x, top + i)
        }
    }
}