package app.word.game.customview

import android.content.Context
import android.os.CountDownTimer
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.LinearLayout
import app.word.game.R
import app.word.game.utlis.PrefUtils
import kotlinx.android.synthetic.main.cv_toolbar.view.*
import java.util.concurrent.TimeUnit

class CvToolbar @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {
    var answer: Int = 0
    lateinit var animationFalse: Animation


    init {
        LayoutInflater.from(context).inflate(R.layout.cv_toolbar, this)
        animationFalse = AnimationUtils.loadAnimation(context, R.anim.vibration_heart)
       // tvLevel.text = PrefUtils.getLevel()
    }

    fun calculateTime(iGameOver: IGameOver) {
        val timer = object : CountDownTimer(20000, 1000) {
            override fun onFinish() {
                iGameOver.finishGame()
            }

            override fun onTick(millisUntilFinished: Long) {
                val millis = millisUntilFinished
                val seconds =
                    ((TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(
                        TimeUnit.MILLISECONDS.toMinutes(millis)
                    )).toString())
                tvTime.text = seconds
            }


        }
        timer.start()
    }

    fun falseAnswer(iGameOver: IGameOver) {
        answer++
        when (answer) {
            1 -> {
                ivHeart1.setImageResource(R.drawable.ic_heart_kirik)
                ivHeart1.startAnimation(animationFalse)
            }
            2 -> {
                ivHeart2.setImageResource(R.drawable.ic_heart_kirik)
                ivHeart2.startAnimation(animationFalse)

            }
            3 -> {
                ivHeart3.setImageResource(R.drawable.ic_heart_kirik)
                ivHeart3.startAnimation(animationFalse)

                iGameOver.finishGame()

            }
        }
    }

}

interface IGameOver {
    fun finishGame()
}