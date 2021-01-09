package com.example.kerkar

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.firestore.SetOptions
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.*


private val TAG = "firedb"

val settings = FirebaseFirestoreSettings.Builder()
        .setPersistenceEnabled(true)
        .build()

fun login_cheack(): Boolean {
    val cheack_user = Firebase.auth.currentUser
    Log.d(TAG, "login check: ${cheack_user != null}")
    return cheack_user != null
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



    fun get_university_list(){
        Log.d(TAG, "####call: get_university_list #####")
        firedb.collection("university")
                .get()
                .addOnSuccessListener { documents ->
                    for (document in documents){
                        Log.d(TAG, "get university_doc_id: ${document.id}")
                        get_university_name(document.id)

                    }
                }
    }
    
    fun get_university_name(university_id: String){
        var university_name: String = ""
        firedb.collection("university")
                .document(university_id)
                .get()
                .addOnCompleteListener { 
                    if(it.isSuccessful){
                        val data = it.result
                        university_name = data!!.data?.get("university").toString()
                        Log.d(TAG, university_name)

                        val localdb = tmp_local_DB(context)
                        localdb.insert_tmp(university_name)

                    }else{
                        Log.d(TAG, "failed: get_university_name -> ${university_id}")
                    }
                }
                .addOnFailureListener {
                    Log.d(TAG, "Error: get_university_name -> ${it}")
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
                    .addOnCompleteListener {
                        if(it.isSuccessful){
                            val university_id = it.result?.getString("university_id")
//                            Log.d(TAG, "aa"+ university_id)
                            Log.d(TAG, "get university_id -> success")

                            firedb.collection("university")
                                    .document(university_id!!)
                                    .collection(week_to_day)
                                    .document(classid)
                                    .get()
                                    .addOnCompleteListener {
                                        if(it.isSuccessful){
                                            Log.d(TAG, "coped course -> success")

                                            val result = it.result!!
                                            //コピーとる
                                            val time = result.getString("week_to_day")
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
                                                    time to in_data
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
                                        }else{
                                            Log.d(TAG, "coped course -> failure")
                                        }

                                    }
                        }else{
                            Log.d(TAG, "get university_id -> failure")
                        }
                    }

        }else{
            Log.d(TAG, "not login")
        }
    }

    fun list_course(week_to_day: String){

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
                                    .collection(week_to_day)
                                    .get()
                                    .addOnSuccessListener {
                                        val timetable_local_DB = timetable_local_DB(context)
                                        timetable_local_DB.clear()

                                        for (document in it){
                                            Log.d(TAG, "doc list: ${document.id}")
                                            Log.d(TAG, "doc list: ${document.get("course")}")
                                            Log.d(TAG, "doc list: ${document.get("lecturer")}")
                                            Log.d(TAG, "doc list: ${document.get("lecturer")!!.javaClass.kotlin}")
                                            Log.d(TAG, "doc list: ${document.get("room")}")


                                            timetable_local_DB.insert_timetable(
                                                    document.id,
                                                    document.get("week_to_day") as String,
                                                    document.get("course") as String,
                                                    document.get("lecturer") as ArrayList<String>,
                                                    document.get("room") as String
                                            )

                                        }
                                    }
                            
                        }else{
                            Log.d(TAG, "get university_id -> failure")
                        }
                    }
            
            
            


        }else{
            Log.d(TAG, "not login")
        }
    }

}