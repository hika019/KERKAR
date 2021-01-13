package com.example.kerkar

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.dialog_add_assignment.view.*
import kotlinx.android.synthetic.main.dialog_add_class_editer.view.*
import kotlinx.android.synthetic.main.dialog_add_university.view.*


data class add_assignment(var day: String, var time: String, var subject: String, var assignment_title: String, var special_notes: String){
}
class add_task(){
    var day: String? = null
    var time: String? = null
    var subject_data: Map<String, String> = mutableMapOf()
    var assignment_title: String? = null
    var special_notes: String? = null
}


class assignment_dialog_class(val context: Context){

    var day: String? = null
    var time: String? = null
    var subject_data: MutableMap<String, String> = mutableMapOf()
    var assignment_title: String? = null
    var special_notes: String? = null


    val home_dialog_View = LayoutInflater.from(context).inflate(
            R.layout.dialog_add_assignment,
            null
    )


    fun add_task_rapper(){
        //登録している授業の一覧
        firedb_task_class(context).get_registered_classes_list()//をつかう
    }


    fun course_selecter_dialog(class_name_list: Array<String>,
                               class_id_list: Array<String>,
                               class_week_to_day_list: Array<String>){

        var select_point: Int? = null

        val builder = AlertDialog.Builder(context)

        builder.setTitle("追加する課題の授業を選択")
                .setSingleChoiceItems(class_name_list, -1){ dialog, which ->
                    select_point = which
                }
                .setPositiveButton("確定"){ dialog, which ->
                    if(select_point != null) {
//                        create_task(class_name_list[select_point!!],
//                                class_id_list[select_point!!],
//                                class_week_to_day_list[select_point!!]
//                        )
                        create_task()
                        val data =hashMapOf<String, String>(
                                "class_name" to class_name_list[select_point!!],
                                "class_id" to class_id_list[select_point!!],
                                "week_to_day" to class_week_to_day_list[select_point!!]
                        )

                        //表示
                        home_dialog_View.dialog_subject.text = "${data["class_name"]}"


                    }
                }
                .show()

    }

    fun create_task(){


        Log.d("hoge", "hoge: ${subject_data}")


        home_dialog_View.dialog_deadline_day_add_button.setOnClickListener{

        }

        home_dialog_View.dialog_deadline_time_add_button.setOnClickListener{
            Toast.makeText(context, "hoge", Toast.LENGTH_SHORT).show()
        }

        home_dialog_View.dialog_subject_add_button.setOnClickListener{
            add_task_rapper()
        }


        val mBilder = AlertDialog.Builder(context)
                .setView(home_dialog_View)
                .setTitle("課題追加")
                .setPositiveButton("確定") { dialog, which ->
                    var add_assignment = add_assignment(
                            home_dialog_View.dialog_deadline_day.text.toString(),
                            home_dialog_View.dialog_deadline_time.text.toString(),
                            home_dialog_View.dialog_subject.text.toString(),
                            home_dialog_View.dialog_assignment_title.text.toString(),
                            home_dialog_View.dialog_assignment_special_notes.text.toString()
                    )
                    Log.d("home", add_assignment.toString())

                    if(add_assignment.day.isNotEmpty() &&
                            add_assignment.time.isNotEmpty() &&
                            add_assignment.subject.isNotEmpty() &&
                            add_assignment.assignment_title.isNotEmpty()){

                    }else{
                        Toast.makeText(context, "空の部分があります", Toast.LENGTH_SHORT).show()
                    }



                    Log.d("dialog", add_assignment.day + add_assignment.time)
                }
                .setNegativeButton("破棄") { dialog, which ->

                }

        mBilder.show()
    }


    fun assigmenment_ditail_dialog(str: String, position: Int){


        AlertDialog.Builder(context)
                .setTitle("課題")
                .setMessage(str)
                .setPositiveButton("OK") { dialog, which ->

                }
                .setNeutralButton("提出済みにする") { dialog, which ->
                    Log.d("Assignment", "$position　を提出済みにする")

                }
                .show()
    }

}


class timetable_dialog_class(){

    fun timetable_dialog_rapper(week: String, time: Int, context: Context){
        firedb_timetable_class(context).get_course_detail(week, time)
    }

    fun timetable_dialog(week: String, time: Int, message: String, context: Context?){
//        var message = get_timetable_details(week, time)

        val week_jp = week_to_day_jp_chenger(week)

        if (context != null) {
            AlertDialog.Builder(context)
                .setTitle("${week_jp}曜日 ${time}限の授業")
                .setMessage(message)
                .setPositiveButton("授業登録") { dialog, which ->
                    //登録画面
                    val add_timetable = add_timetable(context, week, time)
                    add_timetable.search_timetable_dialog_rapper()
                }
                .setNegativeButton("戻る"){ dialog, which ->

                }
                .setNeutralButton("課題を確認") { dialog, which ->

                }
                .show()
        }
    }

    private fun get_timetable_details(week: String, period: Int): String {
        var str: String = ""
        //str = fun 授業詳細を取得(time)
        str = "教科:情報倫理\n教室:951\n教員:中川　太郎"//一時的
        if(str.isEmpty()){
            str = "授業が登録されていません"
        }
        return str
    }
}

class add_timetable(var context: Context, var week: String, val period: Int){
    val firedb = firedb_timetable_class(context)
    val TAG = "add_timetable"

    fun add_timetable_dialog(){
        //localdbにダウンロード
//        get_course_list(week+period, context)

        val dialog_messege = LayoutInflater.from(context).inflate(R.layout.dialog_add_class_editer, null)
        val dialog = AlertDialog.Builder(context)
            .setView(dialog_messege)
            .setTitle("授業登録")
            .setPositiveButton("確定") { dialog, which ->

                val week_to_day = dialog_messege.week_to_day_edit_textview.text.toString()
                val period = dialog_messege.period_edittextview.text.toString()
                var lecture_name = dialog_messege.lecture_neme_edittext.text.toString()
                val teacher_name = dialog_messege.teacher_name_edittext.text.toString()
                var class_name = dialog_messege.class_name_edittext.text.toString()

                class_name = str_num_normalization(class_name)
                lecture_name = str_num_normalization(lecture_name)

                //空欄の確認
                if (week_to_day.isNotEmpty() && period.isNotEmpty() &&
                        lecture_name.isNotEmpty() && teacher_name.isNotEmpty() &&
                        class_name.isNotEmpty()) {

                    Log.d(TAG, "未入力なし")

                    //時間数の確認
                    if (period.toString().toInt() < 6){

                        val week_to_daylist = listOf("月", "火", "水", "木", "金")
                        //登録
                        Log.d(TAG, "時限が5以下です")
//                        Log.d("add_timetable", "これ:"+week_to_daylist.find{it == week_to_day.toString()}.toString())
                        if(week_to_daylist.find{it == week_to_day.toString()} != null){
                            Log.d(TAG, "edit:" + week_to_day)
                            Log.d(TAG, "登録へ")

                            //登録fun
                            val week_symbol = week_to_day_symbol_chenger(week_to_day)
                            val teacher_list = str_to_array(teacher_name)


                            var data = mutableMapOf(
                                    "week_to_day" to  week_symbol + period,
                                    "course" to  lecture_name,
                                    "lecturer" to  teacher_list,
                                    "room" to  class_name
                            )

                            Log.d(TAG, teacher_list.toString())

                            //firebase
                            firedb.create_university_timetable(data)
                        }else{
                            Log.d(TAG, "曜日が不正")
                            Toast.makeText(context, "曜日は漢字一文字にしてください", Toast.LENGTH_LONG).show()
                        }
                    }else{
                        //オーバー
                        Log.d(TAG, "時限数が5以上:${period}")
                        Toast.makeText(context, "時限数は5以下まで対応しています\n" +
                                "入力された時限数は6以上のため登録されていません\n" +
                                "入力された値: ${period}", Toast.LENGTH_LONG).show()
                    }

                } else {
                    Toast.makeText(context, "未入力の場所があります", Toast.LENGTH_SHORT).show()
                    Log.d(TAG, "未入力あり")
                }
                Log.d(TAG, "end add_timetable")

            }
            .setNegativeButton("破棄") { dialog, which ->

            }
            .setNeutralButton("検索") { dialog, which ->
//                search_timetable_dialog_rapper()
            }
        dialog.create().show()
    }


    fun search_timetable_dialog_rapper(){
        try{
            Log.d(TAG, "call search_timetable_dialog_rapper -> success")
            firedb_timetable_class(context).list_course_university(week, period)
        }catch (e: Exception){
            Log.e(TAG, "call search_timetable_dialog_rapper -> failure")
            Log.e(TAG, "error: ${e}")

        }
    }


    fun search_timetable_dialog(list: List<Any>){
        Log.d("dialog", "called search_timetable_dialog")

//        val list = show_course_list(context)
        //localdbからデータ取得
//        val list = timetable_local_DB(context).get_timetable()
        var id_list: Array<String> = arrayOf()
        var selecter_list: Array<String> = arrayOf()

        for(item in list){
            val data = item as Map<String, Any>
            id_list += data["id"] as String

            val week_to_day = data["week_to_day"]
            val course = data["course"]
            val teacher = data["lecturer"] as List<String>
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



        val builder = AlertDialog.Builder(context)
        val week_jp = week_to_day_jp_chenger(week)
        var index: Int = 0

        builder.setTitle(week_jp + "曜日 " + period + "限 で検索されています")
            .setSingleChoiceItems(selecter_list, -1) { dialog, which ->
//                Toast.makeText(context, id_list[which], Toast.LENGTH_SHORT).show()
                index = which
            }
            .setPositiveButton("確定"){ dialog, which ->

                //登録処理
                val firedb = firedb_timetable_class(context)
                Log.d("firedb", "index: ${index}")
                firedb.add_user_timetable_(id_list[index], week+period)
                Toast.makeText(context, "登録されました", Toast.LENGTH_SHORT).show()

//                get_timetable_list(context, null, null)

            }
            .setNegativeButton("空き授業登録"){dialog, which ->

                //firedbから消去
                firedb_timetable_class(context).delete_course(week, period)
            }
            .setNeutralButton("授業をつくる") { dialog, which ->
                add_timetable_dialog()
            }
        val dialog = builder.create()
        dialog.show()
    }

}

fun error_college_upload_dialog(context: Context){
    val messege = "ユーザー情報が正しくアップロードされなかった可能性があります。"
    val dialog = AlertDialog.Builder(context)
            .setTitle("アップロードエラー")
            .setMessage(messege)
            .setPositiveButton("OK"){ dialog, which -> false}
            .show()
}



class register_dialog(val context: Context, val uid: String){

    fun add_university(){
        val dialog_layout = LayoutInflater.from(context).inflate(R.layout.dialog_add_university, null)
        val firedb = firedb_login_register_class(context)

        val dialog = AlertDialog.Builder(context)
                .setTitle("大学を追加")
                .setView(dialog_layout)
                .setPositiveButton("登録"){ dialog, which ->
                    val university_name = dialog_layout.univarsity_edittext.text.toString()
                    firedb.create_university_collection(university_name)

                    firedb.add_user_data(uid, university_name)

                    val i = Intent(context, main_activity::class.java)
                    context.startActivity(i)

                }
                .setNegativeButton("戻る"){ dialog, which ->
                    select_university_rapper()
                }

        dialog.create().show()
    }


    fun select_university_rapper(){
        firedb_login_register_class(context).get_university_list(uid)

    }


    fun select_univarsity(list: Array<String>, uid: String) {

        var university:String? = null
//        val list = localdb.get_tmp()

        val dialog = AlertDialog.Builder(context)
                .setTitle("大学の選択")
                .setSingleChoiceItems(list, -1){ dialog, which ->
                    university = list[which]
                    Toast.makeText(context, list[which], Toast.LENGTH_SHORT).show()
                }
                .setPositiveButton("確定"){ dialog, which ->
//                    Toast.makeText(context, university.toString(), Toast.LENGTH_SHORT).show()
                    if(university != null){
                        val firedb = firedb_login_register_class(context)
                        firedb.add_user_data(uid, university.toString())
                        val i = Intent(context, main_activity::class.java)
                        context.startActivity(i)
                    }else{
                        Toast.makeText(context, "大学の選択/追加をしてください",Toast.LENGTH_LONG).show()
                        select_university_rapper()
                    }
                }
                .setNegativeButton("大学を追加"){ dialog, which->
                    add_university()
                }
        dialog.create().show()
    }
}

