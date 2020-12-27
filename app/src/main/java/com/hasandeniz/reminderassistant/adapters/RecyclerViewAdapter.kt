package com.hasandeniz.reminderassistant.adapters


import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.PopupMenu
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hasandeniz.reminderassistant.R
import com.hasandeniz.reminderassistant.data.Item


class RecyclerViewAdapter() : RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>() {

    private var itemList = emptyList<Item>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_row, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem  = itemList[position]
        holder.textView1.text = currentItem.courseName
        holder.textView2.text = currentItem.className
        holder.imageText1.text = currentItem.startTime
        holder.imageText2.text = currentItem.finishTime

    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener, PopupMenu.OnMenuItemClickListener {
        var textView1: TextView = itemView.findViewById(R.id.textView1)
        var textView2: TextView = itemView.findViewById(R.id.textView2)
        var imageText1: TextView = itemView.findViewById(R.id.imageTextUp)
        var imageText2: TextView = itemView.findViewById(R.id.imageTextDown)
        var imageButton: ImageButton = itemView.findViewById(R.id.popupMenuButton)

        override fun onClick(v: View) {
            showPopupMenu(v)
        }

        private fun showPopupMenu(view: View) {
            val popupMenu = PopupMenu(view.context, view)
            popupMenu.inflate(R.menu.popup_menu)
            popupMenu.setOnMenuItemClickListener(this)
            popupMenu.show()
        }

        override fun onMenuItemClick(item: MenuItem): Boolean {
            return when (item.itemId) {
                R.id.actionPopupEdit -> {
                    println("onMenuItemClick: action_popup_edit @ $adapterPosition")
                    true
                }
                R.id.actionPopupDelete -> {
                    /*
                    itemList.removeAt(adapterPosition)
                    notifyItemRemoved(adapterPosition)
                    notifyItemRangeChanged(adapterPosition,itemList.size)
                    println("onMenuItemClick: action_popup_delete @ $adapterPosition")*/
                    true
                }
                else -> false
            }
        }
        init {
            imageButton.setOnClickListener(this)
        }
    }
    fun setData(item: List<Item>){
        this.itemList = item
        notifyDataSetChanged()
    }
}