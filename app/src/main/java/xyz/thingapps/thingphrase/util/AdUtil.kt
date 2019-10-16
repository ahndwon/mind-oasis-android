package xyz.thingapps.thingphrase.util

import android.content.Context
import android.util.Log
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.InterstitialAd
import com.google.android.gms.ads.MobileAds
import xyz.thingapps.thingphrase.BuildConfig
import xyz.thingapps.thingphrase.R
import xyz.thingapps.thingphrase.sharedApp

object AdUtil {

    //    fun appInit(context: Context) {
//        MobileAds.initialize(context) {
//            Log.d(AdUtil::class.java.name, "initialization status : $it")
//        }
//    }
//
    fun appInit(context: Context) {
        MobileAds.initialize(context, context.getString(R.string.admob_app_id))
    }

    fun activityInit(context: Context, tag: String) {
        MobileAds.initialize(context) {
            Log.d(tag, "initialization status : $it")
        }
    }

    fun setupBanner(adView: AdView) {

        val builder = AdRequest.Builder()

        if (BuildConfig.DEBUG) {
            builder.addTestDevice(adView.context.getString(R.string.admob_test_device))
        }

        val adRequest = builder.build()
        adView.loadAd(adRequest)


    }

    fun showPopup(context: Context, popupAd: InterstitialAd) {
        val count = context.sharedApp.popupCount ?: 0
        Log.d(AdUtil::class.java.name, "count : $count")

        if (count % 4 == 0) {
            val builder = AdRequest.Builder()

            if (BuildConfig.DEBUG) {
                builder.addTestDevice(context.getString(R.string.admob_test_device))
            }

            popupAd.loadAd(builder.build())
            popupAd.show()
        }

        context.sharedApp.popupCount = count + 1
    }
}