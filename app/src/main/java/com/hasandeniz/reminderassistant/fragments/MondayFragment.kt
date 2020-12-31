package com.hasandeniz.reminderassistant.fragments

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.hasandeniz.reminderassistant.AddEventActivity
import com.hasandeniz.reminderassistant.R
import com.hasandeniz.reminderassistant.adapters.RecyclerViewAdapter
import com.hasandeniz.reminderassistant.data.Item
import com.hasandeniz.reminderassistant.data.ItemViewModel
import com.hasandeniz.reminderassistant.globalList
import kotlinx.android.synthetic.main.fragment_monday.view.*
import kotlinx.coroutines.InternalCoroutinesApi


class MondayFragment : Fragment(),RecyclerViewAdapter.ItemListener, RecyclerViewAdapter.OnItemClickListener {
    @InternalCoroutinesApi
    private lateinit var mItemViewModel: ItemViewModel
    @InternalCoroutinesApi
    override fun onCreateView(

            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_monday, container, false)

        val adapter = RecyclerViewAdapter()
        val recyclerView = view.recyclerViewMonday
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter.setListener(this)
        adapter.setEditItemClickListener(this)
        mItemViewModel = ViewModelProvider(this).get(ItemViewModel::class.java)
        mItemViewModel.readMondayData.observe(viewLifecycleOwner, Observer { item ->
            adapter.setData(item as ArrayList<Item>)

        })
        mItemViewModel.getIdData.observe(viewLifecycleOwner,{item->
            var globalList2 = listOf<Int>()
            if(item.isEmpty()){
                globalList2 = globalList.sorted()
                globalList[0] = globalList2[6]

            }else {
                globalList[0] = item[0]
            }
        })
        return view
    }
    @InternalCoroutinesApi
    override fun onItemClicked(item: Item, position: Int) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes"){ _, _ ->
            mItemViewModel.deleteItem(item)
            Toast.makeText(requireContext(), "Successfully removed", Toast.LENGTH_SHORT).show()
        }
        builder.setNegativeButton("No"){ _, _ ->}
        builder.setTitle("Delete ${item.courseName}?")
        builder.setMessage("Are you sure want to delete ${item.courseName}?")
        builder.create().show()
    }
    override fun onEditItemClicked(item: Item, position: Int) {
        val intent = Intent(requireContext(), AddEventActivity::class.java)
        val id = item.id
        intent.putExtra("isEdit",true)
        intent.putExtra("courseName",item.courseName)
        intent.putExtra("className",item.className)
        intent.putExtra("startTime",item.startTime)
        intent.putExtra("finishTime",item.finishTime)
        intent.putExtra("editPosition",0)
        intent.putExtra("id",id)
        startActivity(intent)
    }
}