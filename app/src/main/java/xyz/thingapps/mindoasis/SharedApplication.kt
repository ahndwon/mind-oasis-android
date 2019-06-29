package xyz.thingapps.mindoasis

import android.app.Application
import com.google.firebase.FirebaseApp

class SharedApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
    }
}