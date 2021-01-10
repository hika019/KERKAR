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
    var str = str_num_normalization(str)
    var position: Int
    val list: List<String>

    str = str.replace("[", "").replace("]", "")

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
        timetable_local_DB(context).clear()
        firedb_timetable_class(context).list_course(week_to_day)
        Log.d("home", "call")


    }catch(e: Exception){
        Log.e("fun", "get_course_list -> error: ${e}")
    }
}

fun show_course_list(context: Context): Array<String> {
    Log.d("home", "call2")

    val list = timetable_local_DB(context).get_timetable()
    var data_list: Array<Any> = arrayOf()
    var selecter_list: Array<String> = arrayOf()

    for(item in list){
        val data = item as Map<String, Any>
        data_list += data

        val week_to_day = data["week_to_day"]
        val course = data["course"]
        val teacher = str_to_array(data["lecturer"] as String)
        val room = data["room"]
        var lecturer = ""

        if(teacher.size > 1){
            lecturer += teacher[0] + " ...他"
        }else{
            lecturer += teacher[0]
        }

        val str = "教科: ${course}\n" +
                "講師: ${lecturer}\n" +
                "教室: ${room}"

        selecter_list += str

        Log.d("hoge", selecter_list.toString())
        Log.d(com.example.kerkar.home.TAG, "item: ${item}")
    }
    return selecter_list

}

fun get_course_symbol_map(context: Context){
    val week_to_day_symbol_list = listOf("sun", "mon", "tue", "wen", "thu", "fri", "sat")
    val period_list = listOf(1, 2, 3, 4, 5)
    for(week_item in week_to_day_symbol_list){
        for(period in period_list){
            val week_to_day = week_item + period.toString()
            firedb_timetable_class(context).get_course_symbol(week_to_day)
            tmp_local_DB(context).get_tmp()

        }


    }


}