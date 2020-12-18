package com.example.kerkar

import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item
import kotlinx.android.synthetic.main.activity_assignment_list.*
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


        val hoge = GroupAdapter<GroupieViewHolder>()
        hoge.add(MainAssignmentItem())
        hoge.add(MainAssignmentItem())
        hoge.add(MainAssignmentItem())
        hoge.add(MainAssignmentItem())
        AssignmentActivity_assignment_recyclerView.adapter = hoge



        //提出済み,未提出の切り替え
        unsubmitted_or_submitted_switch.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked){
                submitted_textview.setTypeface(Typeface.DEFAULT_BOLD)
                unsubmitted_textview.setTypeface(Typeface.DEFAULT)
                val flag = 0
                Toast.makeText(this, "true", Toast.LENGTH_SHORT).show()
            }else{
                unsubmitted_textview.setTypeface(Typeface.DEFAULT_BOLD)
                submitted_textview.setTypeface(Typeface.DEFAULT)
                Toast.makeText(this, "false", Toast.LENGTH_SHORT).show()
                val flag = 1
            }
        }



    }

}
class AssignmentActivityItem: Item<GroupieViewHolder>(){
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        //各レポートの内容をいじる
        //下のはサンプル
        //val tmp = viewHolder.itemView.assignment_activity_assignment_recyclerView

    }
    override fun getLayout(): Int {
        return R.layout.main_assignment_info_item
    }
}