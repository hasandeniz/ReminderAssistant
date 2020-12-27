package com.hasandeniz.reminderassistant.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.hasandeniz.reminderassistant.R
import com.hasandeniz.reminderassistant.adapters.RecyclerViewAdapter
import com.hasandeniz.reminderassistant.data.Item
import com.hasandeniz.reminderassistant.data.ItemViewModel
import kotlinx.android.synthetic.main.fragment_wednesday.view.*
import kotlinx.coroutines.InternalCoroutinesApi


class WednesdayFragment : Fragment() {
    @InternalCoroutinesApi
    private lateinit var mItemViewModel: ItemViewModel
    @InternalCoroutinesApi
    override fun onCreateView(

            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_wednesday, container, false)

        val adapter = RecyclerViewAdapter()
        val recyclerView = view.recyclerViewWednesday
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        mItemViewModel = ViewModelProvider(this).get(ItemViewModel::class.java)
        mItemViewModel.readWednesdayData.observe(viewLifecycleOwner, Observer { item ->
            adapter.setData(item as ArrayList<Item>)
        })
        return view
    }
}