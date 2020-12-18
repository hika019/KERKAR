package com.example.kerkar

import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item
import kotlinx.android.synthetic.main.activity_assignment_list.*
import kotlinx.android.synthetic.main.activity_assignment_list.nav_view
import kotlinx.android.synthetic.main.assignment_activity_item.view.*


class assignment_list_activity :AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        val assignmentSwith=assignment_swith()
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

        val main_assignment_adapter = GroupAdapter<GroupieViewHolder>()
        main_assignment_adapter.add(assignment_item())
        main_assignment_adapter.add(assignment_item())
        main_assignment_adapter.add(assignment_item())
        main_assignment_adapter.add(assignment_item())
        main_assignment_adapter.add(assignment_item())
        main_assignment_adapter.add(assignment_item())
        main_assignment_adapter.add(assignment_item())
        main_assignment_adapter.add(assignment_item())
        main_assignment_adapter.add(assignment_item())
        main_assignment_adapter.add(assignment_item())
        main_assignment_adapter.add(assignment_item())
        main_assignment_adapter.add(assignment_item())
        AssignmentActivity_assignment_recyclerView.adapter = main_assignment_adapter








        //提出済み,未提出の切り替え
        unsubmitted_or_submitted_switch.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked){
                submitted_textview.setTypeface(Typeface.DEFAULT_BOLD)
                unsubmitted_textview.setTypeface(Typeface.DEFAULT)
                assignmentSwith.flag=0
                Toast.makeText(this, "true", Toast.LENGTH_SHORT).show()
            }else{
                unsubmitted_textview.setTypeface(Typeface.DEFAULT_BOLD)
                submitted_textview.setTypeface(Typeface.DEFAULT)
                Toast.makeText(this, "false", Toast.LENGTH_SHORT).show()
                assignmentSwith.flag=1
            }
        }



    }

}
class assignment_item: Item<GroupieViewHolder>() {
    val assignmentSwith=assignment_swith()
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        Log.d("item", "call item")
        if(assignmentSwith.flag == 0){
            val tmp = viewHolder.itemView.assignment_activity_info_title_textview.text
        }
        else{
            viewHolder.itemView.assignment_activity_info_title_textview.text = "英語"
        }


    }

    override fun getLayout(): Int {
        return R.layout.assignment_activity_item
    }
}