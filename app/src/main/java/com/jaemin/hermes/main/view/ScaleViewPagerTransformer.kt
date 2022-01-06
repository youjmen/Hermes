package com.jaemin.hermes.main.view

import android.util.Log
import android.view.View
import androidx.viewpager2.widget.ViewPager2

private const val MIN_SCALE = 0.85f
private const val MIN_ALPHA = 0.5f

class ScaleViewPagerTransformer(private val offsetPx : Int) : ViewPager2.PageTransformer {
    override fun transformPage(page: View, position: Float) {
        page.translationX = position * -offsetPx
        page.apply {
            val pageWidth = width
            val pageHeight = height
            Log.d("dddddddpageWidth", width.toString())
            Log.d("dddddddpageHeight", height.toString())
            Log.d("dddddddpagepos", position.toString())
            Log.d("dddddddpageview", page.toString())

            when {
                position < -1 -> { // [-Infinity,-1)
                    // This page is way off-screen to the left.
                    alpha = 0f
                }
                position <= 1 -> { // [-1,1]
                    // Modify the default slide transition to shrink the page as well
                    val scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position))
//                    val vertMargin = pageHeight * (1 - scaleFactor) / 2
//                    val horzMargin = pageWidth * (1 - scaleFactor) / 2
//                    translationX = if (position < 0) {
//                        horzMargin - vertMargin / 2
//                    } else {
//                        horzMargin + vertMargin / 2
//                    }
                    // Scale the page down (between MIN_SCALE and 1)
                    scaleX = scaleFactor
                    scaleY = scaleFactor

                    // Fade the page relative to its size.
                    alpha = (MIN_ALPHA +
                            (((scaleFactor - MIN_SCALE) / (1 - MIN_SCALE)) * (1 - MIN_ALPHA)))
                }
                else -> { // (1,+Infinity]
                    // This page is way off-screen to the right.
                    alpha = 0f
                }
            }
        }

    }
}