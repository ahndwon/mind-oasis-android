package xyz.thingapps.thingphrase.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.basic_info_navy.view.*
import kotlinx.android.synthetic.main.settings_fragment.view.*
import org.jetbrains.anko.support.v4.startActivity
import xyz.thingapps.thingphrase.R
import xyz.thingapps.thingphrase.activity.PolicyActivity
import xyz.thingapps.thingphrase.dialog.CreditDialogFragment

class SettingsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.settings_fragment, container, false)
        view.emailButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:$DEV_EMAIL"))
            startActivity(Intent.createChooser(intent, getString(R.string.send_mail)))
        }

        view.policyButton.setOnClickListener { startActivity<PolicyActivity>() }

        view.creditButton.setOnClickListener {
            CreditDialogFragment().show(fragmentManager, CreditDialogFragment::class.java.name)
        }

        val versionName =
            activity?.packageManager?.getPackageInfo(activity?.packageName, 0)?.versionName
        view.versionTextView.text = String.format(getString(R.string.version_name), versionName)

        return view
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            SettingsFragment().apply {

            }

        const val DEV_EMAIL = "thingpieceapps@gmail.com"
    }
}
