package app.word.game.customview

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import app.word.game.R
import app.word.game.utlis.OptionData
import kotlinx.android.synthetic.main.cv_question.view.*

class CvQuestion @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    init {
        LayoutInflater.from(context).inflate(R.layout.cv_question, this)
    }

    fun setQuestion(question: String) {
        tvquestion.text = question
    }

    fun error() {
        container.background = resources.getDrawable(R.drawable.card_background_error)
    }

    fun claerError() {
        container.background = resources.getDrawable(R.drawable.card_background)
        tvTruAnswer.visibility = View.GONE
    }

    fun setTreuAnser(answer: String) {
        tvTruAnswer.text = answer
        tvTruAnswer.visibility = View.VISIBLE
    }
    fun setCategory(category: Int){
        ivCategory.setImageDrawable(OptionData.getCategoryImage(context,category))
    }

}