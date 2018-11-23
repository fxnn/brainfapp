package de.fxnn.brainfapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlin.reflect.KFunction2

class RecentFileAdapter(
    private val recentFileList: Array<RecentFile>,
    private val onRecentFileSelected: (RecentFile, View) -> Unit
) : RecyclerView.Adapter<RecentFileAdapter.ViewHolder>() {

    var selectedItemPosition = RecyclerView.NO_POSITION

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val textView = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.view_recent_file, parent, false) as TextView
        val viewHolder = ViewHolder(textView)
        viewHolder.registerOnClick(this::onItemClick)
        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textView.text = recentFileList[position].fileName
        holder.textView.isSelected = position == selectedItemPosition
    }

    private fun onItemClick(item: TextView, itemPosition: Int) {
        notifyItemChanged(selectedItemPosition)
        selectedItemPosition = itemPosition
        notifyItemChanged(selectedItemPosition)
        onRecentFileSelected(recentFileList[itemPosition], item)
    }

    override fun getItemCount(): Int = recentFileList.size

    class ViewHolder(val textView: TextView) : RecyclerView.ViewHolder(textView) {

        fun registerOnClick(onClickFunction: KFunction2<TextView, Int, Unit>) {
            textView.setOnClickListener {
                onClickFunction(textView, layoutPosition)
            }
        }

    }

}