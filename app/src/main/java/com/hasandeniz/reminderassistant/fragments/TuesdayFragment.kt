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
import com.hasandeniz.reminderassistant.detailsListTuesday
import kotlinx.android.synthetic.main.fragment_saturday.*
import kotlinx.android.synthetic.main.fragment_tuesday.*


class TuesdayFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_tuesday, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        recyclerViewTuesday.adapter = RecyclerViewAdapter(detailsListTuesday)
        recyclerViewTuesday.layoutManager = LinearLayoutManager(activity)
        recyclerViewTuesday.setHasFixedSize(true)

    }



}