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
import com.hasandeniz.reminderassistant.detailsListSunday
import kotlinx.android.synthetic.main.fragment_saturday.*
import kotlinx.android.synthetic.main.fragment_sunday.*


class SundayFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sunday, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerViewSunday.adapter = RecyclerViewAdapter(detailsListSunday)
        recyclerViewSunday.layoutManager = LinearLayoutManager(activity)
        recyclerViewSunday.setHasFixedSize(true)

    }



}