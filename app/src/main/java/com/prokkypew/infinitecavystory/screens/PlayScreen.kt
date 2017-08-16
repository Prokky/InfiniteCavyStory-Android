package com.prokkypew.infinitecavystory.screens

import com.prokkypew.asciipanelview.AsciiPanelView

/**
 * Created by prokk on 16.08.2017.
 */
class PlayScreen : Screen {
    override fun displayOutput(panel: AsciiPanelView) {

    }

    override fun respondToUserInput(x: Int?, y: Int?, char: AsciiPanelView.ColoredChar): Screen {
        return this
    }
}