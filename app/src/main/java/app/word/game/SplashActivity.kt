package app.word.game

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import app.word.game.utlis.DialogUtils
import app.word.game.utlis.showError
import com.soyak.extensions.utils.NetworkUtils
import com.soyak.extensions.utils.extensions.launchActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (NetworkUtils.isNetworkAvailable(this)) {
            FirebaseHelper().isAppUpdate { update ->
                if (update) {
                    updateApp()
                } else {
                    launchActivity<MainActivity> {  }
                }
            }
        } else {
            showError("İnternet bağlantınızı kontrol ediniz !")
        }
    }

    fun updateApp() {
        val model = DialogUtils.DialogModel(
            "Uygulamayı güncelleyiniz",
            "Güncelle",
            "Vazgeç",
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
        val appPackageName = getPackageName()
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
