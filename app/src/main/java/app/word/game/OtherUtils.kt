package app.word.game

import android.content.Context
import android.content.pm.PackageManager
import android.telephony.TelephonyManager
import app.word.game.CoreApp.Companion.context

object OtherUtils {
    fun versionNumber(): String? {
        var version: String = ""
        try {
            val pInfo = context.packageManager.getPackageInfo(context.packageName, 0)
            version = pInfo.versionName

        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        return version
    }

    fun getCountryCode(): String {
        var location = if (!getCurrentCountryCode().equals("tr")) {
            ""
        } else {
            "tr"
        }
        return location
    }

    fun getCurrentCountryCode(): String {
        val telephonyManager =
            context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        val countryIso = telephonyManager.simCountryIso.toLowerCase()
        return countryIso
    }
}