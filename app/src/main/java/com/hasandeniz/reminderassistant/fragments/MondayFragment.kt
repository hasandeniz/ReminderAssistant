package com.hasandeniz.reminderassistant.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.hasandeniz.reminderassistant.R
import com.hasandeniz.reminderassistant.adapters.RecyclerViewAdapter
import com.hasandeniz.reminderassistant.detailsListMonday
import kotlinx.android.synthetic.main.fragment_monday.*


class MondayFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_monday, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        recyclerViewMonday.adapter = RecyclerViewAdapter(detailsListMonday)
        recyclerViewMonday.layoutManager = LinearLayoutManager(activity)
        recyclerViewMonday.setHasFixedSize(true)

    }



}