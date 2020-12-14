package com.example.kerkar

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item
import kotlinx.android.synthetic.main.activity_main.*

import kotlinx.android.synthetic.main.main_assignment_info_item.view.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        setSupportActionBar(findViewById(R.id.toolbar))

        
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