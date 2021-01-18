package com.example.kerkar

import android.app.Application
import android.content.Context
import android.util.Log
import android.view.View
import java.security.MessageDigest

private val TAG = "otherfun"

class assignment_swith(){
    var flag = 0
}

class MyApp : Application(){
    var QRResult: Array<Any>? = null

    companion object {
        private var instance : MyApp? = null

        fun  getInstance(): MyApp {
            if (instance == null)
                instance = MyApp()

            return instance!!
        }
    }
}


fun week_to_day_jp_chenger(week: String): String{
    val week_to_day_jp_list = listOf("日", "月", "火", "水", "木", "金", "土")
    val week_to_day_symbol_list = listOf("sun", "mon", "tue", "wen", "thu", "fri", "sat")
    val index: Int = week_to_day_symbol_list.indexOf(week)
    return week_to_day_jp_list[index]
}

fun week_to_day_symbol_chenger(week: String): String{
    val week_to_day_jp_list = listOf("日", "月", "火", "水", "木", "金", "土")
    val week_to_day_symbol_list = listOf("sun", "mon", "tue", "wen", "thu", "fri", "sat")
    val index: Int = week_to_day_jp_list.indexOf(week)
    return week_to_day_symbol_list[index]
}

fun str_num_normalization(str: String): String{
    val norm_str = str.replace("０", "0").replace("１", "1")
            .replace("２", "2").replace("３", "3")
            .replace("４", "4").replace("５", "5")
            .replace("６", "6").replace("７", "7")
            .replace("８", "8").replace("９", "9")
            .replace("　", " ")
            .replace("Ⅰ", "I")
            .replace("Ⅱ", "II").replace("Ⅲ", "III")
            .replace("Ⅳ", "IV").replace("Ⅴ", "V")
            .replace("Ⅵ", "VI").replace("Ⅶ", "VII")
            .replace("Ⅷ", "VIII").replace("Ⅸ", "IX")
            .replace("ⅰ", "I")
            .replace("ⅱ", "II").replace("ⅲ", "III")
            .replace("ⅳ", "IV").replace("ⅴ", "V")
            .replace("ⅵ", "VI").replace("ⅶ", "VII")
            .replace("ⅷ", "VIII").replace("ⅸ", "IX")
            .replace("（", "(").replace("）", ")")
            .replace("、", ",")

    return norm_str
}

fun str_to_array(str: String): List<String> {
    var str = str_num_normalization(str)
    var position: Int
    val list: List<String>

    str = str.replace("[", "").replace("]", "")

    if(str.indexOf(",") != -1) list = str.split(",")
    else list = listOf(str)

    return list
}


fun get_timetable_list(context: Context, view: View, flag: Int){
    try{
        firedb_timetable_class(context).get_course_symbol(view, flag)
        Log.d(TAG, "get_timetable_list -> call")
    }catch (e: Exception){
        Log.e(TAG, "get_timetable_list -> error: $e")
    }
}


