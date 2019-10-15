package xyz.thingapps.thingphrase

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.google.android.gms.ads.MobileAds
import com.google.firebase.FirebaseApp
import com.jakewharton.threetenabp.AndroidThreeTen
import xyz.thingapps.thingphrase.util.MAX_INDEX

class SharedApplication : Application() {

    var maximUpdate: Long?
        get() = preferences.getLong(PREFERENCE_LAST_MAXIM_UPDATE, 0L)
        set(value) {
            if (value == null) {
                preferences.edit().remove(PREFERENCE_LAST_MAXIM_UPDATE).apply()
            } else {
                preferences.edit().putLong(PREFERENCE_LAST_MAXIM_UPDATE, value).apply()
            }
        }

    var maximIndex: Int?
        get() = preferences.getInt(PREFERENCE_MAXIM_INDEX, 0)
        set(value) {
            if (value == null) {
                preferences.edit().remove(PREFERENCE_MAXIM_INDEX).apply()
            } else {
                preferences.edit().putInt(PREFERENCE_MAXIM_INDEX, value).apply()
            }
        }

    var nextMaximTutorial: Boolean?
        get() = preferences.getBoolean(PREFERENCE_BOOKMARK_TUTORIAL, true)
        set(value) {
            if (value == null) {
                preferences.edit().remove(PREFERENCE_BOOKMARK_TUTORIAL).apply()
            } else {
                preferences.edit().putBoolean(PREFERENCE_BOOKMARK_TUTORIAL, value).apply()
            }
        }

    var popupCount: Int?
        get() = preferences.getInt(PREFERENCE_POPUP_COUNT, 0)
        set(value) {
            if (value == null) {
                preferences.edit().remove(PREFERENCE_POPUP_COUNT).apply()
            } else {
                preferences.edit().putInt(PREFERENCE_POPUP_COUNT, value).apply()
            }
        }

    private val preferences: SharedPreferences
        get() = getSharedPreferences(PREFERENCE_DOCUMENT_NAME, Context.MODE_PRIVATE)

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
        FirebaseApp.initializeApp(this)
        MobileAds.initialize(this, getString(R.string.admob_app_id_thing_piece))


        if (maximIndex == null) {
            maximIndex = (0..MAX_INDEX).random()
        }
    }

    companion object {
        const val PREFERENCE_LAST_MAXIM_UPDATE = "last_maxim_update"
        const val PREFERENCE_MAXIM_INDEX = "maxim_index"
        const val PREFERENCE_POPUP_COUNT = "popup_count"
        const val PREFERENCE_BOOKMARK_TUTORIAL = "bookmark_tutorial"
        const val PREFERENCE_DOCUMENT_NAME = "xyz.thingapps.mindoasis"
    }
}

val Context.sharedApp: SharedApplication
    get() = applicationContext as SharedApplication