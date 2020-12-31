package com.example.kerkar

import android.app.Application
import android.content.Context
import android.util.Log
import android.widget.Toast
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings

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
        val college_name: String
)

data class college_id(
        val college_nid: String
)

class user_data(
    val college: String? = null
)


class MyApp :Application(){
    var QRResult: String? = null

    companion object {
        private var instance : MyApp? = null

        fun  getInstance(): MyApp {
            if (instance == null)
                instance = MyApp()

            return instance!!
        }
    }
}


val TAG = "firedb"

val settings = FirebaseFirestoreSettings.Builder()
        .setPersistenceEnabled(true)
        .build()

class firedb_login_register_class(private val context: Context){
    private var firedb = FirebaseFirestore.getInstance()


    fun add_user(uid: String, college: String?){
        val data = user_data(college)

        firedb.firestoreSettings = settings

        firedb.collection("user")
                .document(uid)
                .set(data)
                .addOnCanceledListener { Log.d(TAG, "add user college -> 送信完了")}
                .addOnFailureListener {
                    Log.d(TAG, "add user college -> 送信失敗")
                    error_college_upload_dialog(context)
                }

    }

    fun get_college(uid: String) {
        firedb.firestoreSettings = settings


        Log.d(TAG, "1")
        val doc = firedb.collection("user")
                .document(uid)
                .get()
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        val data = it.result
                        val list = data?.toObject(user_data::class.java)



                        Log.d(TAG, list.toString())
                        Log.d(TAG, "get college is success")

                    } else {
                        Log.d(TAG, "get college is failed")
                    }
                }

        Log.d(TAG, "3")


    }
}


class firedb_main_class(private val context: Context){
    private val firedb = FirebaseFirestore.getInstance()

    fun add_college(college_name: String){
        firedb.firestoreSettings = settings
        val id_data = id_generator(college_name)
        val data = college_data(college_name)

        firedb.collection("college")
                .document(id_data)
                .set(data)
                .addOnSuccessListener { Log.d("firedb", "add_college firedb -> 送信完了") }
                .addOnFailureListener {
                    Log.d("firedb", "add_college firedb -> 送信失敗")
                    Toast.makeText(context, "エラーが発生しました", Toast.LENGTH_SHORT).show()
                }
    }

    fun add_timetable_firedb(week_to_day: String, period: String, lecture_name: String, teacher_name: String, class_name: String){
        firedb.firestoreSettings = settings

        var data = tiemtable_data(
                str_num_normalization(week_to_day), str_num_normalization(period),
                str_num_normalization(lecture_name), str_num_normalization(teacher_name), str_num_normalization(class_name))


        //大学idを取得
        var id_data = id_generator("中部大学")



        firedb.collection("college")
            .document(id_data)
                .collection("lecture_name")
                .document()
                .set(data)
                .addOnSuccessListener { Log.d("firedb", "add_timetable_firedb -> 送信完了") }
                .addOnFailureListener {
                    Log.d("firedb", "add_timetable_firedb -> 送信失敗")
                    Toast.makeText(context, "エラーが発生しました", Toast.LENGTH_SHORT).show()
                }
    }




}