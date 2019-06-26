package xyz.thingapps.mind_oasis.activity

import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.debug
import org.jetbrains.anko.toast
import xyz.thingapps.mind_oasis.R
import xyz.thingapps.mind_oasis.fragment.BookmarkFragment
import xyz.thingapps.mind_oasis.fragment.HomeFragment
import xyz.thingapps.mind_oasis.fragment.SettingsFragment
import xyz.thingapps.mind_oasis.util.setStatusBarColor
import xyz.thingapps.mind_oasis.util.setStatusBarIconDark

class MainActivity : AppCompatActivity(), SettingsFragment.OnFragmentInteractionListener, AnkoLogger {

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
                            .replace(R.id.mainContainer,
                                    HomeFragment(), HomeFragment::class.java.name)
                            .commit()
                    true
                }

                1 -> {
                    supportFragmentManager.beginTransaction()
                            .replace(R.id.mainContainer, BookmarkFragment())
                            .addToBackStack(BookmarkFragment::class.java.name)
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
                            .addToBackStack(SettingsFragment::class.java.name)
                            .commit()
                    true
                }

                else -> {
                    true
                }
            }
        }

        setStatusBarColor(R.color.darkNavy)
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

}
