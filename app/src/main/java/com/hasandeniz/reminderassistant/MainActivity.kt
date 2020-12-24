package com.hasandeniz.reminderassistant

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.hasandeniz.reminderassistant.adapters.FragmentAdapter
import com.hasandeniz.reminderassistant.fragments.*
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val actionBar = supportActionBar
        actionBar!!.title = "Reminder"

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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        when (id) {
            R.id.actionCalendar -> {
                println("action calendar clicked")
            }
            R.id.actionNightMode -> {
                println("action night mode clicked")

            }
            R.id.actionNotifications -> {
                println("action notification clicked")

            }
            R.id.actionAddEvent -> {
                println("action add event clicked")

            }
        }
        return super.onOptionsItemSelected(item)
    }







}