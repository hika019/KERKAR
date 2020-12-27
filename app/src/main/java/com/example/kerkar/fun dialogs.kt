package com.example.kerkar

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.dialog_add_class_editer.view.*


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
                .setPositiveButton("ok") { dialog, which ->

                }
                .setNegativeButton("授業登録"){dialog, which ->
                    //登録画面
                    val add_timetable = add_timetable(context)
                    add_timetable.add_timetable_dialog()

                }
                .setNeutralButton("課題を確認") { dialog, which ->

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

class add_timetable(var context: Context){
    fun add_timetable_dialog(){
        val dialog_messege = LayoutInflater.from(context).inflate(R.layout.dialog_add_class_editer, null)
        val dialog = AlertDialog.Builder(context)
            .setView(dialog_messege)
            .setTitle("授業登録")
            .setPositiveButton("確定") { dialog, which ->
                if (dialog_messege.week_to_day_edit_textview.text.isNotEmpty() and
                    dialog_messege.period_edittextview.text.isNotEmpty() and
                    dialog_messege.lecture_neme_edittext.text.isNotEmpty() and
                    dialog_messege.teacher_name_edittext.text.isNotEmpty() and
                    dialog_messege.class_name_edittext.text.isNotEmpty()) {
                    //登録
                    Log.d("add_timetable", "登録")
                } else {
                    Toast.makeText(context, "未入力の場所があります", Toast.LENGTH_SHORT).show()
                    Log.d("add_timetable", "未入力あり")
                }
            }
            .setNegativeButton("破棄") { dialog, which ->

            }
            .setNeutralButton("検索") {dialog, which ->
                search_timetable_dialog()
            }
        dialog.show()
    }

    fun search_timetable_dialog(){
    }

}

