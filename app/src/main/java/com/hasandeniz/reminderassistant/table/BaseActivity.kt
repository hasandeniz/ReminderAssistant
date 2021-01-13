package com.hasandeniz.reminderassistant.table

import android.graphics.RectF
import android.os.Bundle
import android.util.TypedValue
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.alamkanak.weekview.*
import com.alamkanak.weekview.WeekView.*
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import com.hasandeniz.reminderassistant.R
import com.hasandeniz.reminderassistant.databinding.ActivityBaseBinding

abstract class BaseActivity : AppCompatActivity(), EventClickListener, MonthLoader.MonthChangeListener {
    private lateinit var binding: ActivityBaseBinding
    private var mWeekViewType = TYPE_THREE_DAY_VIEW
    private lateinit var shortDateFormat: DateFormat
    private lateinit var timeFormat: DateFormat
    lateinit var weekView: WeekView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        shortDateFormat = WeekViewUtil.getWeekdayWithNumericDayAndMonthFormat(this, true)
        timeFormat = android.text.format.DateFormat.getTimeFormat(this) ?: SimpleDateFormat("HH:mm", Locale.getDefault())
        binding = ActivityBaseBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        weekView = binding.weekView
        weekView.eventClickListener = this
        weekView.monthChangeListener = this

        setupDateTimeInterpreter(false)
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_today -> {
                weekView.goToToday()
                return true
            }
            R.id.action_day_view -> {
                if (!item.isChecked) {
                    item.isChecked = true
                    setDayViewType(TYPE_DAY_VIEW)
                }
                return true
            }
            R.id.action_three_day_view -> {
                if (!item.isChecked) {
                    item.isChecked = true
                    setDayViewType(TYPE_THREE_DAY_VIEW)
                }
                return true
            }
            R.id.action_week_view -> {
                if (!item.isChecked) {
                    item.isChecked = true
                    setDayViewType(TYPE_WEEK_VIEW)
                }
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun setDayViewType(dayViewType: Int) {
        setupDateTimeInterpreter(dayViewType == TYPE_WEEK_VIEW)

        when (dayViewType) {
            TYPE_DAY_VIEW -> {

                mWeekViewType = TYPE_DAY_VIEW
                weekView.numberOfVisibleDays = 1

                weekView.columnGap = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8f, resources.displayMetrics).toInt()
                weekView.textSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12f, resources.displayMetrics)
                weekView.eventTextSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12f, resources.displayMetrics)
            }
            TYPE_THREE_DAY_VIEW -> {

                mWeekViewType = TYPE_THREE_DAY_VIEW
                weekView.numberOfVisibleDays = 3

                weekView.columnGap = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8f, resources.displayMetrics).toInt()
                weekView.textSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12f, resources.displayMetrics)
                weekView.eventTextSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12f, resources.displayMetrics)
            }
            TYPE_WEEK_VIEW -> {

                mWeekViewType = TYPE_WEEK_VIEW
                weekView.numberOfVisibleDays = 7

                weekView.columnGap = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2f, resources.displayMetrics).toInt()
                weekView.textSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 10f, resources.displayMetrics)
                weekView.eventTextSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 10f, resources.displayMetrics)
            }
        }
    }


    protected open fun setupDateTimeInterpreter(shortDate: Boolean) {
        val calendar = Calendar.getInstance().apply {
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }
        val normalDateFormat = WeekViewUtil.getWeekdayWithNumericDayAndMonthFormat(this@BaseActivity, false)
        weekView.dateTimeInterpreter = object : DateTimeInterpreter {
            override fun getFormattedTimeOfDay(hour: Int, minutes: Int): String {
                calendar.set(Calendar.HOUR_OF_DAY, hour)
                calendar.set(Calendar.MINUTE, minutes)
                return timeFormat.format(calendar.time)
            }

            override fun getFormattedWeekDayTitle(date: Calendar): String {
                return if (shortDate) shortDateFormat.format(date.time) else normalDateFormat.format(date.time)
            }
        }
    }

    protected fun getEventTitle(startCal: Calendar, endCal: Calendar? = null, allDay: Boolean = false): String {
        val startDate = startCal.time
        val endDate = endCal?.time
        return when {
            allDay -> {
                if (endCal == null || WeekViewUtil.isSameDay(startCal, endCal))
                    shortDateFormat.format(startDate)
                else "${shortDateFormat.format(startDate)}..${shortDateFormat.format(endDate)}"
            }
            endCal == null -> "${shortDateFormat.format(startDate)} ${timeFormat.format(startDate)}"
            WeekViewUtil.isSameDay(startCal, endCal) -> "${shortDateFormat.format(startDate)} ${timeFormat.format(startDate)}..${timeFormat.format(endDate)}"
            else -> "${shortDateFormat.format(startDate)} ${timeFormat.format(startDate)}..${shortDateFormat.format(endDate)} ${timeFormat.format(endDate)}"
        }
    }

    override fun onEventClick(event: WeekViewEvent, eventRect: RectF) {
    }


    override fun onMonthChange(newYear: Int, newMonth: Int): MutableList<out WeekViewEvent>? {
        return null
    }



    companion object {
        const val TYPE_DAY_VIEW = 1
        const val TYPE_THREE_DAY_VIEW = 2
        const val TYPE_WEEK_VIEW = 3
    }
}