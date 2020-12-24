package com.hasandeniz.reminderassistant

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import androidx.recyclerview.widget.LinearLayoutManager
import com.hasandeniz.reminderassistant.adapters.FragmentAdapter
import com.hasandeniz.reminderassistant.fragments.*
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val adapter = FragmentAdapter(supportFragmentManager)
        adapter.addFragment(MondayFragment(),"Monday")
        adapter.addFragment(TuesdayFragment(),"Tuesday")
        adapter.addFragment(WednesdayFragment(),"Wednesday")
        adapter.addFragment(ThursdayFragment(),"Thursday")
        adapter.addFragment(FridayFragment(),"Friday")
        adapter.addFragment(SaturdayFragment(),"Saturday")
        adapter.addFragment(SundayFragment(),"Sunday")
        viewPager.adapter = adapter
        tabs.setupWithViewPager(viewPager)

    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }







}