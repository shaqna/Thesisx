package com.ngedev.thesisx.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

object DateConverter {
    fun convertMillisToString(timeMillis: Long): String {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = timeMillis
        val dateFormat = SimpleDateFormat("d MMM yyyy", Locale.getDefault())
        return dateFormat.format(calendar.time)
    }

    @SuppressLint("SimpleDateFormat")
    fun convertStringToMillis(stringDate: String): Long {
        val dateFormat = SimpleDateFormat("d MMM yyyy")
        val date = dateFormat.parse(stringDate) as Date
        return date.time
    }

}