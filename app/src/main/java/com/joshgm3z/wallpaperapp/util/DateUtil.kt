package com.joshgm3z.wallpaperapp.util

import java.util.*

class DateUtil {

    companion object {
        fun getTextDate(dateInstance: Long): String {
            val instance = Calendar.getInstance()
            instance.time = Date(dateInstance)
            val month: String = getMonth(instance)
            val day = instance.get(Calendar.DAY_OF_MONTH)
            val year = instance.get(Calendar.YEAR)
            return "$month $day, $year"
        }

        private fun getMonth(instance: Calendar): String {
            when (instance.get(Calendar.MONTH)) {
                1 -> return "jan"
                2 -> return "feb"
                3 -> return "mar"
                4 -> return "apr"
                5 -> return "may"
                6 -> return "jun"
                7 -> return "jul"
                8 -> return "aug"
                9 -> return "sep"
                10 -> return "oct"
                11 -> return "nov"
                12 -> return "dec"
            }
            return ""
        }
    }

}