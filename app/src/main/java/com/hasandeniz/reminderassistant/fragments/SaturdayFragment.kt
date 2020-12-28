package com.hasandeniz.reminderassistant.fragments

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.hasandeniz.reminderassistant.R
import com.hasandeniz.reminderassistant.adapters.RecyclerViewAdapter
import com.hasandeniz.reminderassistant.data.Item
import com.hasandeniz.reminderassistant.data.ItemViewModel
import kotlinx.android.synthetic.main.fragment_saturday.view.*
import kotlinx.coroutines.InternalCoroutinesApi


class SaturdayFragment : Fragment(),RecyclerViewAdapter.ItemListener{
    @InternalCoroutinesApi
    private lateinit var mItemViewModel: ItemViewModel
    @InternalCoroutinesApi
    override fun onCreateView(

            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_saturday, container, false)

        val adapter = RecyclerViewAdapter()
        val recyclerView = view.recyclerViewSaturday
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter.setListener(this)

        mItemViewModel = ViewModelProvider(this).get(ItemViewModel::class.java)
        mItemViewModel.readSaturdayData.observe(viewLifecycleOwner, Observer { item ->
            adapter.setData(item as ArrayList<Item>)
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
}