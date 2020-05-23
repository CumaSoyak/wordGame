package app.word.game.utlis

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.soyak.extensions.utils.extensions.load


@SuppressLint("StaticFieldLeak")
object BindingUtils {

    var mView: View? = null
    var mViewString: String? = null

    @JvmStatic
    @BindingAdapter("imageDrawable")
    fun imageDrawable(image: ImageView, drawable: Drawable) {
        image.setImageDrawable(drawable)
    }

    @JvmStatic
    @BindingAdapter("toImageUrl")
    fun showImage(view: ImageView, url: String?) {
        view.load(url)
    }


}
