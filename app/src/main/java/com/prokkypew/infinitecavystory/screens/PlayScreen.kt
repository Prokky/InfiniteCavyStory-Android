package com.prokkypew.infinitecavystory.screens

import android.graphics.Color
import com.prokkypew.asciipanelview.AsciiPanelView
import com.prokkypew.infinitecavystory.Gui
import com.prokkypew.infinitecavystory.CreatureFactory
import com.prokkypew.infinitecavystory.creatures.Creature
import com.prokkypew.infinitecavystory.drawControls
import com.prokkypew.infinitecavystory.handleControl
import com.prokkypew.infinitecavystory.world.FieldOfView
import com.prokkypew.infinitecavystory.world.World
import com.prokkypew.infinitecavystory.world.WorldBuilder
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


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
    private lateinit var creatureFactory: CreatureFactory
    private var worldGenerated = false
    private val messages = arrayListOf<String>()

    init {
        WorldBuilder(150, 100, 10).makeCaves()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::initWithWorld, Throwable::printStackTrace)
    }

    private fun initWithWorld(w: World) {
        world = w
        worldGenerated = true
        fov = FieldOfView(world)
        creatureFactory = CreatureFactory(world)
        player = creatureFactory.newPlayer(messages, fov)
        gui = Gui(panel, player)
        createCreatures()
        displayOutput()
    }

    private fun createCreatures() {

    }

    override fun displayOutput() {
        panel.clear()
        if (worldGenerated) {
            displayTiles()
            gui.displayStats()
            gui.displayMessages(messages)
            drawControls(panel, PLAY_WIDTH, PLAY_HEIGHT)
        } else {
            panel.writeCenter("Generating world", 10)
        }
    }

    private fun displayTiles() {
        fov.update(player.x, player.y, player.z, player.visionRadius)

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

        handleControl(char.char, player)

        displayOutput()
        return this
    }
}