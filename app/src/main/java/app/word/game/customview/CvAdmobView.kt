package app.word.game.customview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import app.word.game.R
import app.word.game.utlis.PrefUtils
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView

class CvAdmobView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    init {

        LayoutInflater.from(context).inflate(R.layout.item_admob, this)
        if (!PrefUtils.getNotAds() && !PrefUtils.getNotAdsAndOffline()) {
            val adRequest = AdRequest.Builder().build()
            val addMob = findViewById<AdView>(R.id.adView)
            addMob.loadAd(adRequest)
        }


    }
}