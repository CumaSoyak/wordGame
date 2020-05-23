package app.word.game.model

import android.graphics.drawable.Drawable
import app.logistics.utils.helper.genericadapter.ListItemViewModel

data class TopMenu(
    var title: String,
    var image: Drawable
) : ListItemViewModel()