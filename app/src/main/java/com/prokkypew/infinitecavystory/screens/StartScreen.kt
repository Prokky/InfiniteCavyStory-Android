package com.prokkypew.infinitecavystory.screens

import android.view.MotionEvent
import com.prokkypew.asciipanelview.AsciiPanelView

/**
 * Created by prokk on 16.08.2017.
 */
class StartScreen : Screen {
    override fun displayOutput(panel: AsciiPanelView) {
        panel.writeCenter("INFINITE CAVY STORY", 10)
        panel.writeCenter("by Prokky", 12)
        panel.writeCenter("-- click to start --", 20)
    }

    override fun respondToUserInput(touch: MotionEvent): Screen {
        return this
    }
}