package app.word.game.customview

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import app.word.game.R
import kotlinx.android.synthetic.main.cv_category_menu.view.*

class CvTopMenu @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    init {
        LayoutInflater.from(context).inflate(R.layout.cv_category_menu, this)
        val values = context.obtainStyledAttributes(
            attrs,
            R.styleable.CvTopMenu, 0, 0
        )
        values.recycle()
    }


    fun setPoint(point: String, drawable: Drawable) {
        ivCategory.setImageDrawable(drawable)
        tvPoint.text = point
    }

}