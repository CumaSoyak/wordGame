package app.word.game

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import app.word.game.utlis.DialogUtils
import app.word.game.utlis.PrefUtils
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.InterstitialAd
import com.google.android.gms.ads.reward.RewardItem
import com.google.android.gms.ads.reward.RewardedVideoAd
import com.google.android.gms.ads.reward.RewardedVideoAdListener
import com.soyak.extensions.utils.extensions.launchActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    lateinit var animation: Animation
    lateinit var mediaPlayer: MediaPlayer

    var rotation: Int = 0

    private val mRewardedVideoAd: RewardedVideoAd? = null
    private var interstitialAd: InterstitialAd? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        animation = AnimationUtils.loadAnimation(this, R.anim.rotate)
        mediaPlayer = MediaPlayer.create(this, R.raw.play_game)
        listener()
        tvLevel.text = PrefUtils.getLevel()
        setPoint()
        fullScreenAd()
    }

    fun fullScreenAd() {
        interstitialAd = InterstitialAd(this)
        interstitialAd?.adUnitId = "ca-app-pub-7740710689946524/5048304902"
        interstitialAd?.loadAd(AdRequest.Builder().build())
        interstitialAd?.adListener = object : AdListener() {
            override fun onAdLoaded() {
                super.onAdLoaded()

            }
        }
    }

    fun listener() {
        ivPlay.setOnClickListener {
            if (PrefUtils.getHeart().equals("0")) {
                DialogUtils.showPopupChoose(this) {
                    if (mRewardedVideoAd?.isLoaded!!) {
                        mRewardedVideoAd.show()
                    }
                }
            } else {
                startReturn()
            }
        }
        tvSalles.setOnClickListener {
            launchActivity<SalesActivity> { }
        }
        animationListener()
        rewardListener()
    }

    fun startReturn() {
        rotation = (1..6).random()
        mediaPlayer.start()
        ivPlay.startAnimation(animation)
    }

    fun animationListener() {
        animation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(animation: Animation?) {

            }

            override fun onAnimationEnd(animation: Animation?) {
                ivPlay.isEnabled = true
                when (rotation) {
                    1 -> {
                        ivPlay.rotation = 315f //Tarih
                        category_history.imageChange()
                    }
                    2 -> {
                        ivPlay.rotation = 0f //Blim
                        category_sience.imageChange()
                    }
                    3 -> {
                        ivPlay.rotation = 45f //Eğlence
                        category_fun.imageChange()
                    }
                    4 -> {
                        ivPlay.rotation = 225f //Coğrafya
                        category_geography.imageChange()
                    }
                    5 -> {
                        ivPlay.rotation = 180f //Sanat
                        category_art.imageChange()
                    }
                    6 -> {
                        ivPlay.rotation = 135f //Spor
                        category_sport.imageChange()
                    }
                }

                Handler().postDelayed({
                    var intent = Intent(this@MainActivity, QuestionActivity::class.java)
                    intent.putExtra("category", rotation)
                    startActivityForResult(intent, 0)
                }, 200)
            }

            override fun onAnimationStart(animation: Animation?) {
                ivPlay.isEnabled = false
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        interstitialAd?.show()
        restartCategory()
    }

    fun restartCategory() {
        category_history.imageChange(R.drawable.tarihim)
        category_sience.imageChange(R.drawable.bilim)
        category_fun.imageChange(R.drawable.eglence)
        category_geography.imageChange(R.drawable.cografya)
        category_art.imageChange(R.drawable.sanat)
        category_sport.imageChange(R.drawable.spor)
        setPoint()
    }

    fun setPoint() {
        //ca-app-pub-7740710689946524/5048304902
        category_history.progress(PrefUtils.getPointTrue("1").toInt())
        category_sience.progress(PrefUtils.getPointTrue("2").toInt())
        category_fun.progress(PrefUtils.getPointTrue("3").toInt())
        category_geography.progress(PrefUtils.getPointTrue("4").toInt())
        category_art.progress(PrefUtils.getPointTrue("5").toInt())
        category_sport.progress(PrefUtils.getPointTrue("6").toInt())

        cvHeart.setPoint(
            PrefUtils.getHeart().toString(),
            resources.getDrawable(R.drawable.ic_heart)
        )
        cvTopTrue.setPoint(
            PrefUtils.pointSumTrue().toString(),
            resources.getDrawable(R.drawable.ic_true)
        )
        cvTopFalse.setPoint(
            PrefUtils.pointSumFalse().toString(),
            resources.getDrawable(R.drawable.ic_false)
        )


        if (PrefUtils.pointSumTrue() > PrefUtils.pointSumTrue()) {
            PrefUtils.setLevel((PrefUtils.pointSumTrue() / 15).toString())
        }
    }

    fun rewardListener() {
        mRewardedVideoAd?.loadAd(
            "ca-app-pub-7740710689946524/6319339764",
            AdRequest.Builder().build()
        )
        mRewardedVideoAd?.rewardedVideoAdListener = object : RewardedVideoAdListener {
            override fun onRewardedVideoAdClosed() {

            }

            override fun onRewardedVideoAdLeftApplication() {

            }

            override fun onRewardedVideoAdLoaded() {

            }

            override fun onRewardedVideoAdOpened() {

            }

            override fun onRewardedVideoCompleted() {
                PrefUtils.setHeart(3)
            }

            override fun onRewarded(p0: RewardItem?) {

            }

            override fun onRewardedVideoStarted() {

            }

            override fun onRewardedVideoAdFailedToLoad(p0: Int) {

            }
        }

    }

}
