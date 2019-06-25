package xyz.thingapps.mind_oasis.controllers

import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.toast
import org.jetbrains.anko.debug
import xyz.thingapps.mind_oasis.R
import xyz.thingapps.mind_oasis.utils.setStatusBarColor
import xyz.thingapps.mind_oasis.utils.setStatusBarIconDark

class MainActivity : AppCompatActivity(), SettingsFragment.OnFragmentInteractionListener, AnkoLogger {
    override fun onFragmentInteraction(uri: Uri) {
        debug("onFragmentInteraction")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction()
                .replace(R.id.mainContainer, HomeFragment(), HomeFragment::class.java.name)
                .commit()

        val list = listOf(
                R.id.navigation_bookmark, R.id.navigation_share,
                R.id.navigation_settings
        )

        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            when (list.indexOf(menuItem.itemId)) {
                0 -> {
                    supportFragmentManager.beginTransaction()
                            .replace(R.id.mainContainer, BookmarkFragment())
                            .addToBackStack(BookmarkFragment::class.java.name)
                            .commit()
                    true
                }

                1 -> {
                    toast("Share")
                    true
                }

                2 -> {
                    supportFragmentManager.beginTransaction()
                            .replace(R.id.mainContainer, SettingsFragment())
                            .addToBackStack(SettingsFragment::class.java.name)
                            .commit()
                    true
                }

                else -> {
                    true
                }
            }
        }

        setStatusBarColor(android.R.color.white)
        setStatusBarIconDark(true)
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
}
