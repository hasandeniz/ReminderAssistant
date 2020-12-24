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
import kotlinx.android.synthetic.main.fragment_saturday.*
import kotlinx.android.synthetic.main.fragment_sunday.*


class SundayFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sunday, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val exampleItem = denemeItem()
        val exampleItem2 = denemeItem()

        val exampleItemList = ArrayList<ExampleItem>()
        exampleItemList.add(exampleItem)
        exampleItemList.add(exampleItem2)
        recyclerViewSunday.adapter = RecyclerViewAdapter(exampleItemList)
        recyclerViewSunday.layoutManager = LinearLayoutManager(activity)
        recyclerViewSunday.setHasFixedSize(true)

    }

    private fun denemeItem(): ExampleItem {
        val text2 = "Deneme 2"
        val text1 = "Deneme 1"
        val imageText1 = "12:00"
        val imageText2 = "13:00"
        return ExampleItem(text1, text2, imageText1, imageText2)
    }

}