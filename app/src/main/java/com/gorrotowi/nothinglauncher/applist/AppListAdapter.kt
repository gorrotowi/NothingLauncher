package com.gorrotowi.nothinglauncher.applist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.gorrotowi.nothinglauncher.databinding.ItemappBinding
import kotlin.properties.Delegates

class AppListAdapter : RecyclerView.Adapter<AppListAdapter.AppListViewHolder>() {

    var dataSource by Delegates.observable(listOf<AppItem>()) { _, _, _ -> notifyDataSetChanged() }
    private lateinit var onAppListener: (AppItem) -> Unit
    private lateinit var onLongAppListener: (AppItem) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppListViewHolder {
        val binding = ItemappBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return AppListViewHolder(binding)
    }

    override fun getItemCount(): Int = dataSource.size

    override fun onBindViewHolder(holder: AppListViewHolder, position: Int) {
        holder.bindView(dataSource[position])

        holder.binding.root.setOnClickListener {
            if (::onAppListener.isInitialized) {
                onAppListener(dataSource[position])
            }
        }

        holder.binding.root.setOnLongClickListener {
            if (::onLongAppListener.isInitialized) {
                onLongAppListener(dataSource[position])
            }
            true
        }
    }

    fun setOnAppClickListener(block: (AppItem) -> Unit) {
        onAppListener = block
    }

    fun setOnLongAppClickListener(block: (AppItem) -> Unit) {
        onLongAppListener = block
    }

    class AppListViewHolder(val binding: ItemappBinding) : ViewHolder(binding.root) {
        fun bindView(itemData: AppItem) {
            binding.txtAppItem.text = itemData.name
        }
    }
}
