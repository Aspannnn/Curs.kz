package kz.aspan.curs.common

import android.content.Context
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver


fun View.afterLayoutConfiguration(func: () -> Unit) {
    viewTreeObserver?.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
        override fun onGlobalLayout() {
            viewTreeObserver?.removeOnGlobalLayoutListener(this)
            func()
        }
    })
}


fun View.setMargins(
    leftMarginDp: Int? = null,
    topMarginDp: Int? = null,
    rightMarginDp: Int? = null,
    bottomMarginDp: Int? = null
) {
    if (layoutParams is ViewGroup.MarginLayoutParams) {
        val params = layoutParams as ViewGroup.MarginLayoutParams
        leftMarginDp?.run { params.leftMargin = this.dpToPx(context) }
        topMarginDp?.run { params.topMargin = this.dpToPx(context) }
        rightMarginDp?.run { params.rightMargin = this.dpToPx(context) }
        bottomMarginDp?.run { params.bottomMargin = this.dpToPx(context) }
        requestLayout()
    }
}

fun Int.dpToPx(context: Context): Int {
    val metrics = context.resources.displayMetrics
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this.toFloat(), metrics).toInt()
}