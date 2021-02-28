package com.hasandeniz.reminderassistant.activities

import android.app.Dialog
import android.app.TimePickerDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.google.android.ads.nativetemplates.TemplateView
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.hasandeniz.reminderassistant.R
import com.hasandeniz.reminderassistant.data.Item
import com.hasandeniz.reminderassistant.data.ItemViewModel
import com.hasandeniz.reminderassistant.databinding.ActivityAddEventBinding
import kotlinx.coroutines.InternalCoroutinesApi
import java.text.SimpleDateFormat
import java.util.*


var globalPosition:String? = null


class AddEventActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddEventBinding
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
        binding = ActivityAddEventBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        val actionBar = supportActionBar
        actionBar!!.hide()

        MobileAds.initialize(this) {}

        val adLoader = AdLoader.Builder(this, "ca-app-pub-3409964174267492/1675561055")
            .forUnifiedNativeAd { unifiedNativeAd ->
                val template = findViewById<TemplateView>(R.id.nativeTemplateView)
                template.setNativeAd(unifiedNativeAd)
            }
            .build()
        adLoader.loadAd(AdRequest.Builder().build())

        //my app id
        //ca-app-pub-3409964174267492~6721656563

        //my native ad id
        //ca-app-pub-3409964174267492/1675561055


        if (intent.getBooleanExtra("isEdit", false)){
            binding.courseNameInput.setText(intent.getStringExtra("courseName"))
            binding.classNameInput.setText(intent.getStringExtra("className"))
            startTime = intent.getStringExtra("startTime").toString()
            finishTime = intent.getStringExtra("finishTime").toString()
            val startText = getString(R.string.start_time) + " " + startTime
            val finishText = getString(R.string.finish_time) + " " + finishTime
            binding.startTimeButton.text = startText
            binding.finishTimeButton.text =  finishText
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
        binding.saveButton.isEnabled = !(startTime == "" || finishTime == "")
    }

    fun startTime(view: View) {
        val cal = Calendar.getInstance()
        val timeSetListener = TimePickerDialog.OnTimeSetListener { _, hour, minute ->
            cal.set(Calendar.HOUR_OF_DAY, hour)
            cal.set(Calendar.MINUTE, minute)
            startTime = SimpleDateFormat("HH:mm", Locale.getDefault()).format(cal.time).toString()
            val startText = getString(R.string.start_time) + " " + startTime
            binding.startTimeButton.text = startText
            check()
        }
        TimePickerDialog(
            this,
            timeSetListener,
            cal.get(Calendar.HOUR_OF_DAY),
            cal.get(Calendar.MINUTE),
            true
        ).show()
    }
    fun finishTime(view: View) {
        val cal = Calendar.getInstance()
        val timeSetListener = TimePickerDialog.OnTimeSetListener { _, hour, minute ->
            cal.set(Calendar.HOUR_OF_DAY, hour)
            cal.set(Calendar.MINUTE, minute)
            finishTime = SimpleDateFormat("HH:mm", Locale.getDefault()).format(cal.time).toString()
            val finishText = getString(R.string.finish_time) + " " +finishTime
            binding.finishTimeButton.text = finishText
            check()
        }
        TimePickerDialog(
            this,
            timeSetListener,
            cal.get(Calendar.HOUR_OF_DAY),
            cal.get(Calendar.MINUTE),
            true
        ).show()

    }




    @InternalCoroutinesApi
    fun saveButton(view: View) {
        courseName = binding.courseNameInput.text.toString()
        className = binding.classNameInput.text.toString()
        val mainIntent = Intent(this, MainActivity::class.java)
        if (courseName == "" || className == ""){
            val builder = AlertDialog.Builder(this)
            builder.setTitle(getString(R.string.warning))
            builder.setMessage(getString(R.string.must_be_filled))
            builder.setNeutralButton(getString(R.string.ok)){ _: DialogInterface, _: Int -> }
            builder.create().apply {
                setOnShowListener {
                    getButton(Dialog.BUTTON_NEUTRAL)?.setTextColor(ContextCompat.getColor(context,R.color.colorAlertText))
                }
            }.show()
        }else{
            if (intent.getIntExtra("editPosition", -1) != -1){
                when (intent.getIntExtra("editPosition", -1)) {
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
                updateItem()
            }else{
                when (intent.getIntExtra("tabPosition", -1)) {
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
                item = Item(0, courseName, className, startTime, finishTime, date)
                mItemViewModel.addItem(item)
            }
            startActivity(mainIntent)
        }
    }
    fun cancelButton(view: View) {
        finish()
    }

    @InternalCoroutinesApi
    fun updateItem(){
        val courseName = courseName
        val className = className
        val startTime = startTime
        val finishTime = finishTime
        val id = intent.getIntExtra("id", -1)
        val date = intent.getStringExtra("date")

        val newItem = Item(id, courseName, className, startTime, finishTime, date!!)

        mItemViewModel.updateItem(newItem)

    }
}

