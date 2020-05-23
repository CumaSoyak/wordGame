package app.word.game.customview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import app.word.game.R
import kotlinx.android.synthetic.main.cv_category_item.view.*

class CvCategoryItem @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    init {
        LayoutInflater.from(context).inflate(R.layout.cv_category_item, this)
        val values = context.obtainStyledAttributes(attrs,
            R.styleable.CvCategoryItem, 0, 0)

        values.getDrawable(R.styleable.CvCategoryItem_image).let {
            ivCategory.setImageDrawable(it)
        }
        values.getString(R.styleable.CvCategoryItem_title)?.let {
            tvCategoryTitle.text = it
        }
        values.recycle()
    }

    fun imageChange() {
        ivCategory.setImageResource(R.drawable.checked)
    }
    fun imageChange(image:Int) {
        ivCategory.setImageResource(image)
    }
    fun progress(progress:Int){
        tvProgress.progress=progress
    }

}