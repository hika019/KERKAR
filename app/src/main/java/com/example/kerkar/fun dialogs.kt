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
    fun timetable_dialog(week:String, time:Int, context: Context?){
        var message = get_timetable_details(week)
        message = message.replace("\\n", "\n")
        if (context != null) {
            AlertDialog.Builder(context)
                .setTitle("授業")
                .setMessage(message)
                .setPositiveButton("ok") { dialog, which ->

                }
                .setNegativeButton("授業登録"){dialog, which ->
                    //登録画面
                    val add_timetable = add_timetable(context, week, time)
                    add_timetable.add_timetable_dialog()

                }
                .setNeutralButton("課題を確認") { dialog, which ->

                }
                .show()
        }
    }

    private fun get_timetable_details(time: String): String {
        var str: String = ""
        //str = fun 授業詳細を取得(time)
        str = "教科:情報倫理\\n教室:951\\n教員:中川　太郎"//一時的
        if(str.isEmpty()){
            str = "授業が登録されていません"
        }
        return str
    }
}

class add_timetable(var context: Context, var week:String, val period: Int){
    fun add_timetable_dialog(){
        val dialog_messege = LayoutInflater.from(context).inflate(R.layout.dialog_add_class_editer, null)
        val dialog = AlertDialog.Builder(context)
            .setView(dialog_messege)
            .setTitle("授業登録")
            .setPositiveButton("確定") { dialog, which ->

                val week_to_day = dialog_messege.week_to_day_edit_textview.text
                val period = dialog_messege.period_edittextview.text
                val lecture_name = dialog_messege.lecture_neme_edittext.text
                val teacher_name = dialog_messege.teacher_name_edittext.text
                val class_name = dialog_messege.class_name_edittext.text




                //空欄の確認
                if (week_to_day.isNotEmpty() && period.isNotEmpty() &&
                        lecture_name.isNotEmpty() && teacher_name.isNotEmpty() &&
                        class_name.isNotEmpty()) {

                    Log.d("add_timetable", "未入力なし")

                    //時間数の確認
                    if (period.toString().toInt() < 6){
                        val week_to_daylist = listOf("月", "火", "水", "木", "金")
                        //登録
                        Log.d("add_timetable", "時限が5以下です")
                        Log.d("add_timetable", "これ:"+week_to_daylist.find{it == week_to_day.toString()}.toString())
                        if(week_to_daylist.find{it == week_to_day.toString()} != null){
                            Log.d("add_timetable", "edit:"+week_to_day.toString())
                            Log.d("add_timetable", "登録へ")

                            //登録fun

                        }else{
                            Log.d("add_timetable", "曜日が不正")
                            Toast.makeText(context, "曜日は漢字一文字にしてください", Toast.LENGTH_LONG).show()
                        }
                    }else{
                        //オーバー
                        Log.d("add_timetable", "時限数が5以上:${period}")
                        Toast.makeText(context, "時限数は5以下まで対応しています\n" +
                                                        "入力された時限数は6以上のため登録されていません\n" +
                                                        "入力された値: ${period}", Toast.LENGTH_LONG).show()
                    }


                } else {
                    Toast.makeText(context, "未入力の場所があります", Toast.LENGTH_SHORT).show()
                    Log.d("add_timetable", "未入力あり")
                }
                Log.d("add_timetable", "end add_timetable")


            }
            .setNegativeButton("破棄") { dialog, which ->

            }
            .setNeutralButton("検索") {dialog, which ->
                search_timetable_dialog(context, week, period)
            }
        dialog.show()
    }

    fun week_to_day_jp_chenger(week:String): String{
        val week_to_day_jp_list = listOf("日","月", "火", "水", "木", "金", "土")
        val week_to_day_symbol_list = listOf("sun", "mon", "tue", "wen", "thu", "fri", "sat")
        val index: Int = week_to_day_symbol_list.indexOf(week)
        return week_to_day_jp_list[index]
    }


    fun search_timetable_dialog(context:Context, week: String, period:Int){
        Log.d("dialog", "called search_timetable_dialog")

//        val classList: Array<String> = serch_classes(time_and_week)//授業検索

        val colorList: Array<String> = arrayOf("倫理\n山田　太郎", "マクロ経済\n鈴木　恵一", "英語スキル\nデイビッド", "哲学\n奥居　達也", "創造理工実験\n長瀬　亮, 渡辺　二郎", "統計学\n早瀬　はるか", "形式言語とオートマトン\n奥居　太一")
        val builder = AlertDialog.Builder(context)
        val week_jp = week_to_day_jp_chenger(week)
        builder.setTitle(week_jp +"曜日 " +period+"限 で検索されています")
        builder.setItems(colorList) { dialog, which ->
            Toast.makeText(context, colorList[which], Toast.LENGTH_SHORT).show()
        }
        val dialog = builder.create()
        dialog.show()
    }

}

