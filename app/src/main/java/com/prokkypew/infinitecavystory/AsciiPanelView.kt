package com.prokkypew.infinitecavystory

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.View


/**
 * An implementation of terminal view for oldschool games
 * @author Alexander Roman
 */

class AsciiPanelView : View {
    companion object {
        const val DEFAULT_PANEL_WIDTH: Int = 64
        const val DEFAULT_PANEL_HEIGHT: Int = 27
        const val DEFAULT_GLYPH_COLOR: Int = Color.BLACK
        const val DEFAULT_FONT: String = "font.ttf"
    }

    var panelWidth: Int = DEFAULT_PANEL_WIDTH
    var panelHeight: Int = DEFAULT_PANEL_HEIGHT
    var basicGlyphColor: Int = DEFAULT_GLYPH_COLOR
    lateinit var chars: Array<Array<ColoredChar>>
    var tileWidth: Float = 0f
    var tileHeight: Float = 0f
    var textPaint: Paint = Paint()
    var cursorX: Int = 0
    var cursorY: Int = 0
    var fontFamily: String = DEFAULT_FONT

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        readAttributes(attrs)
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        readAttributes(attrs)
        init()
    }

    fun readAttributes(attrs: AttributeSet) {
        val ta = context.obtainStyledAttributes(attrs, R.styleable.AsciiPanelView)
        try {
            panelWidth = ta.getInt(R.styleable.AsciiPanelView_panelWidth, DEFAULT_PANEL_WIDTH)
            panelHeight = ta.getInt(R.styleable.AsciiPanelView_panelHeight, DEFAULT_PANEL_HEIGHT)
            basicGlyphColor = ta.getColor(R.styleable.AsciiPanelView_defaultGlyphColor, DEFAULT_GLYPH_COLOR)
            if (ta.hasValue(R.styleable.AsciiPanelView_fontFamily))
                fontFamily = ta.getString(R.styleable.AsciiPanelView_fontFamily)
        } finally {
            ta.recycle()
        }
    }

    fun init() {
        chars = Array(panelWidth) { Array(panelHeight) { ColoredChar(' ', basicGlyphColor) } }

        val font = Typeface.create(Typeface.createFromAsset(context.assets, fontFamily), Typeface.BOLD)
        textPaint.typeface = font
    }

    override fun onSizeChanged(xNew: Int, yNew: Int, xOld: Int, yOld: Int) {
        super.onSizeChanged(xNew, yNew, xOld, yOld)
        tileWidth = xNew.toFloat() / panelWidth.toFloat()
        textPaint.textSize = yNew.toFloat() / panelHeight.toFloat()
        tileHeight = (yNew.toFloat() - textPaint.descent()) / panelHeight.toFloat()
        textPaint.textSize = tileHeight
    }

    override fun onDraw(canvas: Canvas) {
        for (w in 0..panelWidth - 1) {
            val posX = 0 + tileWidth * w
            for (h in 0..panelHeight - 1) {
                val posY = 0 + tileHeight * (h + 1)
                textPaint.color = chars[w][h].color
                canvas.drawText(chars[w][h].glyph.toString(), posX, posY, textPaint)
            }
        }
    }

    fun setCursorPosX(cursorX: Int) {
        if (cursorX < 0 || cursorX >= panelWidth) throw IllegalArgumentException("cursorX $cursorX must be in range [0,$panelWidth).")

        this.cursorX = cursorX
    }

    fun setCursorPosY(cursorY: Int) {
        if (cursorY < 0 || cursorY >= panelHeight) throw IllegalArgumentException("cursorY $cursorY must be in range [0,$panelHeight).")

        this.cursorY = cursorY
    }

    fun setCursorPosition(x: Int, y: Int) {
        setCursorPosX(x)
        setCursorPosY(y)
    }


    fun writeChar(character: Char) {
        writeChar(character, cursorX, cursorY, basicGlyphColor)
    }

    fun writeChar(character: Char, color: Int) {
        writeChar(character, cursorX, cursorY, color)
    }

    fun writeChar(character: Char, x: Int, y: Int) {
        if (x < 0 || x >= panelWidth) throw IllegalArgumentException("x $x must be in range [0,$panelWidth)")
        if (y < 0 || y >= panelHeight) throw IllegalArgumentException("y $y must be in range [0,$panelHeight)")

        writeChar(character, x, y, basicGlyphColor)
    }

    fun writeChar(character: Char, x: Int, y: Int, color: Int?) {
        if (x < 0 || x >= panelWidth) throw IllegalArgumentException("x $x must be in range [0,$panelWidth)")
        if (y < 0 || y >= panelHeight) throw IllegalArgumentException("y $y must be in range [0,$panelHeight)")

        var glyphColor = color

        if (glyphColor == null) glyphColor = basicGlyphColor

        chars[x][y] = ColoredChar(character, glyphColor)
        cursorX = x + 1
        cursorY = y
    }

    fun writeString(string: String) {
        if (cursorX + string.length > panelWidth) throw IllegalArgumentException("cursorX + string.length() " + (cursorX + string.length) + " must be less than " + panelWidth + ".")

        writeString(string, cursorX, cursorY, basicGlyphColor)
    }

    fun writeString(string: String, color: Int) {
        if (cursorX + string.length > panelWidth) throw IllegalArgumentException("cursorX + string.length() " + (cursorX + string.length) + " must be less than " + panelWidth + ".")

        writeString(string, cursorX, cursorY, color)
    }

    fun writeString(string: String, x: Int, y: Int) {
        if (x + string.length > panelWidth) throw IllegalArgumentException("x + string.length() " + (x + string.length) + " must be less than " + panelWidth + ".")
        if (x < 0 || x >= panelWidth) throw IllegalArgumentException("x $x must be in range [0,$panelWidth)")
        if (y < 0 || y >= panelHeight) throw IllegalArgumentException("y $y must be in range [0,$panelHeight)")

        writeString(string, x, y, basicGlyphColor)
    }

    fun writeString(string: String, x: Int, y: Int, color: Int?) {
        if (x + string.length > panelWidth) throw IllegalArgumentException("x + string.length() " + (x + string.length) + " must be less than " + panelWidth + ".")
        if (x < 0 || x >= panelWidth) throw IllegalArgumentException("x $x must be in range [0,$panelWidth).")
        if (y < 0 || y >= panelHeight) throw IllegalArgumentException("y $y must be in range [0,$panelHeight).")

        var glyphColor = color

        if (glyphColor == null)
            glyphColor = basicGlyphColor


        for (i in 0..string.length - 1) {
            writeChar(string[i], x + i, y, glyphColor)
        }
    }

    fun clear() {
        clearRect(' ', 0, 0, panelWidth, panelHeight, basicGlyphColor)
    }

    fun clear(character: Char) {
        clearRect(character, 0, 0, panelWidth, panelHeight, basicGlyphColor)
    }

    fun clear(character: Char, color: Int) {
        clearRect(character, 0, 0, panelWidth, panelHeight, color)
    }

    fun clearRect(character: Char, x: Int, y: Int, width: Int, height: Int) {
        if (x < 0 || x >= panelWidth) throw IllegalArgumentException("x $x must be within range [0,$panelWidth).")
        if (y < 0 || y >= panelHeight) throw IllegalArgumentException("y $y must be within range [0,$panelHeight).")
        if (width < 1) throw IllegalArgumentException("width $width must be greater than 0.")
        if (height < 1) throw IllegalArgumentException("height $height must be greater than 0.")
        if (x + width > panelWidth) throw IllegalArgumentException("x + width " + (x + width) + " must be less than " + (panelWidth + 1) + ".")
        if (y + height > panelHeight) throw IllegalArgumentException("y + height " + (y + height) + " must be less than " + (panelHeight + 1) + ".")

        clearRect(character, x, y, width, height, basicGlyphColor)
    }

    fun clearRect(character: Char, x: Int, y: Int, width: Int, height: Int, color: Int) {
        if (x < 0 || x >= panelWidth) throw IllegalArgumentException("x $x must be within range [0,$panelWidth)")
        if (y < 0 || y >= panelHeight) throw IllegalArgumentException("y $y must be within range [0,$panelHeight)")
        if (width < 1) throw IllegalArgumentException("width $width must be greater than 0.")
        if (height < 1) throw IllegalArgumentException("height $height must be greater than 0.")
        if (x + width > panelWidth) throw IllegalArgumentException("x + width " + (x + width) + " must be less than " + (panelWidth + 1) + ".")
        if (y + height > panelHeight) throw IllegalArgumentException("y + height " + (y + height) + " must be less than " + (panelHeight + 1) + ".")

        val originalCursorX = cursorX
        val originalCursorY = cursorY
        for (xo in x..x + width - 1) {
            for (yo in y..y + height - 1) {
                writeChar(character, xo, yo, color)
            }
        }
        cursorX = originalCursorX
        cursorY = originalCursorY
    }


    class ColoredChar(var glyph: Char, var color: Int)
}