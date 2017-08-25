package com.prokkypew.infinitecavystory.screens

import android.graphics.Color
import com.prokkypew.asciipanelview.AsciiPanelView
import com.prokkypew.infinitecavystory.Gui
import com.prokkypew.infinitecavystory.R
import com.prokkypew.infinitecavystory.creatures.Creature
import com.prokkypew.infinitecavystory.utils.getIntResource
import com.prokkypew.infinitecavystory.utils.getString
import com.prokkypew.infinitecavystory.world.FieldOfView
import com.prokkypew.infinitecavystory.world.Tile
import com.prokkypew.infinitecavystory.world.World
import com.prokkypew.infinitecavystory.world.WorldBuilder
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async


/**
 * Created by prokk on 16.08.2017.
 */
class PlayScreen(panelView: AsciiPanelView) : Screen(panelView) {
    companion object {
        val PLAY_WIDTH = 62
        val PLAY_HEIGHT = 41
    }

    private lateinit var world: World
    private lateinit var gui: Gui
    private lateinit var player: Creature
    private lateinit var fov: FieldOfView
    private var worldGenerated = false
    private val messages = arrayListOf<String>()

    init {
        displayOutput()
        async(UI) {
            world = WorldBuilder(
                    getIntResource(R.integer.world_width),
                    getIntResource(R.integer.world_height),
                    getIntResource(R.integer.world_depth))
                    .makeCaves()
            initWithWorld()
        }
    }

    private fun initWithWorld() {
        worldGenerated = true
        fov = FieldOfView(world)

        world.createCreatures()
        player = world.createPlayer(messages, fov)

        gui = Gui(panel, player)
        displayOutput()
    }

    override fun displayOutput() {
        panel.clear()
        if (worldGenerated) {
            displayTiles()
            gui.displayStats()
            gui.displayMessages(messages)
            gui.displayControls(panel, PLAY_WIDTH, PLAY_HEIGHT)
            // gui.displayPrompt()
        } else {
            panel.writeCenter(getString(R.string.generating_world), panel.panelHeight / 2)
        }
    }

    private fun displayTiles() {
        fov.update(player.x, player.y, player.z, player.visionRadius)
        world.update()
        for (x in 0 until PLAY_WIDTH) {
            for (y in 0 until PLAY_HEIGHT) {
                val wx = x + getScrollX()
                val wy = y + getScrollY()

                if (player.canSee(wx, wy, player.z))
                    panel.writeChar(world.glyph(wx, wy, player.z), x, y, world.color(wx, wy, player.z))
                else
                    panel.writeChar(fov.tile(wx, wy, player.z).glyph(), x, y, Color.DKGRAY)
            }
        }
    }

    private fun getScrollX(): Int {
        return Math.max(0, Math.min(player.x - PLAY_WIDTH / 2, world.width - PLAY_WIDTH))
    }

    private fun getScrollY(): Int {
        return Math.max(0, Math.min(player.y - PLAY_HEIGHT / 2, world.height - PLAY_HEIGHT))
    }

    override fun respondToUserInput(x: Int?, y: Int?, char: AsciiPanelView.ColoredChar): Screen {
        if (!worldGenerated)
            return this

        gui.handleControl(x!!, y!!, char.char, player)

        displayOutput()
        checkPlayerPosition()
        return this
    }

    private fun checkPlayerPosition() {
        if (world.tile(player.x, player.y, player.z) == Tile.STAIRS_UP) {
            gui.showPrompt(getString(R.string.prompt_tap_to_go_up), object : Gui.OnPromptClick {
                override fun onPromptClick() {
                    player.moveBy(0, 0, -1)
                }
            })
        } else if (world.tile(player.x, player.y, player.z) == Tile.STAIRS_DOWN) {
            gui.showPrompt(getString(R.string.prompt_tap_to_go_down), object : Gui.OnPromptClick {
                override fun onPromptClick() {
                    player.moveBy(0, 0, 1)
                }
            })
        }
    }
}