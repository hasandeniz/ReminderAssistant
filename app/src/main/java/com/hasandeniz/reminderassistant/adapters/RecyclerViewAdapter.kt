package com.hasandeniz.reminderassistant.adapters


import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.PopupMenu
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hasandeniz.reminderassistant.ExampleItem
import com.hasandeniz.reminderassistant.R


class RecyclerViewAdapter(private var exampleList: ArrayList<ExampleItem>) : RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_row, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem  = exampleList[position]
        holder.textView1.text = currentItem.text1
        holder.textView2.text = currentItem.text2
        holder.imageText1.text = currentItem.text3
        holder.imageText2.text = currentItem.text4

    }

    override fun getItemCount(): Int {
        return exampleList.size
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
                    exampleList.removeAt(adapterPosition)
                    notifyItemRemoved(adapterPosition)
                    notifyItemRangeChanged(adapterPosition,exampleList.size)
                    println("onMenuItemClick: action_popup_delete @ $adapterPosition")
                    true
                }
                else -> false
            }
        }
        init {
            imageButton.setOnClickListener(this)
        }
    }
}