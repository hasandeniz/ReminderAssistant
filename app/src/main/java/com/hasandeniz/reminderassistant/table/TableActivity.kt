package com.hasandeniz.reminderassistant.table


import android.os.Bundle
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.ViewModelProvider
import com.alamkanak.weekview.WeekViewEvent
import com.hasandeniz.reminderassistant.R
import com.hasandeniz.reminderassistant.data.ItemViewModel
import kotlinx.coroutines.InternalCoroutinesApi
import java.util.*
import kotlin.collections.ArrayList


open class TableActivity : BaseActivity() {
    var uniqueId: Long = 0
    @InternalCoroutinesApi
    private lateinit var mItemViewModel: ItemViewModel
    private fun getUniqueId(): String = uniqueId++.toString()


    @InternalCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        weekView.typeface = ResourcesCompat.getFont(this, R.font.lato)
        mItemViewModel = ViewModelProvider(this).get(ItemViewModel::class.java)
    }

    @InternalCoroutinesApi
    override fun onMonthChange(newYear: Int, newMonth: Int): MutableList<out WeekViewEvent>? {
        val events = ArrayList<WeekViewEvent>()
        val repeatevents: ArrayList<WeekViewEvent> = ArrayList()

        mItemViewModel.readAllData.observe(this, { item ->
            if (item.isNotEmpty()) {
                for (data in item) {

                    val hour = data.startTime.take(2).toInt()
                    val minute = data.startTime.takeLast(2).toInt()
                    val hour1 = data.finishTime.take(2).toInt()
                    val minute1 = data.finishTime.takeLast(2).toInt()

                    for (i in -1000..1000 step 7) {
                        val startTime = Calendar.getInstance()
                        val date = data.day
                        val day = checkAlarmDate(date)
                        startTime.set(Calendar.DAY_OF_WEEK, day-1)
                        startTime.set(Calendar.HOUR_OF_DAY, hour)
                        startTime.set(Calendar.MINUTE, minute)
                        startTime.add(Calendar.DATE, i)
                        startTime.add(Calendar.SECOND,1)
                        val finishTime = startTime.clone() as Calendar

                        finishTime.set(Calendar.HOUR_OF_DAY, hour1)
                        finishTime.set(Calendar.MINUTE, minute1)

                        val rnds = (1..5).random()

                        val event = WeekViewEvent(getUniqueId(), getEventTitle(startTime, finishTime), startTime, finishTime)
                        when (rnds) {
                            1 -> {
                                event.color = ResourcesCompat.getColor(resources,
                                    R.color.event_color_01, null)
                            }
                            2 -> {
                                event.color = ResourcesCompat.getColor(resources,
                                    R.color.event_color_02, null)
                            }
                            3 -> {
                                event.color = ResourcesCompat.getColor(resources,
                                    R.color.event_color_03, null)
                            }
                            else -> event.color = ResourcesCompat.getColor(resources,
                                R.color.event_color_04, null)
                        }

                        event.name = data.courseName
                        repeatevents.add(event)
                        weekView.notifyDataSetChanged()

                    }


                }
            }
        })
        for (event in repeatevents) {
            if (event.startTime.get(Calendar.MONTH)== newMonth-1 && event.startTime[Calendar.YEAR] == newYear) {
                events.add(event)

            }
        }
        return events
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
}