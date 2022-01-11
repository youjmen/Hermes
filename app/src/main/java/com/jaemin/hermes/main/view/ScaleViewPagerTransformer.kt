package com.jaemin.hermes.main.view

import android.view.View
import androidx.viewpager2.widget.ViewPager2
import kotlin.math.abs
import kotlin.math.max

private const val MIN_SCALE = 0.8f
private const val MIN_ALPHA = 0.5f

class ScaleViewPagerTransformer(private val offsetPx : Int) : ViewPager2.PageTransformer {
    override fun transformPage(page: View, position: Float) {
        page.translationX = position * -offsetPx
        page.apply {
            alpha = 1 - abs(position) + MIN_ALPHA
            when {
                position <= 1 -> {
                    val scaleFactor = max(MIN_SCALE, 1 - abs(position))
                    scaleX = scaleFactor
                    scaleY = scaleFactor

                }
            }
        }

    }
}