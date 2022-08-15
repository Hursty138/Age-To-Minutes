package com.example.agetominutes

import android.app.DatePickerDialog
import android.icu.text.SimpleDateFormat
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.DatePicker
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import java.util.*

class MainActivity : AppCompatActivity() {

    private var tvSelectedDate : TextView? = null
    private var tvAgeInMinutes : TextView? = null

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnDatePicker : Button = findViewById(R.id.btnDatePick)

        tvSelectedDate = findViewById(R.id.tvSelectedDate)
        tvAgeInMinutes = findViewById(R.id.tvAgeInMinutes)



        btnDatePicker.setOnClickListener {
            clickDatePicker()
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun clickDatePicker() {

        val myCalendar = Calendar.getInstance()
        val year = myCalendar.get(Calendar.YEAR)
        val month = myCalendar.get(Calendar.MONTH)
        val day = myCalendar.get(Calendar.DAY_OF_MONTH)
        val dpd = DatePickerDialog(this,
           { _, selectedYear, selectedMonth, selectedDayOfMonth ->

               Toast.makeText(this, "The Year was $selectedYear, The month was ${selectedMonth+1}, " +
                       "The day of the month was $selectedDayOfMonth", Toast.LENGTH_SHORT).show()

               val selectedDate = "$selectedDayOfMonth/${selectedMonth+1}/$selectedYear"

               tvSelectedDate?.text = selectedDate

               val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)

               val theDate = sdf.parse(selectedDate)
               theDate?.let {
                   val selectedDateInMinutes = theDate.time / 60000

                   val currentDate = sdf.parse(sdf.format(System.currentTimeMillis()))
                   currentDate?.let {
                       val currentDateInMinutes = currentDate.time/ 60000

                       val differenceInMinutes = currentDateInMinutes - selectedDateInMinutes

                       tvAgeInMinutes?.text = differenceInMinutes.toString()
                   }

               }




           },
            year,
            month,
            day
            )

        dpd.datePicker.maxDate = System.currentTimeMillis() - 86400000
        dpd.show()



    }
}