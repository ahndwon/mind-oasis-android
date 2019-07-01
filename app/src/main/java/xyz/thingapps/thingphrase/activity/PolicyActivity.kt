package xyz.thingapps.thingphrase.activity

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity

import kotlinx.android.synthetic.main.activity_policy.*
import kotlinx.android.synthetic.main.app_bar_white.*
import xyz.thingapps.thingphrase.R

class PolicyActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_policy)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        appbarTitle.text = getString(R.string.personal_info_policy_ko)

//        setStatusBarColor(R.color.white, true)

        personalInfoWebView.loadUrl(getString(R.string.url_policy))
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.close_menu_black, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.action_close -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}
