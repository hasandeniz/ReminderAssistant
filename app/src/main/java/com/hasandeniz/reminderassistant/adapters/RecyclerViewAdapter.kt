package com.hasandeniz.reminderassistant.adapters


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.hasandeniz.reminderassistant.R
import com.hasandeniz.reminderassistant.data.Item
import com.hasandeniz.reminderassistant.data.ItemViewModel
import com.hasandeniz.reminderassistant.fragments.FridayFragment
import kotlinx.coroutines.InternalCoroutinesApi
import java.util.*



class RecyclerViewAdapter : RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>() {
    private var itemList: MutableList<Item> = Collections.emptyList()
    private lateinit var listener: ItemListener
    private lateinit var editListener: OnItemClickListener

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
        holder.imageButton.setOnClickListener(View.OnClickListener { view ->
            listener.onItemClicked(currentItem, position)
            notifyDataSetChanged()
        })
        holder.itemView.setOnClickListener(View.OnClickListener { view ->
            editListener.onEditItemClicked(currentItem, position)
            notifyDataSetChanged()
        })


    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textView1: TextView = itemView.findViewById(R.id.textView1)
        var textView2: TextView = itemView.findViewById(R.id.textView2)
        var imageText1: TextView = itemView.findViewById(R.id.imageTextUp)
        var imageText2: TextView = itemView.findViewById(R.id.imageTextDown)
        var imageButton: ImageButton = itemView.findViewById(R.id.popupMenuButton)



    }
    fun setData(item: MutableList<Item>){
        this.itemList = item
        notifyDataSetChanged()
    }
    //for delete button
    interface ItemListener {
        fun onItemClicked(item: Item, position: Int)
    }
    fun setListener(listener: ItemListener) {
        this.listener = listener
    }
    //for item itself
    interface OnItemClickListener{
        fun onEditItemClicked(item: Item, position: Int)
    }
    fun setEditItemClickListener(editListener: OnItemClickListener){
        this.editListener = editListener
    }
}