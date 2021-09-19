package kr.co.lee.part10_27

import java.text.DateFormat
import java.text.SimpleDateFormat

class AppUtils {
    companion object {
        fun getDate(dateString: String): String {
            return try {
                val format1 = SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss'Z'")
                val date = format1.parse(dateString)
                val sdf = SimpleDateFormat("MMM d, yyyy")
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
                val netDate = date
                sdf.format(netDate)
            } catch(ex: Exception) {
                ex.printStackTrace()
                "xx"
            }
        }
    }
}