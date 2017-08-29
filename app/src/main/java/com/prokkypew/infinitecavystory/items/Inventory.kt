package com.prokkypew.infinitecavystory.items

/**
 * Created by alexander.roman on 29.08.2017.
 */
class Inventory(max: Int) {

    val items: Array<Item?> = arrayOfNulls(max)

    operator fun get(i: Int): Item? {
        return items[i]
    }

    fun add(item: Item) {
        for (i in items.indices) {
            if (items[i] == null) {
                items[i] = item
                break
            }
        }
    }

    fun remove(item: Item) {
        for (i in items.indices) {
            if (items[i] == item) {
                items[i] = null
                return
            }
        }
    }

    val isFull: Boolean
        get() {
            val size = items.indices.count { items[it] != null }
            return size == items.size
        }

    operator fun contains(item: Item): Boolean {
        return items.contains(item)
    }
}