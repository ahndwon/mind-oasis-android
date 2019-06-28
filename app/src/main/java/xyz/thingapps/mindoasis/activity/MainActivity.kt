package xyz.thingapps.mindoasis.activity

import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.debug
import org.jetbrains.anko.toast
import xyz.thingapps.mindoasis.R
import xyz.thingapps.mindoasis.fragment.BookmarkFragment
import xyz.thingapps.mindoasis.fragment.HomeFragment
import xyz.thingapps.mindoasis.fragment.SettingsFragment
import xyz.thingapps.mindoasis.util.setStatusBarColor
import xyz.thingapps.mindoasis.util.setStatusBarIconDark

class MainActivity : AppCompatActivity(), SettingsFragment.OnFragmentInteractionListener, AnkoLogger {
    private var backPressedTime = 0L

    companion object {
        const val FINISH_INTERVAL_TIME = 2000L
    }

    override fun onFragmentInteraction(uri: Uri) {
        debug("onFragmentInteraction")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction()
                .replace(R.id.mainContainer,
                        HomeFragment(), HomeFragment::class.java.name)
                .commit()

        val list = listOf(
                R.id.navigation_home,
                R.id.navigation_bookmark, R.id.navigation_share,
                R.id.navigation_settings
        )

        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            when (list.indexOf(menuItem.itemId)) {
                0 -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.mainContainer, HomeFragment(), HomeFragment::class.java.name)
                            .commit()
                    true
                }

                1 -> {
                    supportFragmentManager.beginTransaction()
                            .replace(R.id.mainContainer, BookmarkFragment())
//                            .addToBackStack(BookmarkFragment::class.java.name)
                            .commit()
                    true
                }

                2 -> {
                    toast("Share")
                    true
                }

                3 -> {
                    supportFragmentManager.beginTransaction()
                            .replace(R.id.mainContainer, SettingsFragment())
//                            .addToBackStack(SettingsFragment::class.java.name)
                            .commit()
                    true
                }

                else -> {
                    true
                }
            }
        }

        setStatusBarColor(R.color.veryDarkNavy)
        setStatusBarIconDark(false)
    }

    override fun onAttachFragment(fragment: Fragment) {
        if (fragment is SettingsFragment) {
//            fragment.listener = object : SettingsFragment.OnFragmentInteractionListener {
//                override fun onFragmentInteraction(uri: Uri) {
//                    toast("Settings FragmentInteraction")
//                }
//            }
            fragment.listener = this
        }
    }

    override fun onBackPressed() {
        val tempTime = System.currentTimeMillis()
        val intervalTime = tempTime - backPressedTime

        if (intervalTime in 0..FINISH_INTERVAL_TIME) {
            super.onBackPressed()
        } else {
            backPressedTime = tempTime
            toast(getString(R.string.backButtonMessage))
        }
    }
}
