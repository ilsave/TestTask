package ru.ilsave.testtask.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item.view.*
import ru.ilsave.testtask.R

class AdapterItems(
    var itemList: List<AdapterPojoObject>
) : RecyclerView.Adapter<AdapterItems.ItemsHolder>() {

    inner class ItemsHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemsHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false)
        return ItemsHolder(view)
    }

    override fun onBindViewHolder(holder: ItemsHolder, position: Int) {
        val curItem = itemList[position]

        holder.itemView.tvItemTitle.text = curItem.name
        holder.itemView.tvItemDate.text = curItem.date
        if (curItem.isFile){
            holder.itemView.ivItem.setImageResource(R.drawable.ic_baseline_file_24)
        } else {
            holder.itemView.ivItem.setImageResource(R.drawable.ic_baseline_folder_24)
        }


    }

    override fun getItemCount(): Int {
        return itemList.size
    }


}