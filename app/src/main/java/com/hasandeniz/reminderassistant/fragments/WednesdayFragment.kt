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
import com.hasandeniz.reminderassistant.detailsListWednesday
import kotlinx.android.synthetic.main.fragment_saturday.*
import kotlinx.android.synthetic.main.fragment_wednesday.*


class WednesdayFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_wednesday, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerViewWednesday.adapter = RecyclerViewAdapter(detailsListWednesday)
        recyclerViewWednesday.layoutManager = LinearLayoutManager(activity)
        recyclerViewWednesday.setHasFixedSize(true)

    }


}