package app.word.game

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import app.word.game.utlis.DialogUtils
import app.word.game.utlis.PrefUtils
import app.word.game.utlis.showError
import com.soyak.extensions.utils.NetworkUtils
import com.soyak.extensions.utils.extensions.launchActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (intent.hasExtra("puan")) {
            openGooglePlay()
        } else if (PrefUtils.getOffline() || PrefUtils.getNotAdsAndOffline()) {
            nextMainActivity()
        } else {
            if (NetworkUtils.isNetworkAvailable(this)) {
                nextMainActivity()
            } else {
                showError("İnternet bağlantınızı kontrol ediniz !")
            }
        }

    }

    fun nextMainActivity() {
        FirebaseHelper().isAppUpdate { update ->
            if (update) {
                updateApp()
            } else {
                if (BuildConfig.DEBUG) {
                    launchActivity<EnterActivity> { }
                } else {
                    launchActivity<MainActivity> { }
                }
            }
        }
    }

    fun updateApp() {
        val model = DialogUtils.DialogModel(
            getString(R.string.guncelle_uygulama),
            getString(R.string.guncelle),
            getString(R.string.vazgec),
            R.drawable.ic_success,
            false
        )
        DialogUtils.showPopupModel(this, model, object : DialogUtils.DialogListener {
            override fun onNegativeClick() {

            }

            override fun onPositiveClick() {
                openGooglePlay()
            }
        })
    }

    fun openGooglePlay() {
        val appPackageName = packageName
        try {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("market://details?id=" + appPackageName)
                )
            )
        } catch (anfe: android.content.ActivityNotFoundException) {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)
                )
            )
        }
    }

}
