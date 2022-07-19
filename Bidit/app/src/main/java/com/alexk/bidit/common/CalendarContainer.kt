package com.alexk.bidit.common

import android.view.View
import android.widget.CalendarView
import android.widget.TextView
import com.alexk.bidit.R
import com.kizitonwose.calendarview.model.CalendarDay
import com.kizitonwose.calendarview.model.DayOwner
import com.kizitonwose.calendarview.ui.ViewContainer
import java.time.LocalDate

class CalendarContainer(view: View) : ViewContainer(view) {
    val textView: TextView = view.findViewById<TextView>(R.id.tv_date)
    lateinit var day: CalendarDay
    private var selectedDate : LocalDate? = null

    init{
        view.setOnClickListener {

        }
    }
}