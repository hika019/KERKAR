package com.example.kerkar

import android.content.Context
import androidx.appcompat.app.AlertDialog
import java.util.*

class assignment_swith(){
    var flag = 0
}

data class add_assignment(var day: String, var time:String, var subject:String, var assignment_title:String, var special_notes: String){
}


class timetable_dialog_class(){
    fun timetable_dialog(time:String, context: Context?){
        var message = get_timetable_details(time)
        message = message.replace("\\n", "\n")
        if (context != null) {
            AlertDialog.Builder(context)
                .setTitle("授業")
                .setMessage(message)
                .setPositiveButton("OK") { dialog, which ->

                }
                .setNegativeButton("授業登録"){dialog, which ->
                    //登録画面

                }
                .show()
        }
    }

    private fun get_timetable_details(time: String): String {
        //str = fun 授業詳細を取得(time)
        var str = "教科:情報倫理\\n教室:951\\n教員:中川　太郎"//一時的
        if(str.isEmpty()){
            str = "授業が登録されていません"
        }
        return str
    }
}

