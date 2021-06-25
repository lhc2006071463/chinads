package com.tiens.comonlibrary.util

import android.content.res.Resources
import android.util.TypedValue
import android.view.View
import com.tiens.comonlibrary.BuildConfig

val Float.dp
    get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this,
        Resources.getSystem().displayMetrics
    )

val Int.dp
    get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this.toFloat(),
        Resources.getSystem().displayMetrics
    )


fun View.isVisible(): Boolean {
    return visibility == View.VISIBLE
}

fun View.setVisible(visible: Boolean) {
    visibility = if (visible) View.VISIBLE else View.GONE
}

inline fun debug(code: () -> Unit){
    if (BuildConfig.DEBUG) {
        code()
    }
}
