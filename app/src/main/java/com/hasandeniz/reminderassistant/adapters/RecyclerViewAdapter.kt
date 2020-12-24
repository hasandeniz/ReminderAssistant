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

class RecyclerViewAdapter(private val exampleList: List<ExampleItem>) : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>(),
    View.OnClickListener, PopupMenu.OnMenuItemClickListener {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_row,parent,false)
        return  ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem  = exampleList[position]

        holder.textView1.text = currentItem.text1
        holder.textView2.text = currentItem.text2
        holder.imageText1.text = currentItem.text3
        holder.imageText2.text = currentItem.text4
        holder.imageButton.setOnClickListener(this)


    }

    override fun getItemCount() = exampleList.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val textView1: TextView = itemView.findViewById(R.id.textView1)
        val textView2: TextView = itemView.findViewById(R.id.textView2)
        val imageText1: TextView = itemView.findViewById(R.id.imageTextUp)
        val imageText2: TextView = itemView.findViewById(R.id.imageTextDown)
        val imageButton: ImageButton = itemView.findViewById(R.id.popupMenuButton)



    }

    override fun onClick(v: View?) {
        showPopupMenu(v!!)
    }

    private fun showPopupMenu(view : View){
        val popupMenu: PopupMenu = PopupMenu(view.context,view)
        popupMenu.inflate(R.menu.popup_menu)
        popupMenu.setOnMenuItemClickListener(this)
        popupMenu.show()
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        return when (item!!.itemId){
            R.id.actionPopupEdit -> {
                println("deeeenemeee edit")
                true
            }
            R.id.actionPopupDelete -> {
                println("deeeenemeee delete")
                true
            }
            else -> false
        }
    }

}