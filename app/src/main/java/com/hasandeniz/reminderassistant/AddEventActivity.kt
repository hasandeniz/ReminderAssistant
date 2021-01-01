package com.hasandeniz.reminderassistant

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.AlarmManager.INTERVAL_DAY
import android.app.AlarmManager.RTC_WAKEUP
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.hasandeniz.reminderassistant.data.Item
import com.hasandeniz.reminderassistant.data.ItemViewModel
import kotlinx.android.synthetic.main.activity_add_event.*
import kotlinx.coroutines.InternalCoroutinesApi
import java.text.SimpleDateFormat
import java.util.*


var globalPosition:String? = null


class AddEventActivity : AppCompatActivity() {

    lateinit var courseName : String
    lateinit var className : String
    lateinit var startTime : String
    lateinit var finishTime : String
    private lateinit var date : String
    @InternalCoroutinesApi
    private lateinit var mItemViewModel: ItemViewModel
    private lateinit var  item: Item


    @InternalCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val actionBar = supportActionBar
        actionBar!!.hide()
        setContentView(R.layout.activity_add_event)

        if (intent.getBooleanExtra("isEdit",false)){
            courseNameInput.setText(intent.getStringExtra("courseName"))
            classNameInput.setText(intent.getStringExtra("className"))
            startTime = intent.getStringExtra("startTime").toString()
            finishTime = intent.getStringExtra("finishTime").toString()
            startTimeButton.text = "Start Time: " + startTime
            finishTimeButton.text = "Finish Time: " + finishTime
        }else{
            courseName = ""
            className = ""
            startTime = ""
            finishTime = ""
        }

        check()
        mItemViewModel = ViewModelProvider(this).get(ItemViewModel::class.java)

    }


    private fun check(){
        saveButton.isEnabled = !(startTime == "" || finishTime == "")
    }

    @SuppressLint("SimpleDateFormat")
    fun startTime(view: View){
        val cal = Calendar.getInstance()
        val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
            cal.set(Calendar.HOUR_OF_DAY,hour)
            cal.set(Calendar.MINUTE,minute)
            startTime = SimpleDateFormat("HH:mm").format(cal.time).toString()
            startTimeButton.text = "Start Time: " +startTime
            check()
        }
        TimePickerDialog(this,timeSetListener,cal.get(Calendar.HOUR_OF_DAY),cal.get(Calendar.MINUTE),true).show()
    }
    @SuppressLint("SimpleDateFormat")
    fun finishTime(view: View){
        val cal = Calendar.getInstance()
        val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
            cal.set(Calendar.HOUR_OF_DAY,hour)
            cal.set(Calendar.MINUTE,minute)
            finishTime = SimpleDateFormat("HH:mm").format(cal.time).toString()
            finishTimeButton.text = "Finish Time: " + finishTime
            check()
        }
        TimePickerDialog(this,timeSetListener,cal.get(Calendar.HOUR_OF_DAY),cal.get(Calendar.MINUTE),true).show()

    }




    @InternalCoroutinesApi
    fun saveButton(view: View){
        courseName = courseNameInput.text.toString()
        className = classNameInput.text.toString()
        val mainIntent = Intent(this,MainActivity::class.java)
        if (courseName == "" || className == ""){
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Warning!")
            builder.setMessage("All areas must be filled!")
            builder.setNeutralButton("OK"){ dialogInterface: DialogInterface, i: Int -> }
            builder.show()
        }else{
            if (intent.getIntExtra("editPosition",-1) != -1){
                when (intent.getIntExtra("editPosition",-1)) {
                    0 -> {
                        date = "Monday"
                        globalPosition = 0.toString()
                        updateItem(date)
                    }
                    1 -> {
                        date = "Tuesday"
                        globalPosition = 1.toString()
                        updateItem(date)
                    }
                    2 -> {
                        date = "Wednesday"
                        globalPosition = 2.toString()
                        updateItem(date)
                    }
                    3 -> {
                        date = "Thursday"
                        globalPosition = 3.toString()
                        updateItem(date)
                    }
                    4 -> {
                        date = "Friday"
                        globalPosition = 4.toString()
                        updateItem(date)
                    }
                    5 -> {
                        date = "Saturday"
                        globalPosition = 5.toString()
                        updateItem(date)
                    }
                    6 -> {
                        date = "Sunday"
                        globalPosition = 6.toString()
                        updateItem(date)
                    }

                }
            }else{

                when (intent.getIntExtra("tabPosition",-1)) {
                    0 -> {
                        date = "Monday"
                        globalPosition = 0.toString()
                    }
                    1 -> {
                        date = "Tuesday"
                        globalPosition = 1.toString()
                    }
                    2 -> {
                        date = "Wednesday"
                        globalPosition = 2.toString()
                    }
                    3 -> {
                        date = "Thursday"
                        globalPosition = 3.toString()
                    }
                    4 -> {
                        date = "Friday"
                        globalPosition = 4.toString()
                    }
                    5 -> {
                        date = "Saturday"
                        globalPosition = 5.toString()
                    }
                    6 -> {
                        date = "Sunday"
                        globalPosition = 6.toString()
                    }
                }
                item = Item(0,courseName,className,startTime,finishTime,date)
                mItemViewModel.addItem(item)
            }
            startActivity(mainIntent)
        }
    }
    fun cancelButton(view: View) {
        finish()
    }

    @InternalCoroutinesApi
    fun updateItem(date: String){
        val courseName = courseName
        val className = className
        val startTime = startTime
        val finishTime = finishTime
        val id = intent.getIntExtra("id",-1)
        val newItem = Item(id,courseName,className,startTime,finishTime,date)
        mItemViewModel.updateItem(newItem)

    }
}

