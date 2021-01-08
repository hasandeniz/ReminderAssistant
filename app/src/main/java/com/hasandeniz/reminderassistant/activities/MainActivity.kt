package com.hasandeniz.reminderassistant.activities


import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.PowerManager
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.text.HtmlCompat
import androidx.lifecycle.ViewModelProvider
import com.hasandeniz.reminderassistant.R
import com.hasandeniz.reminderassistant.adapters.FragmentAdapter
import com.hasandeniz.reminderassistant.data.ItemViewModel
import com.hasandeniz.reminderassistant.data.MyPreferences
import com.hasandeniz.reminderassistant.databinding.ActivityMainBinding
import com.hasandeniz.reminderassistant.fragments.*
import com.hasandeniz.reminderassistant.notify.AlarmReceiver
import com.hasandeniz.reminderassistant.table.WholeViewSnappingActivity
import kotlinx.coroutines.InternalCoroutinesApi
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseSequence
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseView
import uk.co.deanwild.materialshowcaseview.ShowcaseConfig
import java.util.*


class MainActivity : AppCompatActivity() {
    private lateinit var adapter: FragmentAdapter
    @InternalCoroutinesApi
    private lateinit var mItemViewModel: ItemViewModel
    private lateinit var binding: ActivityMainBinding

    @InternalCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        //change action bar title and its color
        setActionBar()
        //creates fragments for tablayout
        createFragments()
        //selects current tab according to data
        selectTab()
        //checks theme from preferences
        checkTheme()
        //asks permission from user
        askPermission()

        mItemViewModel = ViewModelProvider(this).get(ItemViewModel::class.java)
        setAlarms(mItemViewModel)


    }

    private fun askPermission(){
        val pIntent = Intent()
        val packageName: String = this.packageName
        val pm:PowerManager = this.getSystemService(Context.POWER_SERVICE) as PowerManager
        if (!pm.isIgnoringBatteryOptimizations(packageName)) {
            pIntent.action = Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS
            pIntent.data = Uri.parse("package:$packageName")
            startActivity(pIntent)
        }
    }
    private fun setActionBar(){
        val actionBar = supportActionBar
        val html = "<font color=\"@color/customAppNameColor\">" + getString(R.string.app_name) + "</font>"
        actionBar!!.title = HtmlCompat.fromHtml(html, HtmlCompat.FROM_HTML_MODE_LEGACY)
    }
    private fun selectTab(){
        if(globalPosition != null){
            val tab = binding.tabs.getTabAt(globalPosition!!.toInt())
            tab!!.select()
        }else{
            val tab = binding.tabs.getTabAt(checkDay())
            tab!!.select()
        }
    }
    @InternalCoroutinesApi
    private fun setAlarms(mItemViewModel: ItemViewModel){
        mItemViewModel.readAllData.observe(this, { item ->
            if (item.isNotEmpty()) {
                for (data in item) {
                    val calendar = Calendar.getInstance()
                    val date = data.day
                    val hour = data.startTime.take(2).toInt()
                    val minute = data.startTime.takeLast(2).toInt()
                    val day = checkAlarmDate(date)
                    calendar.set(Calendar.DAY_OF_WEEK, day)
                    calendar.set(Calendar.HOUR_OF_DAY, hour)
                    calendar.set(Calendar.MINUTE, minute)
                    calendar.set(Calendar.SECOND, 0)
                    if (calendar.before(Calendar.getInstance())) {
                        calendar.add(Calendar.DATE, 7)
                    }
                    startAlarm(calendar, data.id)
                }
            }

        })

    }
    private fun startAlarm(calendar: Calendar, id: Int) {
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val alarmIntent = Intent(applicationContext, AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(this, id, alarmIntent, 0)
        if(calendar.before(Calendar.getInstance())){
            calendar.add(Calendar.DATE, 7)
        }
        alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                pendingIntent
        )
    }
    private fun Int.toDp():Int = (this / Resources.getSystem().displayMetrics.density).toInt()
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        Handler(Looper.getMainLooper()).post {
            val view: View = findViewById<View>(R.id.actionAddEvent) as View
            val view1: View = findViewById<View>(R.id.actionNightMode) as View
            val view2: View = findViewById<View>(R.id.actionTable) as View
            val view3: View = findViewById<View>(R.id.viewPager) as View
            val text = HtmlCompat.fromHtml(resources.getString(R.string.got_it),HtmlCompat.FROM_HTML_MODE_LEGACY)
            val config = ShowcaseConfig()
            config.delay = 250
            val sequence = MaterialShowcaseSequence(this, 1.toString())
            sequence.setConfig(config)
            sequence.addSequenceItem(view,
                    getString(R.string.showcase_add), getString(R.string.got_it))
            sequence.addSequenceItem(view1,
                    getString(R.string.showcase_theme), getString(R.string.got_it))
            sequence.addSequenceItem(view2,
                    getString(R.string.showcase_table), getString(R.string.got_it))
            sequence.addSequenceItem(
                    MaterialShowcaseView.Builder(this)
                            .setTarget(view3)
                            .setDismissText(text)
                            .setContentText(getString(R.string.showcase_page))
                            .setShapePadding((-700).toDp())
                            .build()
            )
            sequence.start()
        }

        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.actionAbout -> {
                val intent = Intent(this, AboutActivity::class.java)
                startActivity(intent)
            }
            R.id.actionTable -> {
                val intent = Intent(this, WholeViewSnappingActivity::class.java)
                startActivity(intent)
            }
            R.id.actionNightMode -> {
                chooseThemeDialog()
            }
            R.id.actionAddEvent -> {
                val position: Int = binding.tabs.selectedTabPosition
                val intent = Intent(this, AddEventActivity::class.java)
                intent.putExtra("tabPosition", position)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }
    private fun chooseThemeDialog() {

        val builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.choose_theme_text))
        val styles = arrayOf(
                getString(R.string.light),
                getString(R.string.dark),
                getString(R.string.systemDefault)
        )
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
    private fun checkAlarmDate(date: String):Int{
        return when (date){
            "Monday" -> Calendar.MONDAY
            "Tuesday" -> Calendar.TUESDAY
            "Wednesday" -> Calendar.WEDNESDAY
            "Thursday" -> Calendar.THURSDAY
            "Friday" -> Calendar.FRIDAY
            "Saturday" -> Calendar.SATURDAY
            "Sunday" -> Calendar.SUNDAY
            else -> -1
        }
    }
    private fun createFragments(){
        adapter = FragmentAdapter(supportFragmentManager)
        adapter.addFragment(MondayFragment(), getString(R.string.monday))
        adapter.addFragment(TuesdayFragment(), getString(R.string.tuesday))
        adapter.addFragment(WednesdayFragment(), getString(R.string.wednesday))
        adapter.addFragment(ThursdayFragment(), getString(R.string.thursday))
        adapter.addFragment(FridayFragment(), getString(R.string.friday))
        adapter.addFragment(SaturdayFragment(), getString(R.string.saturday))
        adapter.addFragment(SundayFragment(), getString(R.string.sunday))
        binding.viewPager.adapter = adapter
        binding.tabs.setupWithViewPager(binding.viewPager)
    }

}