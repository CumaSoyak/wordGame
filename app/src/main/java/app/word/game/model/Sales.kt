package app.word.game.model

import android.graphics.drawable.Drawable
import app.logistics.utils.helper.genericadapter.ListItemViewModel

data class Sales(
    var id: String,
    var title: String,
    var price: String,
    var image: Drawable
) : ListItemViewModel()