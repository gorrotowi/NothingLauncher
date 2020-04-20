package com.gorrotowi.nothinglauncher.applist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.gorrotowi.nothinglauncher.R
import kotlinx.android.synthetic.main.itemapp.view.*
import kotlin.properties.Delegates

class AppListAdapter : RecyclerView.Adapter<AppListAdapter.AppListViewHolder>() {

    var dataSource by Delegates.observable(listOf<AppItem>()) { _, _, _ -> notifyDataSetChanged() }
    private lateinit var onAppListener: (AppItem) -> Unit

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AppListViewHolder = AppListViewHolder(
        LayoutInflater
            .from(parent.context)
            .inflate(
                R.layout.itemapp,
                parent,
                false
            )
    )

    override fun getItemCount(): Int = dataSource.size

    override fun onBindViewHolder(holder: AppListViewHolder, position: Int) {
        holder.bindView(dataSource[position])
        holder.viewItem.setOnClickListener {
            if (::onAppListener.isInitialized) {
                onAppListener(dataSource[position])
            }
        }
    }

    fun setOnAppClickListener(block: (AppItem) -> Unit) {
        onAppListener = block
    }

    class AppListViewHolder(val viewItem: View) : ViewHolder(viewItem) {
        fun bindView(itemData: AppItem) {
            viewItem.txtAppItem?.text = itemData.name
        }
    }

}