package app.word.game.utlis

import android.app.Activity
import app.word.game.R
import com.tapadoo.alerter.Alerter


fun Activity.showError(message: String?) {
    Alerter.create(this)
        .setTitle(message ?: "")
        .setBackgroundColorRes(R.color.red)
        .show()
}

fun Activity.showSucces(message: String?) {
    Alerter.create(this)
        .setTitle(message ?: "")
        .setBackgroundColorRes(R.color.green)
        .show()
}

