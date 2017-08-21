package com.prokkypew.infinitecavystory.world

import io.reactivex.Flowable
import java.util.*


/**
 * Created by alexander.roman on 17.08.2017.
 */
class WorldBuilder(private val width: Int, private val height: Int, private val depth: Int) {
    private var tiles = Array(width) { Array(height) { Array(depth) { Tile.UNKNOWN } } }
    private var regions = Array(width) { Array(height) { IntArray(depth) } }
    private var nextRegion = 1


    private fun build(): World {
        return World(tiles)
    }

    private fun randomizeTiles(): WorldBuilder {
        for (x in 0 until width) {
            for (y in 0 until height) {
                for (z in 0 until depth) {
                    tiles[x][y][z] = if (Math.random() < 0.5)
                        Tile.FLOOR
                    else
                        Tile.WALL
                }
            }
        }
        return this
    }

    private fun smooth(times: Int): WorldBuilder {
        val tiles2 = Array(width) { Array(height) { Array(depth) { Tile.UNKNOWN } } }
        for (time in 0 until times) {

            for (x in 0 until width) {
                for (y in 0 until height) {
                    for (z in 0 until depth) {
                        var floors = 0
                        var rocks = 0

                        for (ox in -1..1) {
                            (-1..1).filter { x + ox in 0..(width - 1) && y + it >= 0 && y + it < height }
                                    .forEach {
                                        if (tiles[x + ox][y + it][z] === Tile.FLOOR)
                                            floors++
                                        else
                                            rocks++
                                    }
                        }
                        tiles2[x][y][z] = if (floors >= rocks)
                            Tile.FLOOR
                        else
                            Tile.WALL
                    }
                }
            }
            tiles = tiles2
        }
        return this
    }

    private fun createRegions(): WorldBuilder {
        regions = Array(width) { Array(height) { IntArray(depth) } }

        for (z in 0 until depth) {
            for (x in 0 until width) {
                (0 until height)
                        .filter { tiles[x][it][z] !== Tile.WALL && regions[x][it][z] == 0 }
                        .map { fillRegion(nextRegion++, x, it, z) }
                        .filter { it < 25 }
                        .forEach { removeRegion(nextRegion - 1, z) }
            }
        }
        return this
    }

    private fun removeRegion(region: Int, z: Int) {
        for (x in 0 until width) {
            for (y in 0 until height) {
                if (regions[x][y][z] == region) {
                    regions[x][y][z] = 0
                    tiles[x][y][z] = Tile.WALL
                }
            }
        }
    }

    private fun fillRegion(region: Int, x: Int, y: Int, z: Int): Int {
        var size = 1
        val open = ArrayList<Point>()
        open.add(Point(x, y, z))
        regions[x][y][z] = region

        while (!open.isEmpty()) {
            val p = open.removeAt(0)

            for (neighbor in p.neighbors8()) {
                if (neighbor.x < 0 || neighbor.y < 0 || neighbor.x >= width
                        || neighbor.y >= height)
                    continue

                if (regions[neighbor.x][neighbor.y][neighbor.z] > 0 || tiles[neighbor.x][neighbor.y][neighbor.z] === Tile.WALL)
                    continue

                size++
                regions[neighbor.x][neighbor.y][neighbor.z] = region
                open.add(neighbor)
            }
        }
        return size
    }

    private fun connectRegions(): WorldBuilder {
        for (z in 0 until depth - 1) {
            connectRegionsDown(z)
        }
        return this
    }

    private fun connectRegionsDown(z: Int) {
        val connected = ArrayList<Int>()

        for (x in 0 until width) {
            for (y in 0 until height) {
                val r = regions[x][y][z] * 1000 + regions[x][y][z + 1]
                if (tiles[x][y][z] === Tile.FLOOR
                        && tiles[x][y][z + 1] === Tile.FLOOR
                        && !connected.contains(r)) {
                    connected.add(r)
                    connectRegionsDown(z, regions[x][y][z],
                            regions[x][y][z + 1])
                }
            }
        }
    }

    private fun connectRegionsDown(z: Int, r1: Int, r2: Int) {
        val candidates = findRegionOverlaps(z, r1, r2)

        var stairs = 0
        do {
            val p = candidates.removeAt(0)
            tiles[p.x][p.y][z] = Tile.STAIRS_DOWN
            tiles[p.x][p.y][z + 1] = Tile.STAIRS_UP
            stairs++
        } while (candidates.size / stairs > 250)
    }

    private fun findRegionOverlaps(z: Int, r1: Int, r2: Int): MutableList<Point> {
        val candidates = ArrayList<Point>()

        for (x in 0 until width) {
            (0 until height)
                    .filter { tiles[x][it][z] === Tile.FLOOR && tiles[x][it][z + 1] === Tile.FLOOR && regions[x][it][z] == r1 && regions[x][it][z + 1] == r2 }
                    .mapTo(candidates) { Point(x, it, z) }
        }

        Collections.shuffle(candidates)
        return candidates
    }

    private fun addExitStairs(): WorldBuilder {
        var x: Int
        var y: Int

        do {
            x = (Math.random() * width).toInt()
            y = (Math.random() * height).toInt()
        } while (tiles[x][y][0] !== Tile.FLOOR)

        tiles[x][y][0] = Tile.STAIRS_UP
        return this
    }

    fun makeCaves(): Flowable<World> {
        return Flowable.fromCallable<World> {
            randomizeTiles().smooth(8).createRegions().connectRegions()
                    .addExitStairs().build()
        }
    }
}