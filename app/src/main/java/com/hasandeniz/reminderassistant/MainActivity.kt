package com.hasandeniz.reminderassistant


import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.google.android.material.tabs.TabLayout
import com.hasandeniz.reminderassistant.adapters.FragmentAdapter
import com.hasandeniz.reminderassistant.fragments.*
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*


class MainActivity : AppCompatActivity() {
    private lateinit var adapter: FragmentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        checkTheme()
        val actionBar = supportActionBar
        actionBar!!.title = "Reminder"
        adapter = FragmentAdapter(supportFragmentManager)
        adapter.addFragment(MondayFragment(), "Monday")
        adapter.addFragment(TuesdayFragment(), "Tuesday")
        adapter.addFragment(WednesdayFragment(), "Wednesday")
        adapter.addFragment(ThursdayFragment(), "Thursday")
        adapter.addFragment(FridayFragment(), "Friday")
        adapter.addFragment(SaturdayFragment(), "Saturday")
        adapter.addFragment(SundayFragment(), "Sunday")
        viewPager.adapter = adapter
        tabs.setupWithViewPager(viewPager)
        val tabLayout = findViewById<View>(R.id.tabs) as TabLayout
        val tab = tabLayout.getTabAt(checkDay())
        tab!!.select()

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
                chooseThemeDialog()
            }
            R.id.actionNotifications -> {
                println("action notification clicked")

            }
            R.id.actionAddEvent -> {
                val position: Int = tabs.selectedTabPosition
                val intent = Intent(this, AddEventActivity::class.java)
                intent.putExtra("position", position)
                startActivity(intent)


            }
        }
        return super.onOptionsItemSelected(item)
    }
    private fun chooseThemeDialog() {

        val builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.choose_theme_text))
        val styles = arrayOf("Light", "Dark", "System default")
        val checkedItem = MyPreferences(this).darkMode

        builder.setSingleChoiceItems(styles, checkedItem) { dialog, which ->

            when (which) {
                0 -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    MyPreferences(this).darkMode = 0
                    delegate.applyDayNight()
                    dialog.dismiss()
                }
                1 -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    MyPreferences(this).darkMode = 1
                    delegate.applyDayNight()
                    dialog.dismiss()
                }
                2 -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                    MyPreferences(this).darkMode = 2
                    delegate.applyDayNight()
                    dialog.dismiss()
                }

            }
        }

        val dialog = builder.create()
        dialog.show()
    }
    private fun checkTheme() {
        when (MyPreferences(this).darkMode) {
            0 -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                delegate.applyDayNight()
            }
            1 -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                delegate.applyDayNight()
            }
            2 -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                delegate.applyDayNight()
            }
        }
    }

    private fun checkDay():Int{
        val calendar: Calendar = Calendar.getInstance()
        return when (calendar.get(Calendar.DAY_OF_WEEK)) {
            Calendar.MONDAY -> 0
            Calendar.TUESDAY -> 1
            Calendar.WEDNESDAY -> 2
            Calendar.THURSDAY -> 3
            Calendar.FRIDAY -> 4
            Calendar.SATURDAY -> 5
            Calendar.SUNDAY -> 6
            else -> 0
        }

    }

}