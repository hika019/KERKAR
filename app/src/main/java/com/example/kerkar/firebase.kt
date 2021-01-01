package com.example.kerkar

import android.app.Application
import android.content.Context
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.ktx.Firebase

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



val TAG = "firedb"

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


    fun add_user_college(uid: String, college: String?){
        val data = college_data(college)

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

        var data = hashMapOf(
                "week_to_day" to  week_to_day,
                "period" to period,
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