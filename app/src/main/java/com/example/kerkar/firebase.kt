package com.example.kerkar

import android.app.Application
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

class Global : Application(){
    var value: String? = null

    companion object {
        private var instance : Global? = null

        fun  getInstance(): Global {
            if (instance == null)
                instance = Global()

            return instance!!
        }
    }
}

data class tiemtable_data(
    val week_to_day: String,
    val period: String,
    val lecture_name: String,
    val teacher_name: String,
    val class_name: String
     )

data class assignment_data(
    val day: String,
    val tiem: String,
    val lecture_name: String,
    val assignment_name: String,
    val note: String?
)

data class college_data(
        val college_name: String?
)

data class college_id(
        val college_nid: String
)

data class user_data(
    val college: String? = null
)



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
    val global = Global.getInstance()

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
        val time = SimpleDateFormat("yyyy/MM/dd_HH:mm-ss").format(Date())
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


    fun get_college() {
        val login_check = login_cheack()
        if (login_check == true){
            val uid = FirebaseAuth.getInstance().currentUser!!.uid
            val postDoc = firedb.collection("user").document(uid)
            val hoge = postDoc
                    .get()
                    .addOnCompleteListener {
                        if(it.isSuccessful){
                            Log.d(TAG, "get college is success")
                            val data = it.result

                        }else{
                            Log.d(TAG, "get college is failed")
                        }
                    }

        }

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
//                    val hoge = tmp_local_DB(context)
//                    hoge.get_tmp()
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


class firedb_main_class(private val context: Context){
    private val firedb = FirebaseFirestore.getInstance()

    fun get_college() {
        val login_check = login_cheack()
        if (login_check == true){
            val uid = FirebaseAuth.getInstance().currentUser!!.uid
            val postDoc = firedb.collection("user").document(uid)
            val hoge = postDoc
                    .get()
                    .addOnCompleteListener {
                        if(it.isSuccessful){
                            Log.d(TAG, "get college is success")
                            val data = it.result

                        }else{
                            Log.d(TAG, "get college is failed")
                        }
                    }

        }

    }

    fun add_university_timetable_firedb(week_to_day: String, period: String, lecture_name: String, teacher_name: String, class_name: String){
        firedb.firestoreSettings = settings

        var data = hashMapOf(
                "week_to_day" to  week_to_day + period,
                "lecture_name" to  lecture_name,
                "teacher_name" to  teacher_name,
                "class_name" to  class_name
        )


        //大学idを取得
        var id_college_data = id_generator("中部大学")



        firedb.collection("college")
            .document(id_college_data)
                .collection(lecture_name)
                .document()
                .set(data)
                .addOnSuccessListener { Log.d("firedb", "add_timetable_firedb -> 送信完了") }
                .addOnFailureListener {
                    Log.d("firedb", "add_timetable_firedb -> 送信失敗")
                    Toast.makeText(context, "エラーが発生しました", Toast.LENGTH_SHORT).show()
                }
    }




}