package app.word.game.utlis.genericadapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.RecyclerView

import app.logistics.utils.helper.genericadapter.ListItemViewModel
import com.google.android.gms.ads.AdRequest

class GenericAdapter<T : ListItemViewModel>(
    @LayoutRes val layoutId: Int, val listener: OnListItemViewClickListener? = null,
    val itemType: String? = null
) :
    RecyclerView.Adapter<GenericAdapter.GenericViewHolder<T>>() {

    private val items = mutableListOf<T>()
    private var inflater: LayoutInflater? = null
    private var onListItemViewClickListener = listener

    companion object {
        private var isAdMobRequestFirst = true
        lateinit var adRequest: AdRequest

    }

    fun addItems(items: ArrayList<T>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    fun clearItems() {
        this.items.clear()
        notifyDataSetChanged()
    }

    fun setOnListItemViewClickListener(onListItemViewClickListener: OnListItemViewClickListener?) {
        this.onListItemViewClickListener = onListItemViewClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenericViewHolder<T> {
        val layoutInflater = inflater ?: LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ViewDataBinding>(layoutInflater, layoutId, parent, false)
        return GenericViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GenericViewHolder<T>, position: Int) {
        val itemViewModel = items[position]
        itemViewModel.adapterPosition = position
        itemViewModel.itemSize = items.size
        //itemViewModel.list.addAll(items)
        onListItemViewClickListener?.let { itemViewModel.onListItemViewClickListener = it }
        holder.bind(itemViewModel)
    }


    class GenericViewHolder<T : ListItemViewModel>(private val binding: ViewDataBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(itemViewModel: T) {
            binding.setVariable(BR.vm, itemViewModel)
            binding.executePendingBindings()

        }

    }
    override fun getItemCount(): Int = items.size
    interface OnListItemViewClickListener {
        fun onClick(view: View, position: Int, model: ListItemViewModel) {}
        fun onClickDetail(view: View, position: Int, model: ListItemViewModel) {}
        fun onClickUserDetail(view: View, position: Int, model: ListItemViewModel) {}
        fun onClickOptionSettings(view: View, position: Int, model: ListItemViewModel) {}
    }

}