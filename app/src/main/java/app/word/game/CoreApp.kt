package app.word.game

import android.app.Activity
import android.content.Context
import android.os.Bundle
import androidx.multidex.MultiDexApplication
import com.google.android.gms.ads.MobileAds
import com.google.firebase.firestore.FirebaseFirestore


class CoreApp : MultiDexApplication() {

    companion object{
        lateinit var context: Context
        lateinit var db: FirebaseFirestore

    }
    override fun onCreate() {
        super.onCreate()
        context = applicationContext
        db = FirebaseFirestore.getInstance()

      //  MobileAds.initialize(this, "ca-app-pub-7740710689946524~1155746649")

        registerActivityLifecycleCallbacks(object:ActivityLifecycleCallbacks{
            override fun onActivityPaused(activity: Activity) {

            }

            override fun onActivityStarted(activity: Activity) {
             }

            override fun onActivityDestroyed(activity: Activity) {

            }

            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {

            }

            override fun onActivityStopped(activity: Activity) {

            }

            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {

            }

            override fun onActivityResumed(activity: Activity) {
             }
        })

    }
}