package com.example.kerkar

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_home.*

class TimetableActivity :AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timetable)
        setSupportActionBar(findViewById(R.id.toolbar))

        nav_view.setNavigationItemSelectedListener {
            when (it.itemId){
                R.id.draw_manu_home -> {
                    val intent = Intent(this, HomeActivity::class.java)
                    startActivity(intent)
                    false
                }
                R.id.draw_manu_unsubmitted_assignment -> {
                    val intent = Intent(this, assignment_list_activity::class.java)
                    startActivity(intent)
                    false
                }
                R.id.draw_manu_timetable -> {
                    false
                }
                else -> false
            }

        }


    }
}