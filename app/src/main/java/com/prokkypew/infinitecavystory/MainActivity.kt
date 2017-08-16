package com.prokkypew.infinitecavystory

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Window
import android.view.WindowManager
import com.prokkypew.asciipanelview.AsciiPanelView
import com.prokkypew.infinitecavystory.screens.Screen
import com.prokkypew.infinitecavystory.screens.StartScreen
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), AsciiPanelView.OnCharClickedListener {
    lateinit var currentScreen: Screen
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_main)

        panelView.onCharClickedListener = this

        currentScreen = StartScreen()
        currentScreen.displayOutput(panelView)
    }

    override fun onCharClicked(x: Int?, y: Int?, char: AsciiPanelView.ColoredChar) {
        currentScreen = currentScreen.respondToUserInput(x, y, char)
        currentScreen.displayOutput(panelView)
    }
}
