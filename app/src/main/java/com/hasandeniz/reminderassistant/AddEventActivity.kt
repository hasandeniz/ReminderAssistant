package com.hasandeniz.reminderassistant

import android.annotation.SuppressLint
import android.app.TimePickerDialog
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



class AddEventActivity : AppCompatActivity() {
    lateinit var courseName : String
    lateinit var className : String
    lateinit var startTime : String
    lateinit var finishTime : String
    private lateinit var date : String
    @InternalCoroutinesApi
    private lateinit var mItemViewModel: ItemViewModel

    @InternalCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val actionBar = supportActionBar
        actionBar!!.hide()
        setContentView(R.layout.activity_add_event)
        courseName = ""
        className = ""
        startTime = ""
        finishTime = ""
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
            when (intent.getIntExtra("position",-1)) {
                0 -> {
                    date = "Monday"
                    val item = Item(0,courseName,className,startTime,finishTime,date)
                    mItemViewModel.addItem(item)
                    Toast.makeText(this,"Added", Toast.LENGTH_LONG).show()
                }
                1 -> {
                    date = "Tuesday"
                    val item = Item(0,courseName,className,startTime,finishTime,date)
                    mItemViewModel.addItem(item)
                    Toast.makeText(this,"Added", Toast.LENGTH_LONG).show()
                }
                2 -> {
                    date = "Wednesday"
                    val item = Item(0,courseName,className,startTime,finishTime,date)
                    mItemViewModel.addItem(item)
                    Toast.makeText(this,"Added", Toast.LENGTH_LONG).show()
                }
                3 -> {
                    date = "Thursday"
                    val item = Item(0,courseName,className,startTime,finishTime,date)
                    mItemViewModel.addItem(item)
                    Toast.makeText(this,"Added", Toast.LENGTH_LONG).show()
                }
                4 -> {
                    date = "Friday"
                    val item = Item(0,courseName,className,startTime,finishTime,date)
                    mItemViewModel.addItem(item)
                    Toast.makeText(this,"Added", Toast.LENGTH_LONG).show()
                }
                5 -> {
                    date = "Saturday"
                    val item = Item(0,courseName,className,startTime,finishTime,date)
                    mItemViewModel.addItem(item)
                    Toast.makeText(this,"Added", Toast.LENGTH_LONG).show()
                }
                6 -> {
                    date = "Sunday"
                    val item = Item(0,courseName,className,startTime,finishTime,date)
                    mItemViewModel.addItem(item)
                    Toast.makeText(this,"Added", Toast.LENGTH_LONG).show()
                }
            }
            startActivity(mainIntent)
        }

    }
    fun cancelButton(view: View) {
        finish()
    }

}