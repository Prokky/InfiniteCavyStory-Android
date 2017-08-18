package com.prokkypew.infinitecavystory.screens

import com.prokkypew.asciipanelview.AsciiPanelView
import com.prokkypew.infinitecavystory.MainApplication

/**
 * Created by prokk on 16.08.2017.
 */
abstract class Screen(var panel: AsciiPanelView) {
    abstract fun displayOutput()
    abstract fun respondToUserInput(x: Int?, y: Int?, char: AsciiPanelView.ColoredChar): Screen
}

fun getString(stringId: Int): String {
    return MainApplication.context.getString(stringId)
}