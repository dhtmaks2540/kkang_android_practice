package kr.co.lee.part10_29

import java.text.SimpleDateFormat

class AppUtils {
    companion object {
        fun getDate(dateString: String): String {
            return try {
                val format1 = SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss'Z'")
                val date = format1.parse(dateString)
                val sdf = SimpleDateFormat("MMM d, yyyyy")
                sdf.format(date)
            } catch(ex: Exception) {
                ex.printStackTrace()
                "xx"
            }
        }

        fun getTime(dateString: String): String {
            return try {
                val format1 = SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss'Z'")
                val date = format1.parse(dateString)
                val sdf = SimpleDateFormat("h:mm a")
                sdf.format(date)
            } catch(ex: Exception) {
                ex.printStackTrace()
                "xx"
            }
        }
    }
}