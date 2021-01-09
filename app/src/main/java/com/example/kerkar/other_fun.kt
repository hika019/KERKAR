package com.example.kerkar

import android.content.Context
import android.util.Log
import java.security.MessageDigest



class assignment_swith(){
    var flag = 0
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
    val str = str_num_normalization(str)
    var position: Int
    val list: List<String>

    if(str.indexOf(",") != -1) list = str.split(",")
    else list = listOf(str)

    return list
}

fun id_generator(str: String): String {
    val id = MessageDigest.getInstance("SHA-256")
            .digest(str.toByteArray())
            .joinToString(separator = "") {
                "%02x".format(it)
            }
    return id
}


fun get_course_list(week_to_day: String, context: Context){
    try{
        firedb_timetable_class(context).list_course(week_to_day)
        Log.d("home", "call")


    }catch(e: Exception){
        Log.e("fun", "get_course_list -> error: ${e}")
    }
}

fun show_course_list(context: Context){
    Log.d("home", "call2")

    val list = timetable_local_DB(context).get_timetable()
    var id_list: Array<String> = arrayOf()
    var selecter_list: Array<String> = arrayOf()
    for(item in list){
        val data = item as Map<String, Any>
        id_list += data["id"].toString()
        Log.d(com.example.kerkar.home.TAG, "item: ${item}")

    }
}