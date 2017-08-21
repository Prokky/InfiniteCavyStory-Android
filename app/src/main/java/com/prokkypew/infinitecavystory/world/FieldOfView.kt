package com.prokkypew.infinitecavystory.world


/**
 * Created by alexander.roman on 18.08.2017.
 */
class FieldOfView(private val world: World) {
    private var depth: Int = 0

    private var visible: Array<BooleanArray>? = null

    fun isVisible(x: Int, y: Int, z: Int): Boolean {
        return z == depth && x >= 0 && y >= 0 && x < visible!!.size
                && y < visible!![0].size && visible!![x][y]
    }

    private val tiles: Array<Array<Array<Tile>>>

    fun tile(x: Int, y: Int, z: Int): Tile {
        return tiles[x][y][z]
    }

    init {
        this.visible = Array(world.width) { BooleanArray(world.height) }
        this.tiles = Array(world.width) { Array(world.height) { Array(world.depth) { Tile.UNKNOWN } } }

        for (x in 0 until world.width) {
            for (y in 0 until world.height) {
                for (z in 0 until world.depth) {
                    tiles[x][y][z] = Tile.UNKNOWN
                }
            }
        }
    }

    fun update(wx: Int, wy: Int, wz: Int, r: Int) {
        depth = wz
        visible = Array(world.width) { BooleanArray(world.height) }

        for (x in -r until r) {
            for (y in -r until r) {
                if (x * x + y * y > r * r)
                    continue

                if (wx + x < 0 || wx + x >= world.width || wy + y < 0
                        || wy + y >= world.height)
                    continue

                for (p in Line(wx, wy, wx + x, wy + y)) {
                    val tile = world.tile(p.x, p.y, wz)
                    visible!![p.x][p.y] = true
                    tiles[p.x][p.y][wz] = tile

                    if (!tile.isGround)
                        break
                }
            }
        }
    }
}