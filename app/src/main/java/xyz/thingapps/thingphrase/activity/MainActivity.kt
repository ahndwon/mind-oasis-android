package xyz.thingapps.thingphrase.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.toast
import xyz.thingapps.thingphrase.R
import xyz.thingapps.thingphrase.fragment.BookmarkFragment
import xyz.thingapps.thingphrase.fragment.HomeFragment
import xyz.thingapps.thingphrase.fragment.SettingsFragment
import xyz.thingapps.thingphrase.util.AdUtil

class MainActivity : AppCompatActivity(), AnkoLogger {
    private var backPressedTime = 0L

    companion object {
        const val FINISH_INTERVAL_TIME = 2000L
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initAd()

        supportFragmentManager.beginTransaction()
            .replace(
                R.id.fragmentContainer,
                HomeFragment(), HomeFragment::class.java.name
            )
            .commit()

        val list = listOf(
            R.id.navigation_home,
            R.id.navigation_bookmark,
//                R.id.navigation_share,
            R.id.navigation_settings
        )

        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            when (list.indexOf(menuItem.itemId)) {
                0 -> {
                    supportFragmentManager.beginTransaction()
                        .replace(
                            R.id.fragmentContainer,
                            HomeFragment(),
                            HomeFragment::class.java.name
                        )
                        .commit()
                    true
                }

                1 -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainer, BookmarkFragment())
                        .commit()
                    true
                }

                2 -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainer, SettingsFragment())
                        .commit()
                    true
                }

                else -> {
                    true
                }
            }
        }

    }

    private fun initAd() {
        AdUtil.activityInit(this, MainActivity::class.java.name)
        AdUtil.setupBanner(adView)
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
