package com.jaemin.hermes.util

import android.content.Context
import android.util.TypedValue


fun Float.toPx(context: Context) : Int{
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,this, context.resources.displayMetrics).toInt()
}