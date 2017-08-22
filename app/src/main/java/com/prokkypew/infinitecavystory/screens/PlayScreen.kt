package com.prokkypew.infinitecavystory.screens

import android.graphics.Color
import com.prokkypew.asciipanelview.AsciiPanelView
import com.prokkypew.infinitecavystory.StuffFactory
import com.prokkypew.infinitecavystory.creatures.Creature
import com.prokkypew.infinitecavystory.world.FieldOfView
import com.prokkypew.infinitecavystory.world.World
import com.prokkypew.infinitecavystory.world.WorldBuilder
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


/**
 * Created by prokk on 16.08.2017.
 */
class PlayScreen(panelView: AsciiPanelView) : Screen(panelView) {
    private lateinit var world: World
    private lateinit var player: Creature
    private lateinit var fov: FieldOfView
    private var worldGenerated = false

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
        val factory = StuffFactory(world)
        createCreatures(factory)
        displayOutput()
    }

    private fun createCreatures(factory: StuffFactory) {
        player = factory.newPlayer(fov)
    }

    override fun displayOutput() {
        panel.clear()
        if (worldGenerated) {
            displayTiles()
        } else {
            panel.writeCenter("Generating world", 10)
        }
    }

    private fun displayTiles() {
        fov.update(player.x, player.y, player.z, player.visionRadius)

        for (x in 0 until panel.panelWidth) {
            for (y in 0 until panel.panelHeight) {
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
        return Math.max(0, Math.min(player.x - panel.panelWidth / 2, world.width - panel.panelWidth))
    }

    private fun getScrollY(): Int {
        return Math.max(0, Math.min(player.y - panel.panelHeight / 2, world.height - panel.panelHeight))
    }

    override fun respondToUserInput(x: Int?, y: Int?, char: AsciiPanelView.ColoredChar): Screen {
        if (!worldGenerated)
            return this

        var xMove = 0
        var yMove = 0

        if (x?.coerceIn(0, 4) == x)
            xMove = -1
        else if (x?.coerceIn(panel.panelWidth - 3, panel.panelWidth) == x)
            xMove = 1

        if (y?.coerceIn(0, 4) == y)
            yMove = -1
        else if (y?.coerceIn(panel.panelHeight - 3, panel.panelHeight) == y)
            yMove = 1

        player.moveBy(xMove, yMove, 0)

        displayOutput()

        return this
    }
}