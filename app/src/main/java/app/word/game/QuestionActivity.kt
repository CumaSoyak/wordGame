package app.word.game

import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import app.word.game.customview.IGameOver
import app.word.game.model.Question
import app.word.game.utlis.OptionData
import app.word.game.utlis.PrefUtils
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_question.*


class QuestionActivity : AppCompatActivity(), IGameOver {

    lateinit var animationTrue: Animation
    lateinit var animationFalse: Animation
    var questionList: ArrayList<Question> = arrayListOf()
    var questionItem = 0

    var mTrueAnswer: Int = 0
    var mFalseAnswer: Int = 0
    var allQuestionCompleted = false

    val category by lazy {
        intent.getIntExtra("category", -1)
    }
    lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question)
        animationTrue = AnimationUtils.loadAnimation(this, R.anim.left)
        animationFalse = AnimationUtils.loadAnimation(this, R.anim.vibration)
        initListener()
        db = FirebaseFirestore.getInstance()
        tvQuestion.setCategory(category)
        questionList = PrefUtils.getQuestion(PrefUtils.getOptionCategory()+category.toString())!!
        initData()
    }

    fun initData() {
        tvQuestion.setQuestion(questionList.get(0).question)
        firstChoose.text = questionList.get(0).chooseOne
        secondChoose.text = questionList.get(0).chooseTwo
    }

    fun initListener() {
        cvToolbar.calculateTime(this)
        firstChoose.setOnClickListener {
            findAnswer(firstChoose.text.toString(), secondChoose.text.toString())
        }
        secondChoose.setOnClickListener {
            findAnswer(secondChoose.text.toString(), firstChoose.text.toString())
        }
        tvNextQuestion.setOnClickListener {
            tvNextQuestion.visibility = View.GONE
            tvQuestion.claerError()
        }
    }


    fun findAnswer(answer: String, trueAnswer: String) {
        if (questionList.size > questionItem) {
            if (questionList.get(questionItem).answer.equals(answer)) {
                tvQuestion.startAnimation(animationTrue)
                mTrueAnswer++
            } else {
                mFalseAnswer++
                tvQuestion.startAnimation(animationFalse)
                cvToolbar.falseAnswer(this)
                tvQuestion.error()
                tvNextQuestion.visibility = View.VISIBLE
                tvQuestion.setTreuAnser(trueAnswer)
            }
            questionItem++
            if (questionList.size > questionItem) {
                tvQuestion.setQuestion(questionList.get(questionItem).question)
                firstChoose.text = questionList.get(questionItem).chooseOne
                secondChoose.text = questionList.get(questionItem).chooseTwo
            } else {
                allQuestionCompleted = true
                win()
            }
        }

    }

    override fun finishGame() {
        if (!this.isFinishing) { //show dialog
            win()
        }
    }

    fun win() {
        var myDialog = Dialog(this)
        myDialog.setContentView(R.layout.win_popup)
        val heart: TextView = myDialog.findViewById(R.id.tvHeart)
        val truePoint: TextView = myDialog.findViewById(R.id.tvTrue)
        val falsePoint: TextView = myDialog.findViewById(R.id.tvFalse)
        val btnRestart: AppCompatButton = myDialog.findViewById(R.id.btnRestart)
        val tvCong: TextView = myDialog.findViewById(R.id.tvCang)

        if (mFalseAnswer == 0) {
            if (allQuestionCompleted) {
                PrefUtils.setHeart(1)
                tvCong.visibility = View.VISIBLE
            }
            heart.text = "3"
        } else if (mFalseAnswer == 3) {
            PrefUtils.setHeart(-1)
        } else {
            heart.text = (3 - mFalseAnswer).toString()
        }
        truePoint.text = mTrueAnswer.toString()
        falsePoint.text = mFalseAnswer.toString()

        val kategori_image: ImageView = myDialog.findViewById(R.id.kategori_image)
        val kategori_text: TextView = myDialog.findViewById(R.id.kategori_text)


        kategori_image.setImageDrawable(OptionData.getCategoryImage(this, category))
        kategori_text.text = OptionData.getCategory(this, category)

        btnRestart.setOnClickListener {
            finish()
            myDialog.dismiss()
            PrefUtils.setPointTrue(category.toString(), mTrueAnswer)
            PrefUtils.setPointFalse(category.toString(), mFalseAnswer)
        }

        myDialog.setCancelable(false)
        myDialog.show()
    }


    fun dummyQuestion(): ArrayList<Question> {
        var question: ArrayList<Question> = arrayListOf()
        question.add(
            Question(
                "we can go ss cuma \n usa dd",
                "we can go ss sdfdfsdf sdfsdf",
                "sen",
                "ben"
            )
        )
        question.add(Question("dünya", "fff", "dünya", "dünya"))
        question.add(Question("ankara", "ankara", "o", "ankara"))
        question.add(Question("anne", "mersin", "anne", "anne"))
        question.add(Question("nasıl", "nasıl", "o", "nasıl"))
        question.add(Question("yunus", "s", "yunus", "yunus"))
        question.add(Question("dünya", "fff", "dünya", "dünya"))
        question.add(Question("ankara", "ankara", "o", "ankara"))
        question.add(Question("anne", "mersin", "anne", "anne"))
        return question
    }
}
