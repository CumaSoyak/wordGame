package app.word.game

import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import app.word.game.CoreApp.Companion.db
import app.word.game.OtherUtils.getCountryCode
import app.word.game.model.Question
import app.word.game.utlis.DialogUtils
import app.word.game.utlis.PrefUtils
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.InterstitialAd
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.reward.RewardItem
import com.google.android.gms.ads.reward.RewardedVideoAd
import com.google.android.gms.ads.reward.RewardedVideoAdListener
import com.google.gson.Gson
import com.soyak.extensions.utils.extensions.launchActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    lateinit var animation: Animation
    lateinit var mediaPlayer: MediaPlayer

    var rotation: Int = 0

    private var mRewardedVideoAd: RewardedVideoAd? = null
    private var interstitialAd: InterstitialAd? = null
    private var adCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        interstitialAd = InterstitialAd(this)
        interstitialAd?.adUnitId = "ca-app-pub-7740710689946524/5048304902"

        animation = AnimationUtils.loadAnimation(this, R.anim.rotate)
        mediaPlayer = MediaPlayer.create(this, R.raw.play_game)
        listener()
        tvLevel.text = "Level: " + PrefUtils.getLevel().toString()
        setPoint()
        firstOpen()
        questionUpdate()
        rotation = (1..6).random()

    }

    fun questionUpdate() {
        if (PrefUtils.getQuestion("16")?.size == 0) {
            getQuestionCulture(1)
        }
        FirebaseHelper().isAppUpdateQuestion {
            if (it) {
                getQuestionCulture(1)
            }
        }
    }


    fun listener() {
        ivPlay.setOnClickListener {
            if (PrefUtils.getHeart() == 0) {
                DialogUtils.showPopupChoose(this) {
                    if (mRewardedVideoAd?.isLoaded!!) {
                        mRewardedVideoAd!!.show()
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
        ivSettings.setOnClickListener {
            DialogUtils.showPopupMode(this) {}
        }
        ivRating.setOnClickListener {
            openGooglePlay()
        }

    }

    fun startReturn() {
        rotation = ((rotation + 4) % 6)
        if (rotation == 0) {
            rotation = (1..6).random()
        }
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
        if (!PrefUtils.getNotAds() && !PrefUtils.getNotAdsAndOffline()) {
            adCount++
            if (adCount != 0 && adCount % 3 == 0) {
                interstitialAd?.show()
                interstitialAd?.adListener = object : AdListener() {
                    override fun onAdLoaded() {
                        super.onAdLoaded()

                    }
                }
            } else {
                fullScreenAd()
            }
        }
        restartCategory()
    }

    fun fullScreenAd() {
        interstitialAd?.loadAd(AdRequest.Builder().build())

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
        category_history.progress(PrefUtils.progressCalculate("1"))
        category_sience.progress(PrefUtils.progressCalculate("2"))
        category_fun.progress(PrefUtils.progressCalculate("3"))
        category_geography.progress(PrefUtils.progressCalculate("4"))
        category_art.progress(PrefUtils.progressCalculate("5"))
        category_sport.progress(PrefUtils.progressCalculate("6"))

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
            PrefUtils.setLevel((PrefUtils.pointSumTrue() - PrefUtils.pointSumFalse() / 10))
        }
    }

    fun rewardListener() {
        mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(this)
        mRewardedVideoAd?.loadAd(
            "ca-app-pub-7740710689946524/6319339764",
            AdRequest.Builder().build()
        )
        mRewardedVideoAd?.rewardedVideoAdListener = object : RewardedVideoAdListener {
            override fun onRewardedVideoAdClosed() {
                print("")
            }

            override fun onRewardedVideoAdLeftApplication() {
                print("")
            }

            override fun onRewardedVideoAdLoaded() {
                print("")
            }

            override fun onRewardedVideoAdOpened() {
                print("")
            }

            override fun onRewardedVideoCompleted() {
            }

            override fun onRewarded(reward: RewardItem?) {
                PrefUtils.setHeart(reward?.amount!!)
                setPoint()

            }

            override fun onRewardedVideoStarted() {

            }

            override fun onRewardedVideoAdFailedToLoad(p0: Int) {

            }
        }

    }

    fun firstOpen() {
        if (PrefUtils.checkIsFirstTimeOpen()) {
            DialogUtils.showPopupMode(this) {
                DialogUtils.showPopupFirstLearning(this) {
                    ivPlay.performClick()
                }
            }
        }

//        var count: Int = 0
//        val timer = object : CountDownTimer(1000, 10) {
//            override fun onFinish() {
//            }
//
//            override fun onTick(time: Long) {
//                100 - count++
//                category_history.progress(count)
//                category_sience.progress(count)
//                category_fun.progress(count)
//                category_geography.progress(count)
//                category_art.progress(count)
//                category_sport.progress(count)
//            }
//
//
//        }
//        timer.start()

    }

    fun getQuestionEnglish(category: Int) {
        val questionList: ArrayList<Question> = arrayListOf()
        val docRef = db.collection("question").document("info").collection(category.toString())
            .whereEqualTo("option", "2")
        docRef.addSnapshotListener { snapshot, e ->
            snapshot?.forEachIndexed { index, queryDocumentSnapshot ->
                val data: Question = queryDocumentSnapshot.toObject(Question::class.java)
                questionList.add(data)
            }
            if (category <= 6) {
                PrefUtils.setQuestion("2", category.toString(), Gson().toJson(questionList))
                getQuestionEnglish(category + 1)
                //  getQuestionCulture(1)
            }

        }
    }

    fun getQuestionCulture(category: Int) {
        val questionList: ArrayList<Question> = arrayListOf()
        val docRef = db.collection("question").document("info").collection(category.toString())
            .whereEqualTo("option", "1" + getCountryCode())
        docRef.addSnapshotListener { snapshot, e ->
            snapshot?.forEachIndexed { index, queryDocumentSnapshot ->
                val data: Question = queryDocumentSnapshot.toObject(Question::class.java)
                questionList.add(data)
            }
            if (category <= 6) {
                PrefUtils.setQuestion("1", category.toString(), Gson().toJson(questionList))
                getQuestionCulture(category + 1)
            }

        }
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
