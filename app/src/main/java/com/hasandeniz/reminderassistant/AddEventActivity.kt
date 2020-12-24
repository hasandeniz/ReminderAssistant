package com.hasandeniz.reminderassistant

import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import kotlinx.android.synthetic.main.activity_add_event.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

val detailsListMonday = ArrayList<ExampleItem>()
val detailsListTuesday = ArrayList<ExampleItem>()
val detailsListWednesday = ArrayList<ExampleItem>()
val detailsListThursday = ArrayList<ExampleItem>()
val detailsListFriday = ArrayList<ExampleItem>()
val detailsListSaturday = ArrayList<ExampleItem>()
val detailsListSunday = ArrayList<ExampleItem>()

class AddEventActivity : AppCompatActivity() {
    lateinit var courseName : String
    lateinit var className : String
    lateinit var startTime : String
    lateinit var finishTime : String
    private lateinit var date : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_event)


    }

    fun startTime(view:View){
        val cal = Calendar.getInstance()
        val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
            cal.set(Calendar.HOUR_OF_DAY,hour)
            cal.set(Calendar.MINUTE,minute)
            startTime = SimpleDateFormat("HH:mm").format(cal.time).toString()
            startTimeButton.text = startTime
        }
        TimePickerDialog(this,timeSetListener,cal.get(Calendar.HOUR_OF_DAY),cal.get(Calendar.MINUTE),true).show()
    }
    fun finishTime(view:View){
        val cal = Calendar.getInstance()
        val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
            cal.set(Calendar.HOUR_OF_DAY,hour)
            cal.set(Calendar.MINUTE,minute)
            finishTime = SimpleDateFormat("HH:mm").format(cal.time).toString()
            finishTimeButton.text = finishTime
        }
        TimePickerDialog(this,timeSetListener,cal.get(Calendar.HOUR_OF_DAY),cal.get(Calendar.MINUTE),true).show()
    }

    fun button(view: View){
        val mainIntent = Intent(this,MainActivity::class.java)
        courseName = courseNameInput.text.toString()
        className = classNameInput.text.toString()

        when (intent.getIntExtra("position",-1)) {
            0 -> {
                 date = "Monday"
                val newItem = ExampleItem(courseName,className,startTime,finishTime,date)
                detailsListMonday.add(newItem)

            }
            1 -> {
                date = "Tuesday"
                val newItem = ExampleItem(courseName,className,startTime,finishTime,date)
                detailsListTuesday.add(newItem)
            }
            2 -> {
                date = "Wednesday"
                val newItem = ExampleItem(courseName,className,startTime,finishTime,date)
                detailsListWednesday.add(newItem)
            }
            3 -> {
                date = "Thursday"
                val newItem = ExampleItem(courseName,className,startTime,finishTime,date)
                detailsListThursday.add(newItem)
            }
            4 -> {
                date = "Friday"
                val newItem = ExampleItem(courseName,className,startTime,finishTime,date)
                detailsListFriday.add(newItem)
            }
            5 -> {
                date = "Saturday"
                val newItem = ExampleItem(courseName,className,startTime,finishTime,date)
                detailsListSaturday.add(newItem)
            }
            6 -> {
                date = "Sunday"
                val newItem = ExampleItem(courseName,className,startTime,finishTime,date)
                detailsListSunday.add(newItem)
            }
        }
        // her eklemeden sonra main activity baştan çağırıldığı için current tab hep monday olarak açılıyor
        startActivity(mainIntent)
    }



}