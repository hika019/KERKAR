package com.example.kerkar

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.MotionEvent
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import com.google.android.material.navigation.NavigationView

import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.main_assignment_info_item.view.*


class HomeActivity : AppCompatActivity() {




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setSupportActionBar(findViewById(R.id.toolbar))


        nav_view.setNavigationItemSelectedListener {
            when (it.itemId){
                R.id.draw_manu_home -> {
//                    val intent = Intent(this, HomeActivity::class.java)
//                    startActivity(intent)
                    false
                }
                R.id.draw_manu_unsubmitted_assignment -> {
                    val intent = Intent(this, assignment_list_activity::class.java)
                    startActivity(intent)
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


        val main_assignment_adapter = GroupAdapter<GroupieViewHolder>()
        main_assignment_adapter.add(AssignmentItem())
        main_assignment_adapter.add(AssignmentItem())
        main_assignment_adapter.add(AssignmentItem())
        main_assignment_adapter.add(AssignmentItem())
        main_assignment_adapter.add(AssignmentItem())
        main_assignment_adapter.add(AssignmentItem())
        main_assignment_adapter.add(AssignmentItem())
        main_assignment_adapter.add(AssignmentItem())
        main_assignment_adapter.add(AssignmentItem())
        main_assignment_adapter.add(AssignmentItem())
        main_assignment_adapter.add(AssignmentItem())
        main_assignment_adapter.add(AssignmentItem())
        main_assignment_info_recyclerview.adapter = main_assignment_adapter

        floatingActionButton.setOnClickListener {
            val intent = Intent(this, assignment_list_activity::class.java)
            startActivity(intent)

        }

    }

    private fun NavigationView.setNavigationItemSelectedListener(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.draw_manu_home -> Log.d("draw", "home select")
        }


        return true
    }


}

class AssignmentItem: Item<GroupieViewHolder>(){
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        //各レポートの内容をいじる
        //下のはサンプル
        val tmp = viewHolder.itemView.main_assignmennt_info_subject_textview.text

    }
    override fun getLayout(): Int {
        return R.layout.main_assignment_info_item
    }
}

class today_timetable_Item: Item<GroupieViewHolder>(){
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
    }
    override fun getLayout(): Int {
        return R.layout.today_taimetable
    }
}