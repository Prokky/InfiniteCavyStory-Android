package com.prokkypew.infinitecavystory

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MotionEvent
import android.view.View
import android.view.Window
import android.view.WindowManager
import com.prokkypew.infinitecavystory.screens.Screen
import com.prokkypew.infinitecavystory.screens.StartScreen
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnTouchListener {
    lateinit var currentScreen: Screen
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_main)

        panel.setOnTouchListener(this)

        currentScreen = StartScreen()
        currentScreen.displayOutput(panel)
    }

    override fun onTouch(p0: View, p1: MotionEvent): Boolean {
        currentScreen.displayOutput(panel)
        currentScreen = currentScreen.respondToUserInput(p1)
        return true
    }
}
