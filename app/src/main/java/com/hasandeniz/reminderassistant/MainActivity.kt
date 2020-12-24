package com.hasandeniz.reminderassistant

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.PopupMenu
import com.github.dhaval2404.colorpicker.MaterialColorPickerDialog
import com.github.dhaval2404.colorpicker.listener.ColorListener
import com.github.dhaval2404.colorpicker.model.ColorSwatch
import com.google.android.material.tabs.TabLayout
import com.hasandeniz.reminderassistant.adapters.FragmentAdapter
import com.hasandeniz.reminderassistant.fragments.*
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    private lateinit var adapter: FragmentAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val actionBar = supportActionBar
        actionBar!!.title = "Reminder"

        adapter = FragmentAdapter(supportFragmentManager)
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
        when (item.itemId) {
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
                val position: Int = tabs.selectedTabPosition
                val intent = Intent(this,AddEventActivity::class.java)
                intent.putExtra("position",position)
                startActivity(intent)


            }
        }
        return super.onOptionsItemSelected(item)
    }






}