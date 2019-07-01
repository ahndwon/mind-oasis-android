package xyz.thingapps.thingphrase.util

import android.app.Activity
import android.content.Context
import android.os.Build
import android.util.DisplayMetrics
import android.view.Gravity
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import org.threeten.bp.ZonedDateTime

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

fun Toast.showCenter() {
    this.setGravity(Gravity.CENTER, 0, 0)
    this.show()
}

fun Context.convertDpToPixel(dp: Float): Float {
    val resources = this.resources
    val metrics = resources.displayMetrics
    return dp * (metrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
}

fun ZonedDateTime.isSameDay(dateTime: ZonedDateTime): Boolean {
    return (this.dayOfYear == dateTime.dayOfYear && this.year == dateTime.year)
}