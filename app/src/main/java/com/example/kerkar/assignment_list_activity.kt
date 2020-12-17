package com.example.kerkar

import android.content.Intent
import android.os.Bundle
import android.view.ContextMenu
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_assignment_list.*
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_home.nav_view

class assignment_list_activity :AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_assignment_list)

        setSupportActionBar(findViewById(R.id.toolbar))

        nav_view.setNavigationItemSelectedListener {
            when (it.itemId){
                R.id.draw_manu_home -> {
                    val intent = Intent(this, HomeActivity::class.java)
                    startActivity(intent)
                    false
                }
                R.id.draw_manu_unsubmitted_assignment -> {
//                    val intent = Intent(this, assignment_list_activity::class.java)
//                    startActivity(intent)
                    false
                }
                R.id.draw_manu_timetable -> {
                    val intent = Intent(this, TimetableActivity::class.java)
                    startActivity(intent)
                    false
                }
                else -> false
            }

        }
        
        switch1.setOnCheckedChangeListener { buttonView, isChecked ->
            if(switch1.isChecked){
                Toast.makeText(this, "true", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(this, "false", Toast.LENGTH_SHORT).show()
            }
        }

        


    }

}