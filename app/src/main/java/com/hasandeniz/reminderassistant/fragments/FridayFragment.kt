package com.hasandeniz.reminderassistant.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.hasandeniz.reminderassistant.ExampleItem
import com.hasandeniz.reminderassistant.R
import com.hasandeniz.reminderassistant.adapters.RecyclerViewAdapter
import com.hasandeniz.reminderassistant.detailsListFriday
import kotlinx.android.synthetic.main.fragment_friday.*
import kotlinx.android.synthetic.main.fragment_monday.*


class FridayFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_friday, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerViewFriday.adapter = RecyclerViewAdapter(detailsListFriday)
        recyclerViewFriday.layoutManager = LinearLayoutManager(activity)
        recyclerViewFriday.setHasFixedSize(true)

    }



}