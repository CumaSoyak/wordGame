package app.word.game.utlis

import android.content.Context
import android.graphics.drawable.Drawable
import app.word.game.R

object OptionData {

    fun getCategory(context: Context, category: Int): String {
        var mCategory: String = ""
        when (category) {
            1 -> {
                mCategory = context.getString(R.string.tarih)

            }
            2 -> {
                mCategory = context.getString(R.string.bilim)

            }
            3 -> {
                mCategory = context.getString(R.string.eglence)


            }
            4 -> {
                mCategory = context.getString(R.string.cografya)

            }
            5 -> {
                mCategory = context.getString(R.string.sanat)


            }
            6 -> {
                mCategory = context.getString(R.string.spor)


            }
        }
        return mCategory
    }

    fun getCategoryImage(context: Context, category: Int): Drawable {
        var mCategory: Drawable? = null
        when (category) {
            1 -> {
                mCategory = context.resources.getDrawable(R.drawable.tarihim)
            }
            2 -> {
                mCategory = context.resources.getDrawable(R.drawable.bilim)
            }
            3 -> {
                mCategory = context.resources.getDrawable(R.drawable.eglence)
            }
            4 -> {
                mCategory = context.resources.getDrawable(R.drawable.cografya)
            }
            5 -> {
                mCategory = context.resources.getDrawable(R.drawable.sanat)

            }
            6 -> {
                mCategory = context.resources.getDrawable(R.drawable.spor)

            }
            else -> {
                print("Category budur :" + category.toString())
            }
        }
        return mCategory!!
    }
}