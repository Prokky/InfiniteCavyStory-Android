package com.prokkypew.infinitecavystory.screens

import com.prokkypew.asciipanelview.AsciiPanelView
import com.prokkypew.infinitecavystory.R

/**
 * Created by prokk on 16.08.2017.
 */
class StartScreen : Screen {
    val bottomTextYPos = 20
    override fun displayOutput(panel: AsciiPanelView) {
        panel.writeCenter(getString(R.string.start_screen_header), 10)
        panel.writeCenter(getString(R.string.start_screen_middle_text), 12)
        panel.writeCenter(getString(R.string.start_screen_bottom_text), bottomTextYPos)
    }

    override fun respondToUserInput(x: Int?, y: Int?, char: AsciiPanelView.ColoredChar): Screen {
        if (y == bottomTextYPos && getString(R.string.start_screen_bottom_text).contains(char.glyph))
            return PlayScreen()
        else
            return this
    }
}