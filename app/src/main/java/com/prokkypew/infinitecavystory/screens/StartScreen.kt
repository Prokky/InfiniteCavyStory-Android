package com.prokkypew.infinitecavystory.screens

import com.prokkypew.asciipanelview.AsciiPanelView
import com.prokkypew.infinitecavystory.R
import com.prokkypew.infinitecavystory.utils.getString

/**
 * Created by prokk on 16.08.2017.
 */
class StartScreen(panelView: AsciiPanelView) : Screen(panelView) {
    companion object {
        private val BOTTOM_TEXT_Y_POS = 20
    }

    override fun displayOutput() {
        panel.clear()
        panel.writeCenter(getString(R.string.start_screen_header), 10)
        panel.writeCenter(getString(R.string.start_screen_middle_text), 12)
        panel.writeCenter(getString(R.string.start_screen_bottom_text), BOTTOM_TEXT_Y_POS)
    }

    override fun respondToUserInput(x: Int?, y: Int?, char: AsciiPanelView.ColoredChar): Screen {
        return PlayScreen(panel)
    }
}