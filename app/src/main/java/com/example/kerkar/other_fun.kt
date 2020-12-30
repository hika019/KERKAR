package com.example.kerkar

fun week_to_day_jp_chenger(week:String): String{
    val week_to_day_jp_list = listOf("日","月", "火", "水", "木", "金", "土")
    val week_to_day_symbol_list = listOf("sun", "mon", "tue", "wen", "thu", "fri", "sat")
    val index: Int = week_to_day_symbol_list.indexOf(week)
    return week_to_day_jp_list[index]
}

fun week_to_day_symbol_chenger(week:String): String{
    val week_to_day_jp_list = listOf("日","月", "火", "水", "木", "金", "土")
    val week_to_day_symbol_list = listOf("sun", "mon", "tue", "wen", "thu", "fri", "sat")
    val index: Int = week_to_day_jp_list.indexOf(week)
    return week_to_day_symbol_list[index]
}

fun str_num_normalization(str: String): String{
    val norm_str = str.replace("０","0").replace("１","1")
            .replace("２","2").replace("３","3")
            .replace("４","4").replace("５","5")
            .replace("６","6").replace("７","7")
            .replace("８","8").replace("９","9")
            .replace("　"," ")
            .replace("Ⅰ","I")
            .replace("Ⅱ","II").replace("Ⅲ","III")
            .replace("Ⅳ","IV").replace("Ⅴ","V")
            .replace("Ⅵ","VI").replace("Ⅶ","VII")
            .replace("Ⅷ","VIII").replace("Ⅸ","IX")
            .replace("ⅰ","I")
            .replace("ⅱ","II").replace("ⅲ","III")
            .replace("ⅳ","IV").replace("ⅴ","V")
            .replace("ⅵ","VI").replace("ⅶ","VII")
            .replace("ⅷ","VIII").replace("ⅸ","IX")
            .replace("（","(").replace("）",")")

    return norm_str
}