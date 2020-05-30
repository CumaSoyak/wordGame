package app.word.game.utlis

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatImageView
import app.word.game.R

object DialogUtils {
    lateinit var myDialog: Dialog

    @SuppressLint("SupportAnnotationUsage")
    data class DialogModel(

        var title: String? = null,

        var positive: String? = null,

        var negative: String? = null,

        var icon: Int,
        @DrawableRes

        var isNegativeButton: Boolean = false

    )

    interface DialogListener {

        fun onPositiveClick()

        fun onNegativeClick()

    }

    fun showPopupChoose(context: Context, watchVidoe: () -> Unit) {
        myDialog = Dialog(context)
        myDialog.setContentView(R.layout.dialog_choose)
        myDialog.show()
        val btnPlay: AppCompatButton = myDialog.findViewById(R.id.btnVieoPlay)
        val ivCancel: ImageView = myDialog.findViewById(R.id.ivCancel)
        btnPlay.setOnClickListener {
            myDialog.dismiss()
            watchVidoe.invoke()
        }
        ivCancel.setOnClickListener {
            myDialog.dismiss()
        }
        myDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        myDialog.show()
        myDialog.setCancelable(false)

    }

    fun showPopupMode(context: Context,close:()->Unit) {
        myDialog = Dialog(context)
        myDialog.setContentView(R.layout.dialog_mode)

        val llEnglish: LinearLayout = myDialog.findViewById(R.id.llEnglish)
        val llCulture: LinearLayout = myDialog.findViewById(R.id.llCulture)
        val ivCheckCulture: ImageView = myDialog.findViewById(R.id.ivCheckCulture)
        val ivCheckEnglish: ImageView = myDialog.findViewById(R.id.ivCheckEnglish)
        val ivClose: ImageView = myDialog.findViewById(R.id.ivClose)
        ivClose.setOnClickListener {
            myDialog.dismiss()
            close.invoke()
        }
        if (PrefUtils.getOption()) {
            ivCheckEnglish.setImageDrawable(context.resources.getDrawable(R.drawable.checked))
        } else {
            ivCheckCulture.setImageDrawable(context.resources.getDrawable(R.drawable.checked))
        }

        llEnglish.setOnClickListener {
            ivCheckEnglish.setImageDrawable(context.resources.getDrawable(R.drawable.checked))
            ivCheckCulture.setImageDrawable(null)
            PrefUtils.setOption(true)
        }
        llCulture.setOnClickListener {
            ivCheckCulture.setImageDrawable(context.resources.getDrawable(R.drawable.checked))
            ivCheckEnglish.setImageDrawable(null)
            PrefUtils.setOption(false)
        }
        myDialog.setCancelable(false)
        myDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        myDialog.show()
    }

    fun showPopupFirstLearning(context: Context,click: () -> Unit) {
        myDialog = Dialog(context)
        myDialog.setContentView(R.layout.dialog_learning)
        val btnNext: Button = myDialog.findViewById(R.id.btnNext)
        btnNext.setOnClickListener {
            click.invoke()
            myDialog.dismiss()
        }
        myDialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        myDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        myDialog.show()
    }

    /**Listener mesajları*/
    fun showPopupModel(context: Context, model: DialogModel, listener: DialogListener) {
        myDialog = Dialog(context)
        myDialog.setContentView(R.layout.model_dialog)
        val btnDecline: AppCompatButton = myDialog.findViewById(R.id.btnDecline)
        val btnAccept: AppCompatButton = myDialog.findViewById(R.id.btnAccept)
        val txtPopup: TextView = myDialog.findViewById(R.id.tvTitle)
        val close: ImageView = myDialog.findViewById(R.id.ivClose)
        val image: AppCompatImageView = myDialog.findViewById(R.id.lAnimationView)


        if (model.isNegativeButton) {

        }
        image.setImageDrawable(context.resources.getDrawable(model.icon))
        txtPopup.text = model.title
        btnDecline.text = model.negative
        btnAccept.text = model.positive

        btnDecline.setOnClickListener {
            myDialog?.dismiss()
            listener.onNegativeClick()

        }
        btnAccept.setOnClickListener {
            myDialog?.dismiss()
            listener.onPositiveClick()

        }
        myDialog.setCancelable(false)
        myDialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        myDialog.show()
    }


}