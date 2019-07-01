package xyz.thingapps.thingphrase.dialog

import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import xyz.thingapps.thingphrase.util.convertDpToPixel

open class FullWidthDialogFragment : DialogFragment() {
    override fun onResume() {
        super.onResume()
        if (showsDialog) {
            setDialogSize()
        }
    }

    private fun setDialogSize() {
        activity?.let { activity ->
            activity.resources?.displayMetrics?.let { metrics ->
                val dialogWidth =
                    Math.min(
                        metrics.widthPixels - activity.convertDpToPixel(16f).toInt(),
                        metrics.heightPixels
                    )
                dialog?.window?.setLayout(dialogWidth, ViewGroup.LayoutParams.WRAP_CONTENT)
            }
        }
    }
}