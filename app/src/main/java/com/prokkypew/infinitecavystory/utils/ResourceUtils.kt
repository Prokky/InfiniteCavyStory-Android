package com.prokkypew.infinitecavystory.utils

import android.util.TypedValue
import com.prokkypew.infinitecavystory.MainApplication


/**
 * Created by alexander.roman on 25.08.2017.
 */

fun getString(stringId: Int): String {
    return MainApplication.context.getString(stringId)
}

fun getIntResource(id: Int): Int {
    return MainApplication.context.resources.getInteger(id)
}

fun getFloatResource(id: Int): Float {
    val outValue = TypedValue()
    MainApplication.context.resources.getValue(id, outValue, true)
    return outValue.float
}