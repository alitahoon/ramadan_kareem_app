package com.example.ramadan_kareem.util

import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.card.MaterialCardView

class ShrinkBehavior : CoordinatorLayout.Behavior<MaterialCardView>() {
    override fun layoutDependsOn(
        parent: CoordinatorLayout,
        child: MaterialCardView,
        dependency: View
    ): Boolean {
        return dependency is AppBarLayout
    }

    override fun onDependentViewChanged(
        parent: CoordinatorLayout,
        child: MaterialCardView,
        dependency: View
    ): Boolean {
        val appBarLayout = dependency as AppBarLayout
        val totalScrollRange = appBarLayout.totalScrollRange
        val percentage = -dependency.y / totalScrollRange
        child.layoutParams.height = (child.height * (1 - percentage)).toInt()
        child.requestLayout()
        return true
    }
}
