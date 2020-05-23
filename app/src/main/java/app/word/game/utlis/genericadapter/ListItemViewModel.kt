package app.logistics.utils.helper.genericadapter

import app.word.game.utlis.genericadapter.GenericAdapter


abstract class ListItemViewModel {
    var adapterPosition: Int = -1
    var onListItemViewClickListener: GenericAdapter.OnListItemViewClickListener? = null
    var itemSize: Int = -1
    val list = mutableListOf<Any>()
}