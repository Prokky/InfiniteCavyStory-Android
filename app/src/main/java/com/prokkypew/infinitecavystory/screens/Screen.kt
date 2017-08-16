package com.prokkypew.infinitecavystory.screens

import com.prokkypew.asciipanelview.AsciiPanelView
import com.prokkypew.infinitecavystory.MainApplication

/**
 * Created by prokk on 16.08.2017.
 */
interface Screen {
    fun displayOutput(panel: AsciiPanelView)
    fun respondToUserInput(x: Int?, y: Int?, char: AsciiPanelView.ColoredChar): Screen
}

fun getString(stringId: Int): String{
    return MainApplication.context.getString(stringId)
}