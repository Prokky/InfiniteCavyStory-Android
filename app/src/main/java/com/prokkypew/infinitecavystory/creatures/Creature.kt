package com.prokkypew.infinitecavystory.creatures

import com.prokkypew.infinitecavystory.R
import com.prokkypew.infinitecavystory.creatures.ai.CreatureAi
import com.prokkypew.infinitecavystory.items.Inventory
import com.prokkypew.infinitecavystory.utils.getString
import com.prokkypew.infinitecavystory.world.Tile
import com.prokkypew.infinitecavystory.world.World


/**
 * Created by alexander.roman on 17.08.2017.
 */
class Creature(private val world: World, val glyph: Char, val color: Int, val name: String, var maxHp: Int, var maxMana: Int, var defense: Int) {

    var inventory = Inventory(20)
    var x: Int = 0
    var y: Int = 0
    var z: Int = 0
    var ai: CreatureAi? = null
    val visionRadius: Int = 9
    var mana = maxMana
    var hp = maxHp
    var maxFood: Int = 1000
    var food: Int = maxFood / 3 * 2
    var xp: Int = 0
    var level: Int = 1
    var causeOfDeath: String = ""

    fun moveBy(mx: Int, my: Int, mz: Int) {
        if (mx == 0 && my == 0 && mz == 0)
            return

        val tile = world.tile(x + mx, y + my, z + mz)

        if (mz == -1) {
            if (tile === Tile.STAIRS_DOWN) {
                doAction(getString(R.string.message_walk_up_stairs), z + mz + 1)
            } else {
                doAction(getString(R.string.message_walk_up_stairs_error))
                return
            }
        } else if (mz == 1) {
            if (tile === Tile.STAIRS_UP) {
                doAction(getString(R.string.message_walk_down_stairs), z + mz + 1)
            } else {
                doAction(getString(R.string.message_walk_down_stairs_error))
                return
            }
        }

        val other = world.creature(x + mx, y + my, z + mz)

        if (other == null)
            ai?.onEnter(x + mx, y + my, z + mz, tile)
        else
            meleeAttack(other)
    }

    private fun meleeAttack(other: Creature) {
        commonAttack(other, attackValue(), getString(R.string.message_attack), other.name)
    }

    private fun commonAttack(other: Creature, attack: Int, action: String, vararg params: Any) {
        var amount = Math.max(0, attack - other.defenseValue())

        amount = (Math.random() * amount).toInt() + 1

        doAction(action, *params, amount)

        other.modifyHp(-amount, String.format(getString(R.string.message_killed_by), name))

        if (other.hp < 1)
            gainXp(other)
    }

    private fun modifyXp(amount: Int) {
        xp += amount

        notify(getString(R.string.message_modify_xp), if (amount < 0) getString(R.string.message_lose) else getString(R.string.message_gain), amount)

        while (xp > (Math.pow(level.toDouble(), 1.75) * 25).toInt()) {
            level++
            doAction(getString(R.string.message_levelup), level)
            ai?.onGainLevel()
            modifyHp(level * 2, getString(R.string.message_levelup_error))
        }
    }

    private fun modifyHp(amount: Int, causeOfDeath: String) {
        hp += amount
        this.causeOfDeath = causeOfDeath

        if (hp > maxHp) {
            hp = maxHp
        } else if (hp < 1) {
            doAction(getString(R.string.message_die))
            world.remove(this)
        }
    }

    private fun gainXp(other: Creature) {
        val amount = other.maxHp + other.attackValue() + other.defenseValue() - level

        if (amount > 0)
            modifyXp(amount)
    }

    private var attackValue: Int = 0

    private fun attackValue(): Int {
        return attackValue
    }

    private var defenseValue: Int = 0

    private fun defenseValue(): Int {
        return defenseValue
    }


    fun canSee(wx: Int, wy: Int, wz: Int): Boolean {
        if (ai == null)
            return false
        return ai!!.canSee(wx, wy, wz)
    }


    fun realTile(wx: Int, wy: Int, wz: Int): Tile {
        return world.tile(wx, wy, wz)
    }

    private fun notify(message: String, vararg params: Any) {
        ai?.onNotify(String.format(message, *params))
    }

    fun hunger(): String {
        return when {
            food < maxFood * 0.10 -> getString(R.string.food_starving)
            food < maxFood * 0.25 -> getString(R.string.food_hungry)
            food > maxFood * 0.90 -> getString(R.string.food_stuffed)
            food > maxFood * 0.75 -> getString(R.string.food_full)
            else -> getString(R.string.food_fine)
        }
    }

    fun canEnter(wx: Int, wy: Int, wz: Int): Boolean {
        return world.tile(wx, wy, wz).isGround && world.creature(wx, wy, wz) == null
    }

    fun doAction(message: String, vararg params: Any) {
        for (other in getCreaturesWhoSeeMe()) {
            if (other === this) {
                other.notify("You $message.", *params)
            } else {
                other.notify(String.format(getString(R.string.message_generic), name, makeSecondPerson(message)), *params)
            }
        }
    }

    fun update() {
        ai?.onUpdate()
    }

    fun tile(wx: Int, wy: Int, wz: Int): Tile {
        return if (canSee(wx, wy, wz))
            world.tile(wx, wy, wz)
        else
            ai!!.rememberedTile(wx, wy, wz)
    }

    fun creature(wx: Int, wy: Int, wz: Int): Creature? {
        return if (canSee(wx, wy, wz))
            world.creature(wx, wy, wz)
        else
            null
    }

    private fun getCreaturesWhoSeeMe(): List<Creature> {
        val others = ArrayList<Creature>()
        val r = 9
        for (ox in -r until r + 1) {
            (-r until r + 1)
                    .filter { ox * ox + it * it <= r * r }
                    .mapNotNullTo(others) { world.creature(x + ox, y + it, z) }
        }
        return others
    }

    private fun makeSecondPerson(text: String): String {
        val words = text.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        words[0] = words[0] + "s"

        val builder = StringBuilder()
        for (word in words) {
            builder.append(" ")
            builder.append(word)
        }

        return builder.toString().trim { it <= ' ' }
    }

    fun pickup() {
        val item = world.item(x, y, z)

        if (inventory.isFull || item == null) {
            doAction(getString(R.string.grab_at_ground))
        } else {
            doAction(getString(R.string.pickup), item.appearance())
            world.removeItem(x, y, z)
            inventory.add(item)
        }
    }
}