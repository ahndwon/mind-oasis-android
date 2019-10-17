package xyz.thingapps.thingphrase.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.InterstitialAd
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import xyz.thingapps.thingphrase.BuildConfig
import xyz.thingapps.thingphrase.R
import xyz.thingapps.thingphrase.util.AdUtil
import java.util.concurrent.TimeUnit

class SplashActivity : AppCompatActivity() {

    private val disposeBag = CompositeDisposable()
    private lateinit var popupAd: InterstitialAd

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        setupPopupAd()

        Single.create<Unit> { emitter ->
            emitter.onSuccess(Unit)
        }
            .delay(2000, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                if (popupAd.isLoaded) {
                    popupAd.show()
                } else {
                    Log.d("SplashActivity", "The interstitial wasn't loaded yet.")
                    startMainActivity()
                }

            }, {
                Log.d(SplashActivity::class.java.name, "main activity start failed", it)
            }).addTo(disposeBag)
    }

    private fun setupPopupAd() {
        AdUtil.activityInit(this, SplashActivity::class.java.name)

        popupAd = InterstitialAd(this)
        popupAd.adUnitId = getString(R.string.admob_popup_id)

        val builder = AdRequest.Builder()

        if (BuildConfig.DEBUG) {
            builder.addTestDevice(getString(R.string.admob_test_device))
        }

        popupAd.loadAd(builder.build())

        popupAd.adListener = object : AdListener() {
            override fun onAdFailedToLoad(errorCode: Int) {
                startMainActivity()
            }

            override fun onAdClosed() {
                startMainActivity()
            }

            override fun onAdLoaded() {
                popupAd.show()
                disposeBag.dispose()
                super.onAdLoaded()
            }
        }
    }


    private fun startMainActivity() {
        val intent = Intent(this@SplashActivity, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onDestroy() {
        disposeBag.dispose()
        super.onDestroy()
    }

}
