package com.tiens.comonlibrary.util

import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable

fun generateDrawable(color: Int, radius: Int): Drawable {
    val drawable = GradientDrawable()
    drawable.color = ColorStateList.valueOf(color)
    drawable.cornerRadius = radius.toFloat()
    return drawable
}

fun generateStrokeDrawable(strokeWidth: Int, color: Int, radius: Int): Drawable {
    val drawable = GradientDrawable()
    drawable.setStroke(strokeWidth,color)
    drawable.cornerRadius = radius.toFloat()
    return drawable
}

fun generateTopCornerDrawable(color: Int, radius: Int): Drawable {
    val drawable = GradientDrawable()
    drawable.color = ColorStateList.valueOf(color)
    drawable.cornerRadii = floatArrayOf(radius.toFloat(),radius.toFloat(),radius.toFloat(),radius.toFloat(),0f,0f,0f,0f)
    return drawable
}

fun generateBottomCornerDrawable(color: Int, radius: Int): Drawable {
    val drawable = GradientDrawable()
    drawable.color = ColorStateList.valueOf(color)
    drawable.cornerRadii = floatArrayOf(0f,0f,0f,0f,radius.toFloat(),radius.toFloat(),radius.toFloat(),radius.toFloat())
    return drawable
}