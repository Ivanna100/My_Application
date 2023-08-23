package com.ispolska.myApplication.task_2.utils.ext

import android.view.View
import android.view.ViewPropertyAnimator

fun View.animateVisibility(visibility: Int) {
    val animator: ViewPropertyAnimator = when (visibility) {
        View.VISIBLE -> animate().alpha(1f).setDuration(300) // TODO: maybe to constants
        View.GONE -> animate().alpha(0f).setDuration(300)
        else -> return
    }
    animator.withStartAction {
        this.visibility = View.VISIBLE
    }.withEndAction {
        this.visibility = visibility
    }.start()
}