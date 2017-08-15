package com.prokkypew.infinitecavystory

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<AsciiPanelView>(R.id.panel).writeString("peWly")
        findViewById<AsciiPanelView>(R.id.panel).writeString("peWly", 0, 26)
    }
}
