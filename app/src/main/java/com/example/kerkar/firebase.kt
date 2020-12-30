package com.example.kerkar

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore

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


class firedb_class(private val context: Context){
    private val firedb = FirebaseFirestore.getInstance()

    fun add_timetable_firedb(week_to_day: String, period: String, lecture_name: String, teacher_name: String, class_name: String){

        val data = tiemtable_data(week_to_day, period, lecture_name, teacher_name, class_name)

        firedb.collection("授業")
            .document()
            .set(data)
            .addOnSuccessListener { Log.d("firedb", "add_timetable_firedb -> 送信完了") }
            .addOnFailureListener {
                Log.d("firedb", "add_timetable_firedb -> 送信失敗")
                Toast.makeText(context, "", Toast.LENGTH_SHORT).show()
            }
    }
}