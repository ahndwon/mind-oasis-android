package xyz.thingapps.mind_oasis.utils

import android.app.Activity
import android.os.Build
import android.view.View
import androidx.core.content.ContextCompat

fun Activity.setStatusBarIconDark(isDark: Boolean) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        val decor = this.window.decorView
        if (isDark) {
            decor.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        } else {
            // We want to change tint color to white again.
            // You can also record the flags in advance so that you can turn UI back completely if
            // you have set other flags before, such as translucent or full screen.
            decor.systemUiVisibility = 0
        }
    }
}


fun Activity.setStatusBarColor(colorId: Int) {
    this.window.statusBarColor = ContextCompat.getColor(this, colorId)
}