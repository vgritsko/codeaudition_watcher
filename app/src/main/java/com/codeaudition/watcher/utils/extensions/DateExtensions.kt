package com.codeaudition.watcher.utils.extensions


import java.text.SimpleDateFormat
import java.util.*

fun Date.currentDate(dateFormat: SimpleDateFormat): String =
    dateFormat.format(Date(System.currentTimeMillis()))

object DateExtensions{
    val appDateFormat:SimpleDateFormat  get()  = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
}