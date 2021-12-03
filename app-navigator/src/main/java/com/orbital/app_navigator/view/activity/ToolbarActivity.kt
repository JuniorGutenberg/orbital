package com.orbital.app_navigator.view.activity

import androidx.appcompat.widget.Toolbar


class ToolbarActivity{

    fun hideToolbar(toolbar: Toolbar) {
        toolbar.clearAnimation()
        toolbar.animate().translationY(toolbar.height.toFloat()).duration = 200
    }
    fun showToolbar(toolbar: Toolbar) {
        toolbar.clearAnimation()
        toolbar.animate().translationY(0f).duration = 200
    }
}