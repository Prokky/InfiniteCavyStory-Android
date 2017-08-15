package com.prokkypew.infinitecavystory

import android.graphics.Color
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(RobolectricTestRunner::class)
@Config(manifest = "app/src/main/AndroidManifest.xml",
        sdk = intArrayOf(26))
class AsciiPanelTest {
    lateinit var panel: AsciiPanelView

    @Before
    @Throws(Exception::class)
    fun setUp() {
        panel = AsciiPanelView(RuntimeEnvironment.application)
    }

    @Test
    fun checkPanelCreated() {
        assertNotNull(panel)
    }

    @Test
    fun checkCursor() {
        panel.setCursorPosition(15, 20)
        assertEquals(panel.cursorX, 15)
        assertEquals(panel.cursorY, 20)
        checkInvalidCursorPos(-1, 5)
        checkInvalidCursorPos(5, -1)
        checkInvalidCursorPos(Integer.MAX_VALUE, 5)
        checkInvalidCursorPos(5, Integer.MAX_VALUE)
    }

    fun checkInvalidCursorPos(x: Int, y: Int) {
        try {
            panel.setCursorPosition(x, y)
            Assert.fail()
        } catch (e: IllegalArgumentException) {
            assertNotNull(e)
        }
    }

    @Test
    fun checkWriteChar() {
        panel.setCursorPosition(5, 14)
        panel.writeChar('c')
        assertEquals(panel.chars[5][14].glyph, 'c')

        panel.setCursorPosition(1, 1)
        panel.writeChar('p', Color.RED)
        assertEquals(panel.chars[1][1].glyph, 'p')
        assertEquals(panel.chars[1][1].color, Color.RED)

        panel.writeChar('a', 3, 3)
        assertEquals(panel.chars[3][3].glyph, 'a')

        panel.writeChar('#', 4, 4, Color.BLUE)
        assertEquals(panel.chars[4][4].glyph, '#')
        assertEquals(panel.chars[4][4].color, Color.BLUE)

        checkInvalidCharPos('c', 5, Integer.MAX_VALUE, null)
        checkInvalidCharPos('c', Integer.MAX_VALUE, 5, null)
        checkInvalidCharPos('c', 5, Integer.MAX_VALUE, Color.RED)
        checkInvalidCharPos('c', Integer.MAX_VALUE, 5, Color.RED)
    }

    fun checkInvalidCharPos(glyph: Char, x: Int, y: Int, color: Int?) {
        try {
            if (color == null) {
                panel.writeChar(glyph, x, y)
            } else {
                panel.writeChar(glyph, x, y, color)
            }
            Assert.fail()
        } catch (e: IllegalArgumentException) {
            assertNotNull(e)
        }
    }

    @Test
    fun checkWriteString() {
        var string = "pew"
        panel.writeString(string)
        checkCorrectString(string, 0, 0, null)

        panel.setCursorPosX(0)
        panel.writeString(string, Color.BLUE)
        checkCorrectString(string, 0, 0, Color.BLUE)

        panel.writeString(string, 10, 1)
        checkCorrectString(string, 10, 1, null)

        panel.writeString(string, 0, 1, Color.RED)
        checkCorrectString(string, 0, 1, Color.RED)

        panel.setCursorPosX(63)
        try {
            panel.writeString("pewly test")
            Assert.fail()
        } catch (e: IllegalArgumentException) {
            assertNotNull(e)
        }
    }

    fun checkCorrectString(string: String, posX: Int, posY: Int, color: Int?) {
        for (i in posX..posX + string.length - 1) {
            assertEquals(panel.chars[i][posY].glyph, string[i - posX])
            if (color != null)
                assertEquals(panel.chars[i][posY].color, color)
        }
    }
}
