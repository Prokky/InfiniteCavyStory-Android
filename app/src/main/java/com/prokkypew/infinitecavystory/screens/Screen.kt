package com.prokkypew.infinitecavystory.screens

import android.view.MotionEvent
import com.prokkypew.asciipanelview.AsciiPanelView

/**
 * Created by prokk on 16.08.2017.
 */
interface Screen {
    fun displayOutput(panel: AsciiPanelView)
    fun respondToUserInput(touch: MotionEvent): Screen
}