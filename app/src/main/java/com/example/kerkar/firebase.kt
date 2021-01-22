package com.example.kerkar

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.kerkar.assignment_list.TaskListItem
import com.example.kerkar.home.TodayListItem
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.*
import com.google.firebase.ktx.Firebase
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import kotlinx.android.synthetic.main.activity_assignment_list.view.*
import kotlinx.android.synthetic.main.activity_home.view.*
import kotlinx.android.synthetic.main.activity_home.view.main_assignment_info_recyclerview
import kotlinx.android.synthetic.main.activity_timetable.view.*
import kotlinx.android.synthetic.main.item_timetable.view.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap
import kotlin.math.abs


private val TAG = "firedb"

val settings = FirebaseFirestoreSettings.Builder()
        .setPersistenceEnabled(true)
        .build()

fun login_cheack(): Boolean {
    val cheack_user = Firebase.auth.currentUser
    Log.d(TAG, "login check: ${cheack_user != null}")
    return cheack_user != null
}

fun get_uid(): String {
    val uid = FirebaseAuth.getInstance().currentUser!!.uid
    return uid
}


class firedb_login_register_class(private val context: Context){
    private var firedb = FirebaseFirestore.getInstance()

    //university
    fun create_university_collection(university: String){
        val data = hashMapOf(
                "university" to university
        )
        firedb.collection("university")
                .document()
                .set(data)
                .addOnSuccessListener {
                    Log.d(TAG, "create_university_name -> 送信完了")
                    serach_university_doc_id(university)
                }
                .addOnFailureListener {
                    Log.d(TAG, "create_university_name -> 送信失敗")
                    error_college_upload_dialog(context)
                }
    }

    fun serach_university_doc_id(university_name: String){
        firedb.collection("university").whereEqualTo("university", university_name)
                .get()
                .addOnSuccessListener{ documents ->
                    for (document in documents){
//                        Log.d(TAG, "${document.id} => ${document.data}")
                        Log.d(TAG, "get university_id: ${document.id}")
                        add_university_id(document.id)
                    }
                }
    }

    fun add_university_id(university_id: String){
        val data = hashMapOf(
                "university_id" to university_id
        )
        firedb.collection("university")
                .document(university_id)
                .set(data, SetOptions.merge())
                .addOnSuccessListener { Log.d(TAG, "add_university_id -> 送信完了")}
                .addOnFailureListener {
                    Log.d(TAG, "add user college -> 送信失敗")
                    error_college_upload_dialog(context)
                }
        Log.d(TAG, "add university -> ${university_id} -> add_university_id:${university_id}")
    }


    //user
    fun add_user_data(uid: String, college: String){
        val time = SimpleDateFormat("yyyy/MM/dd HH:mm").format(Date())
        val data = hashMapOf(
                "uid" to uid,
                "university" to college,
                "create_at" to time
        )

//        firedb.firestoreSettings = settings

        val user = firedb.collection("user")
                .document(uid)
                .set(data)
                .addOnSuccessListener {
                    Log.d(TAG, "add user data -> 送信完了")
                }
                .addOnFailureListener {
                    Log.d(TAG, "add user data -> 送信失敗")
                    error_college_upload_dialog(context)
                }

        //大学idの追加
        get_univarsity_id(uid, college)


    }

    fun get_univarsity_id(uid: String, univarsity_name: String) {
        firedb.collection("university").whereEqualTo("university", univarsity_name)
                .get()
                .addOnSuccessListener{ documents ->
                    for (document in documents){
//                        Log.d(TAG, "${document.id} => ${document.data}")
                        Log.d(TAG, "get university_id: ${document.id}")
                        add_user_collection_to_university_id(uid, document.id)
                    }
                }
    }


    fun add_user_collection_to_university_id(uid: String, university_id: String){
        Log.d(TAG, "call add_university_id")

        val data = hashMapOf(
                "university_id" to university_id
        )
        firedb.collection("user")
                .document(uid)
                .set(data, SetOptions.merge())
                .addOnSuccessListener { Log.d(TAG, "add_university_id -> 送信完了")}
                .addOnFailureListener {
                    Log.d(TAG, "add user college -> 送信失敗")
                    error_college_upload_dialog(context)
                }
        Log.d(TAG, "add_user -> ${uid} -> add_university_id:${university_id}")
    }


    fun get_university_list(uid: String){
        Log.d(TAG, "####call: get_university_list #####")
        firedb.collection("university")
                .get()
                .addOnSuccessListener { documents ->
                    var university_array: Array<String> = arrayOf()
                    for (document in documents){
                        Log.d(TAG, "get university_doc_id: ${document.id}")
//                        get_university_name(document.id)

                        val niversity_name = document.get("university").toString()
                        university_array += niversity_name
                    }


                    register_dialog(context, uid).select_univarsity(university_array, uid)
                }
    }

    fun check_university_data(){
        Log.d(TAG, "check_university_data -> call")
        if(login_cheack() == true){
            val uid = FirebaseAuth.getInstance().currentUser!!.uid
            firedb.collection("user")
                    .document(uid)
                    .get()
                    .addOnCompleteListener {
                        if(it.isSuccessful){
                            Log.d(TAG, "check_university_data -> university_data is not null")
                            val university = it.result?.getString("university")
                            if(university != null){
                                //homeへ
                                val intent = Intent(context, main_activity::class.java)
                                context.startActivity(intent)
                            }else{
                                //university選択
                                Log.d(TAG, "check_university_data -> university_data is null")
                                Log.d(TAG, "select_university_rapper -> call")
                                register_dialog(context, uid).select_university_rapper()
                            }

                        }
                    }
        }
    }

}

class firedb_timetable_class(private val context: Context){
    private val firedb = FirebaseFirestore.getInstance()

    fun create_university_timetable(data: MutableMap<String, Any>){
        val login_check = login_cheack()
        if (login_check == true){
            val uid = FirebaseAuth.getInstance().currentUser!!.uid
            firedb.collection("user").document(uid)
                    .get()
                    .addOnCompleteListener {
                        if(it.isSuccessful){
                            Log.d(TAG, "get college is success")
                            //大学idを取得
                            val university_id = it.result?.getString("university_id")
                            Log.d(TAG, "university_id: ${university_id}")

                            add_course_to_univeristy(university_id!!, data)

                        }else{
                            Log.d(TAG, "get college is failed")
                        }
                    }
        }
    }


    fun add_course_to_univeristy(university_id: String, data: MutableMap<String, Any>){
        val week_to_day = data["week_to_day"]!!.toString()

        val doc = firedb.collection("university")
                    .document(university_id)
                     .collection(week_to_day)
                    .document()

        data["id"] = doc.id

        doc.set(data)
                .addOnSuccessListener {
                    Log.d(TAG, "add_course_data to university -> success")

                    add_user_timetable_(doc.id, data["week_to_day"].toString())
                }
                .addOnFailureListener {
                    Log.d(TAG, "add_course_data to university -> failure")
                    Toast.makeText(context, "時間割が正しく登録されませんでした", Toast.LENGTH_LONG).show()
                }
    }


    fun add_user_timetable_(classid: String, week_to_day: String){
        val login_check = login_cheack()
        if (login_check == true){
            val uid = FirebaseAuth.getInstance().currentUser!!.uid

            //classidで検索
            firedb.collection("user")
                    .document(uid)
                    .get()
                    .addOnSuccessListener {
                        val university_id = it.getString("university_id")
//                        Log.d(TAG, "hoge:"+ university_id)
                        Log.d(TAG, "get university_id -> success")

                        firedb.collection("university")
                                .document(university_id!!)
                                .collection(week_to_day)
                                .document(classid)
                                .get()
                                .addOnSuccessListener {
//                                    Log.d("hoge", "uni_id:${university_id}")
//                                    Log.d("hoge", "week:${week_to_day}")
//                                    Log.d("hoge", "classid:${classid}")
//                                    Log.d("hoge", "hoge:${it}")
//                                    Log.d("hoge", "hoge${it.result?.getString("week_to_day")}")


                                    val result = it
                                    //コピーとる
                                    val time = it.getString("week_to_day")
                                    val course = result.getString("course")
                                    val id = result.getString("id")
                                    val lecturer = result.get("lecturer")
                                    val room = result.getString("room")

                                    val in_data = hashMapOf(
                                            "week_to_day" to time,
                                            "id" to id,
                                            "lecturer" to lecturer,
                                            "course" to course,
                                                "room" to room
                                    )
                                    Log.d(TAG, "in_data: $in_data")

                                    val data = hashMapOf(
                                            time.toString() to in_data
                                    )

                                    firedb.collection("user")
                                            .document(uid)
                                            .set(data, SetOptions.merge())
                                            .addOnSuccessListener {
                                                Log.d(TAG, "add course to user: ${id} -> success")
                                            }
                                            .addOnFailureListener {
                                                Log.d(TAG, "add course to user: ${id} -> failure")
                                            }
                                }
                    }

        }else{
            Log.d(TAG, "not login")
        }
    }

    fun list_course_university(week_to_day: String, period: Int){

        val login_check = login_cheack()
        if(login_check == true){

            val uid = FirebaseAuth.getInstance().currentUser!!.uid

            //classidで検索
            firedb.collection("user")
                    .document(uid)
                    .get()
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            val university_id = it.result?.getString("university_id")!!
                            Log.d(TAG, "get university_id -> success")

                            //授業検索
                            firedb.collection("university")
                                    .document(university_id)
                                    .collection(week_to_day + period.toString())
                                    .get()
                                    .addOnSuccessListener {
                                        Log.d(TAG, "get ${week_to_day + period.toString()} class data -> success")

                                        val datalist: ArrayList<Any> = arrayListOf()

                                        for (document in it){

                                            val data = hashMapOf<String, Any>(
                                                    "id" to document.id,
                                                   "week_to_day" to document.get("week_to_day") as String,
                                                    "course" to document.get("course") as String,
                                                    "lecturer" to document.get("lecturer") as ArrayList<String>,
                                                    "room" to document.get("room") as String
                                            )

                                            datalist.add(data)
                                        }

                                        add_timetable(context, week_to_day, period).search_timetable_dialog(datalist)

                                    }
                                    .addOnFailureListener {
                                        Log.e(TAG, "get ${week_to_day + period.toString()} class data -> failure")
                                    }

                        }else{
                            Log.d(TAG, "get university_id -> failure")
                        }
                    }

        }else{
            Log.d(TAG, "not login")
        }
    }


    fun get_course_symbol(view: View, flag:Int) {
        val week_to_day_symbol_list = listOf("sun", "mon", "tue", "wen", "thu", "fri", "sat")
        val period_list:List<Int> = List(5){it +1}

        var timetable_data_map: MutableMap<String, String> = mutableMapOf()

        if (login_cheack() == true){
            val uid = FirebaseAuth.getInstance().currentUser!!.uid
            firedb.collection("user")
                    .document(uid)
                    .get()
                    .addOnSuccessListener {
                        Log.d(TAG, "get_course_symbol -> success")
                        for(week in week_to_day_symbol_list){
                            for(period in period_list){
                                val week_to_day = week + period.toString()
                                val tmp_data = it.get(week_to_day)
                                if (tmp_data != null){
                                    val data = it.get(week_to_day) as Map<String?, Any?>

                                    var title: String? = data["course"].toString()

                                    timetable_data_map.put(data["week_to_day"] as String, data["course"] as String)

                                }
                            }
                        }
                        if (flag ==0){
                            val week_name = arrayOf("sun", "mon", "tue", "wen", "thu", "fri", "sat")
                            val calendar: Calendar = Calendar.getInstance()
                            val week = week_name[calendar.get(Calendar.DAY_OF_WEEK)-1]

                            view.today_first_period.timetable_title_textView.text=
                                    timetable_data_map["${week}1"]
                            view.today_second_period.timetable_title_textView.text=
                                    timetable_data_map["${week}2"]
                            view.today_third_period.timetable_title_textView.text=
                                    timetable_data_map["${week}3"]
                            view.today_fourth_period.timetable_title_textView.text=
                                    timetable_data_map["${week}4"]
                            view.today_fifth_period.timetable_title_textView.text=
                                    timetable_data_map["${week}5"]
                            Log.d(TAG, "get_course_symbol (Home) -> call")

                        }else if(flag == 1){
                            //Timetable
                            view.timetable_include_mon1.timetable_title_textView.text =
                                    timetable_data_map["mon1"]
                            view.timetable_include_mon2.timetable_title_textView.text =
                                    timetable_data_map["mon2"]
                            view.timetable_include_mon3.timetable_title_textView.text =
                                    timetable_data_map["mon3"]
                            view.timetable_include_mon4.timetable_title_textView.text =
                                    timetable_data_map["mon4"]
                            view.timetable_include_mon5.timetable_title_textView.text =
                                    timetable_data_map["mon5"]

                            view.timetable_include_tue1.timetable_title_textView.text =
                                    timetable_data_map["tue1"]
                            view.timetable_include_tue2.timetable_title_textView.text =
                                    timetable_data_map["tue2"]
                            view.timetable_include_tue3.timetable_title_textView.text =
                                    timetable_data_map["tue3"]
                            view.timetable_include_tue4.timetable_title_textView.text =
                                    timetable_data_map["tue4"]
                            view.timetable_include_tue5.timetable_title_textView.text =
                                    timetable_data_map["tue5"]

                            view.timetable_include_wen1.timetable_title_textView.text =
                                    timetable_data_map["wen1"]
                            view.timetable_include_wen2.timetable_title_textView.text =
                                    timetable_data_map["wen2"]
                            view.timetable_include_wen3.timetable_title_textView.text =
                                    timetable_data_map["wen3"]
                            view.timetable_include_wen4.timetable_title_textView.text =
                                    timetable_data_map["wen4"]
                            view.timetable_include_wen5.timetable_title_textView.text =
                                    timetable_data_map["wen5"]

                            view.timetable_include_thu1.timetable_title_textView.text =
                                    timetable_data_map["thu1"]
                            view.timetable_include_thu2.timetable_title_textView.text =
                                    timetable_data_map["thu2"]
                            view.timetable_include_thu3.timetable_title_textView.text =
                                    timetable_data_map["thu3"]
                            view.timetable_include_thu4.timetable_title_textView.text =
                                    timetable_data_map["thu4"]
                            view.timetable_include_thu5.timetable_title_textView.text =
                                    timetable_data_map["thu5"]

                            view.timetable_include_fri1.timetable_title_textView.text =
                                    timetable_data_map["fri1"]
                            view.timetable_include_fri2.timetable_title_textView.text =
                                    timetable_data_map["fri2"]
                            view.timetable_include_fri3.timetable_title_textView.text =
                                    timetable_data_map["fri3"]
                            view.timetable_include_fri4.timetable_title_textView.text =
                                    timetable_data_map["fri4"]
                            view.timetable_include_fri5.timetable_title_textView.text =
                                    timetable_data_map["fri5"]

                            Log.d(TAG, "get_course_symbol (Timetable) -> call")
                        }
                    }
                    .addOnFailureListener{
                        Log.e(TAG, "get_course_symbol -> failure")
                    }

        }else{
            Log.d(TAG, "not login")
            Toast.makeText(context, "ログインされていません", Toast.LENGTH_SHORT).show()
        }
    }

    fun get_course_detail(week: String, period: Int){
        var str: String? = null
        if (login_cheack() == true){
            val uid = FirebaseAuth.getInstance().currentUser!!.uid
            firedb.collection("user")
                    .document(uid)
                    .get()
                    .addOnSuccessListener {
                        Log.d(TAG, "get_course_detail(${week+period}) -> success")
                        val raw_data = it.get(week+period.toString())
                        if(raw_data != null){
                            val map_data = raw_data as Map<String, Any>

                            str = "教科: ${map_data["course"]}\n" +
                                    "教室: ${map_data["room"]}\n"

                            val teacher = map_data["lecturer"] as List<String>
                            if(teacher.size > 1){
                                str += "講師: ${teacher[0]} ...他"
                            }else{
                                str += "講師: ${teacher[0]}"
                            }
                        }else{
                            str = "授業が登録されていません"
                        }
                        timetable_dialog_class().timetable_dialog(week, period, str!!, context)
                    }
                    .addOnFailureListener {
                        Log.e(TAG, "get_course_detail(${week+period}) -> failure")
                    }
        }else{
            Log.e(TAG, "call get_course_detail -> not login")
            Toast.makeText(context, "ログインしていないため検索不可", Toast.LENGTH_SHORT).show()
        }
    }

    fun delete_course(week: String, period: Int){
        if(login_cheack() == true){
            val uid = FirebaseAuth.getInstance().currentUser!!.uid

            val updates = hashMapOf<String, Any>(
                    week + period.toString() to FieldValue.delete()
            )

            firedb.collection("user")
                    .document(uid)
                    .update(updates)
                    .addOnCompleteListener {
                        Log.d(TAG, "deleate ${week + period} -> complete")
                    }

        }
    }


}

class firedb_add_task_class(private val context: Context){
    private val firedb = FirebaseFirestore.getInstance()

    var class_list: Array<Any> = arrayOf()
    val week_to_day_symbol_list = listOf("sun", "mon", "tue", "wen", "thu", "fri", "sat")
    val period_list:List<Int> = List(5){it +1}

    private var task_list: Array<HashMap<String, Any>> = arrayOf()

    fun get_create_classes_list(){
        if(login_cheack()){
            val uid = get_uid()

            firedb.collection("user")
                    .document(uid)
                    .get()
                    .addOnSuccessListener {

                        var class_name_array: Array<String> = arrayOf()
                        var class_id_array: Array<String> = arrayOf()
                        var class_week_to_day_array: Array<String> = arrayOf()


                        for( week in week_to_day_symbol_list){
                            for(period in period_list){
                                val week_period = week + period

                                val raw_data = it.get(week_period)
//                                Log.d("hoge", "hoge${raw_data}")

                                if (raw_data != null){
                                    val data = raw_data as Map<String, Any>
                                    class_name_array +=  data["course"] as String
                                    class_id_array +=  data["id"] as String
                                    class_week_to_day_array += data["week_to_day"] as String
                                }
                            }
                        }

                        assignment_dialog_class(context).course_selecter_dialog(class_name_array, class_id_array, class_week_to_day_array)

                    }
        }
    }

    fun add_task_to_university(data: HashMap<String, Any>) {

        val class_data: HashMap<String, String> = data["class"] as HashMap<String, String>

//        Log.d("hoge", "data: $class_data")

        val uid = get_uid()

        firedb.collection("user")
                .document(uid)
                .get()
                .addOnCompleteListener{
                    if(it.isSuccessful){
                        Log.d(TAG, "add_task_to_university -> Successfull")

                        val university_id = it.result?.getString("university_id")
                        Log.d(TAG, "university_id -> get")

                        val task_document = firedb.collection("university")
                                .document(university_id!!)
                                .collection(class_data["week_to_day"]!!)
                                .document(class_data["class_id"]!!)
                                .collection("task")
                                .document()

                        val task_id = task_document.id

                        val task_data = hashMapOf(
                            "task_id" to task_id,
                            "timelimit" to ("${data["day"]} ${data["time"]}"),
                            "task_name" to data["task_title"],
                            "note" to data["note"]
                        )

                        task_document.set(task_data, SetOptions.merge())
                                .addOnSuccessListener {
                                    Log.d(TAG, "task_data: $task_data")
                                    Log.d(TAG, "set task_data -> Success")
                                }
                                .addOnFailureListener {
                                    Log.e(TAG, "set task_data -> Failure")
                                }

                    }else{
                        Log.e(TAG, "add_task_to_university -> not Successfull")
                    }
                }
    }

    fun load_task(){
        load_task2()
        Log.d("hoge", "data2: ${task_list.size}")
    }

    fun load_task2(){
        var tmp_class_list: Array<Any> = arrayOf()

        if(login_cheack()){
            val uid = get_uid()
            firedb.collection("user")
                    .document(uid)
                    .get()
                    .addOnSuccessListener {

                        val university_id = it.getString("university_id")

                        for (week in  week_to_day_symbol_list){
                            for (time in period_list){
                                val week_period = week + time
                                val raw_data = it.get(week_period)

                                if(raw_data != null){
                                    //classdata get
                                    val data = raw_data as Map<String, Any>
                                    val tmp_class_data = hashMapOf(
                                            "course" to data["course"] as String,
                                            "id" to data["id"] as String,
                                            "week_to_day" to data["week_to_day"] as String
                                    )

                                    tmp_class_list += tmp_class_data
                                }
                            }
                        }

                        val university_collection = firedb.collection("university")
                                .document(university_id!!)

//                        val adapter = GroupAdapter<GroupieViewHolder>()

                        //task data get
                        for(class_list_item in tmp_class_list){
                            val tmp_class_data = class_list_item as HashMap<String, String>

                            university_collection.collection(tmp_class_data["week_to_day"]!!)
                                    .document(tmp_class_data["id"]!!)
                                    .collection("task")
                                    .get()
                                    .addOnSuccessListener {
                                        Log.d(TAG, "get task -> success")
                                        for( document in it){
                                            val task_id = document.getString("task_id")
                                            val task_name = document.getString("task_name")
                                            val timelimit = document.getString("timelimit")
                                            val note = document.getString("note")

                                            val task_data = hashMapOf(
                                                    "task_id" to task_id,
                                                    "task_name" to task_name,
                                                    "timelimit" to timelimit,
                                                    "note" to note
                                            )
                                            val class_data: HashMap<String, Any> = hashMapOf(
                                                    "course" to tmp_class_data["course"] as String,
                                                    "id" to tmp_class_data["id"] as String,
                                                    "week_to_day" to tmp_class_data["week_to_day"] as String,
                                                    "task" to task_data
                                                    )

                                            Log.d("hoge", "class_data: $class_data")

//                                            adapter.add(TodayListItem(
//                                                    task_data["timelimit"] as String,
//                                                    class_data["course"] as String,
//                                                    task_data["task_name"] as String
//                                                )
//                                            )
//                                            Log.d("hoge", "data: ${myApp.QRResult}")
                                            task_list += class_data
                                            Log.d("hoge", "data: ${task_list.size}")
                                        }
                                    }
                                    .addOnFailureListener {
                                        Log.e(TAG, "get task -> failure")
                                    }
//                            Log.d("hoge", "class_list: $class_list")
                        }


//                        adapter.setOnItemClickListener { item, view ->
////                            val hoge = item as Map<Any, Any>
//
//                            Toast.makeText(context, "item:${task_list[]}",Toast.LENGTH_LONG).show()
//                            Log.d("hoge", "item: ${item.javaClass.kotlin}")
//                        }
//                        view.main_assignment_info_recyclerview.adapter = adapter


//                        val teacher_list = arrayListOf("哲学", "英語", "創造理工実験", "現代社会経済", "データベース", "オブジェクト指向言語")
//                        val adapter = Home_Assignment_list_CustomAdapter(teacher_list, context)
//                        val layoutManager = LinearLayoutManager(context)
//                        view.main_assignment_info_recyclerview.layoutManager = layoutManager
//                        view.main_assignment_info_recyclerview.adapter = adapter
//                        view.main_assignment_info_recyclerview.setHasFixedSize(true)

                    }
                    .addOnFailureListener {
                        Log.e(TAG, "load_task: get_class -> Failure")
                    }
        }
    }
}

class firedb_load_task_class(private val context: Context){
    private val firedb = FirebaseFirestore.getInstance()
    private val week_to_day_symbol_list = listOf("sun", "mon", "tue", "wen", "thu", "fri", "sat")
    private val period_list:List<Int> = List(5){it +1}

    private var task_list: Array<Any> = arrayOf()

    fun home_get_task(view: View) {
//        var task_list: Array<Any> = arrayOf()
        var tmp_class_list: Array<Map<String, Any>> = arrayOf()

        if(login_cheack()){
            val uid = get_uid()
            firedb.collection("user")
                    .document(uid)
                    .get()
                    .addOnSuccessListener {

                        val university_id = it.getString("university_id")

                        for (week in  week_to_day_symbol_list){
                            for (time in period_list){
                                val week_period = week + time
                                val raw_data = it.get(week_period)

                                if(raw_data != null){
                                    //classdata get
                                    val data = raw_data as Map<String, Any>
                                    val tmp_class_data = hashMapOf(
                                            "course" to data["course"] as String,
                                            "id" to data["id"] as String,
                                            "week_to_day" to data["week_to_day"] as String
                                    )

                                    tmp_class_list += tmp_class_data
                                }
                            }
                        }

                        val university_collection = firedb.collection("university")
                                .document(university_id!!)

                        val adapter = GroupAdapter<GroupieViewHolder>()

                        //task data get
                        for(class_list_item in tmp_class_list){
                            val tmp_class_data = class_list_item as HashMap<String, String>

                            university_collection.collection(tmp_class_data["week_to_day"]!!)
                                    .document(tmp_class_data["id"]!!)
                                    .collection("task")
                                    .get()
                                    .addOnSuccessListener {
                                        Log.d(TAG, "get task -> success")
                                        for( document in it){
                                            val task_id = document.getString("task_id")
                                            val task_name = document.getString("task_name")
                                            val timelimit = document.getString("timelimit")
                                            val note = document.getString("note")
                                            val compusers = document.get("done_list")

                                            Log.d("hoge", (compusers == null).toString())

                                            var task_data = hashMapOf(
                                                    "task_id" to task_id!!,
                                                    "task_name" to task_name,
                                                    "timelimit" to timelimit,
                                                    "note" to note,
                                                    "done_list" to compusers
                                            )
                                            val class_data: HashMap<String, Any> = hashMapOf(
                                                    "course" to tmp_class_data["course"] as String,
                                                    "id" to tmp_class_data["id"] as String,
                                                    "week_to_day" to tmp_class_data["week_to_day"] as String,
                                                    "task" to task_data
                                            )

                                            Log.d("hoge", "class_data: $class_data")

                                            val day = task_data["timelimit"] as String

                                            adapter.add(TodayListItem(
                                                    day.substring(5,10),
                                                    class_data["course"] as String,
                                                    task_data["task_name"] as String
                                            ))
//                                            Log.d("hoge", "data: ${myApp.QRResult}")
                                            task_list += class_data
                                        }
                                    }
                                    .addOnFailureListener {
                                        Log.e(TAG, "get task -> failure")
                                    }
//                            Log.d("hoge", "class_list: $class_list")
                        }

                        adapter.setOnItemClickListener { item, view ->
//                            Log.d("hoge", item.javaClass.kotlin.toString())
//                            item as task_data_class
//                            Log.d("hoge", "item: ${item.id}")
//                            Log.d("hoge", "item: ${task_list[abs(item.id+1).toInt()]}")
                            val task_data = task_list[abs(item.id+1).toInt()] as Map<String, Any>
                            val task = task_data["task"] as Map<String, String>
                            Log.d("hoge", "data: ${task}")


                            val str = "期限: ${task["timelimit"]}\n" +
                                    "教科: ${task_data["course"]}\n" +
                                    "詳細: ${task["task_name"]}\n" +
                                    "その他: ${task["note"]}"
                            task_dialogs(context).home_task_ditail_dialog(task_data)
//                            Toast.makeText(context, "item: ${abs(item.id+1).toInt()}",Toast.LENGTH_LONG).show()
                        }
                        view.main_assignment_info_recyclerview.adapter = adapter
                    }
                    .addOnFailureListener {
                        Log.e(TAG, "load_task: get_class -> Failure")
                    }
        }else{
            Log.e(TAG, "not login")
        }
    }


    fun get_task_not_comp(view: View, falg: Boolean) {
//        var task_list = arrayOf<Any>()
        Log.d("hoge", "size: ${task_list.size}")

        var tmp_class_list: Array<Map<String, Any>> = arrayOf()

        if(login_cheack()){
            val uid = get_uid()
            firedb.collection("user")
                    .document(uid)
                    .get()
                    .addOnSuccessListener {

                        val university_id = it.getString("university_id")
                        for (week in  week_to_day_symbol_list){
                            for (time in period_list){
                                val week_period = week + time
                                val raw_data = it.get(week_period)

                                if(raw_data != null){
                                    //classdata get
                                    val data = raw_data as Map<String, Any>
                                    val tmp_class_data = hashMapOf(
                                            "course" to data["course"] as String,
                                            "id" to data["id"] as String,
                                            "week_to_day" to data["week_to_day"] as String
                                    )

                                    tmp_class_list += tmp_class_data
                                }
                            }
                        }

                        val university_collection = firedb.collection("university")
                                .document(university_id!!)

                        val adapter = GroupAdapter<GroupieViewHolder>()
                        task_list = arrayOf()
                        Log.d("hoge", "start size: ${task_list.size}")

                        //task data get
                        for(class_list_item in tmp_class_list){
                            val tmp_class_data = class_list_item as HashMap<String, String>

                            university_collection.collection(tmp_class_data["week_to_day"]!!)
                                    .document(tmp_class_data["id"]!!)
                                    .collection("task")
                                    .get()
                                    .addOnSuccessListener {
                                        Log.d(TAG, "get task -> success")
                                        for( document in it){
                                            val task_id = document.getString("task_id")
                                            val task_name = document.getString("task_name")
                                            val timelimit = document.getString("timelimit")
                                            val note = document.getString("note")
                                            val compusers = document.get("done_list")

//                                            Log.d("hoge", (compusers == null).toString())

                                            var task_data = hashMapOf(
                                                    "task_id" to task_id!!,
                                                    "task_name" to task_name,
                                                    "timelimit" to timelimit,
                                                    "note" to note,
                                                    "done_list" to compusers
                                            )
                                            val class_data: HashMap<String, Any> = hashMapOf(
                                                    "course" to tmp_class_data["course"] as String,
                                                    "id" to tmp_class_data["id"] as String,
                                                    "week_to_day" to tmp_class_data["week_to_day"] as String,
                                                    "task" to task_data
                                            )

                                            Log.d("hoge", "class_data: $class_data")

                                            val day = task_data["timelimit"] as String

                                            if(compusers!= null ){
                                                val done_list = compusers as Map<String, Boolean>
                                                val fuga = done_list[uid]
                                                if(done_list[uid] != true){
//                                                    task_list += class_data
                                                    adapter.add(TaskListItem(
                                                            day.substring(5,10),
                                                            class_data["course"] as String,
                                                            task_data["task_name"] as String
                                                    ))
                                                }

                                            }else{
                                                //compusersが存在する
                                                task_list += class_data
                                                adapter.add(TaskListItem(
                                                        day.substring(5,10),
                                                        class_data["course"] as String,
                                                        task_data["task_name"] as String
                                                ))
                                            }


                                        }
                                    }
                                    .addOnFailureListener {
                                        Log.e(TAG, "get task -> failure")
                                    }
//                            Log.d("hoge", "class_list: $class_list")
                        }

                        adapter.setOnItemClickListener { item, view ->
//                            Log.d("hoge", item.javaClass.kotlin.toString())
//                            item as task_data_class
                            Log.d("hoge", "item: ${item.id}")
                            Toast.makeText(context, "${item.id}", Toast.LENGTH_SHORT).show()
//                            Log.d("hoge", "item: ${task_list[abs(item.id+1).toInt()]}")
//                            val task_data = task_list[abs(item.id+1).toInt()] as Map<String, Any>
//                            val task = task_data["task"] as Map<String, String>
//                            Log.d("hoge", "data: ${task}")


//                            val str = "期限: ${task["timelimit"]}\n" +
//                                    "教科: ${task_data["course"]}\n" +
//                                    "詳細: ${task["task_name"]}\n" +
//                                    "その他: ${task["note"]}"
//                            task_dialogs(context).home_task_ditail_dialog(task_data)
//                            Toast.makeText(context, "item: ${abs(item.id+1).toInt()}",Toast.LENGTH_LONG).show()
                        }
                        view.AssignmentActivity_assignment_recyclerView.adapter = adapter
                    }
                    .addOnFailureListener {
                        Log.e(TAG, "load_task: get_class -> Failure")
                    }
        }else{
            Log.e(TAG, "not login")
        }
    }

    fun task_to_comp(class_data: Map<String, Any>) {
        if(login_cheack()){
            val uid = get_uid()
            firedb.collection("user")
                    .document(uid)
                    .get()
                    .addOnCompleteListener {
                        if(it.isSuccessful){
                            val university_id = it.result?.getString("university_id")

                            val week_to_day = class_data["week_to_day"] as String
                            val class_id = class_data["id"] as String
                            val task = class_data["task"] as Map<String, String>
                            val task_id = task["task_id"]

                            val firedb_task = firedb.collection("university")
                                    .document(university_id!!)
                                    .collection(week_to_day)
                                    .document(class_id)
                                    .collection("task")
                                    .document(task_id!!)

                            firedb_task.get()
                                    .addOnCompleteListener {
                                        if(it.isSuccessful){
//                                            Log.d("hoge", "in")
                                            var compuser: MutableMap<String, Boolean> = mutableMapOf()
                                            if(it.result?.get("done_list") == null){
                                                compuser = mutableMapOf(uid to true)
                                            }else{
                                                compuser = it.result?.get("done_list") as MutableMap<String, Boolean>
                                                compuser.put(uid, true)
                                            }
                                            val data = hashMapOf("done_list" to compuser)

                                            firedb_task.set(data,SetOptions.merge())
                                                    .addOnSuccessListener {
                                                        Log.d(TAG, "task_to_comp -> upload -> success")
                                                    }
                                                    .addOnFailureListener {
                                                        Log.e(TAG, "task_to_comp -> upload -> failure")
                                                    }

                                        }else{
                                            Log.e(TAG, "get task -> failure")
                                        }
                                    }
                        }else{
                            Log.e(TAG, "task_to_comp: get userdata -> failure")
                        }
                    }
            
        }else{
            Log.e(TAG, "not Login")
        }
    }

    fun task_to_not_comp(class_data: Map<String, Any>){
        if(login_cheack()){
            val uid = get_uid()
            firedb.collection("user")
                    .document(uid)
                    .get()
                    .addOnCompleteListener {
                        if(it.isSuccessful){
                            val university_id = it.result?.getString("university_id")

                            val week_to_day = class_data["week_to_day"] as String
                            val class_id = class_data["id"] as String
                            val task = class_data["task"] as Map<String, String>
                            val task_id = task["task_id"]

                            val firedb_task = firedb.collection("university")
                                    .document(university_id!!)
                                    .collection(week_to_day)
                                    .document(class_id)
                                    .collection("task")
                                    .document(task_id!!)

                            firedb_task.get()
                                    .addOnCompleteListener {
                                        if(it.isSuccessful){
//                                            Log.d("hoge", "in")
                                            var compuser: MutableMap<String, Boolean> = mutableMapOf()
                                            compuser = it.result?.get("done_list") as MutableMap<String, Boolean>
                                            compuser.remove(uid)
                                            val data = hashMapOf("done_list" to compuser)

                                            firedb_task.set(data,SetOptions.merge())
                                                    .addOnSuccessListener {
                                                        Log.d(TAG, "task_to_not_comp -> upload -> success")
                                                    }
                                                    .addOnFailureListener {
                                                        Log.e(TAG, "task_to_not_comp -> upload -> failure")
                                                    }
                                        }else{
                                            Log.e(TAG, "task_to_not_comp: get task -> failure")
                                        }
                                    }
                        }else{
                            Log.e(TAG, "task_to_not_comp: get userdata -> failure")
                        }
                    }

        }else{
            Log.e(TAG, "not Login")
        }
    }
}


