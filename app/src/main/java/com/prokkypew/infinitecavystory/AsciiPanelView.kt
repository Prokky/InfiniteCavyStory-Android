package com.prokkypew.infinitecavystory

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

/**
 * Created by alexander.roman on 11.08.2017.
 */
class AsciiPanelView : View {
    companion object {
        val panelWitdh: Int = 100
        val panelHeight: Int = 42
    }

    var tiles = Array(panelWitdh) { Array(panelHeight) { PanelTile(' ', Color.RED) } }
    var tileWidth: Float = 0f
    var tileHeight: Float = 0f
    var textPaint: Paint = Paint()

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    fun init() {
        tiles[35][12] = PanelTile('@', Color.BLUE)
        tiles[88][33] = PanelTile('#', Color.RED)
    }

    override fun onSizeChanged(xNew: Int, yNew: Int, xOld: Int, yOld: Int) {
        super.onSizeChanged(xNew, yNew, xOld, yOld)

        tileWidth = xNew.toFloat() / panelWitdh.toFloat()
        tileHeight = yNew.toFloat() / panelHeight.toFloat()
        textPaint.textSize = tileHeight
    }

    override fun onDraw(canvas: Canvas?) {
        for (w in 0..panelWitdh-1) {
            val posX = 0 + tileWidth * w
            for (h in 0..panelHeight-1) {
                val posY = 0 + tileHeight * h
                textPaint.color = tiles[w][h].color
                canvas?.drawText(tiles[w][h].char.toString(), posX, posY, textPaint)
            }
        }
    }

    inner class PanelTile constructor(val char: Char, val color: Int)
}