package com.example.kerkar

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.add_assignment_dialog.view.*
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
        main_assignment_adapter.add(MainAssignmentItem())
        main_assignment_adapter.add(MainAssignmentItem())
        main_assignment_adapter.add(MainAssignmentItem())
        main_assignment_adapter.add(MainAssignmentItem())
        main_assignment_adapter.add(MainAssignmentItem())
        main_assignment_info_recyclerview.adapter = main_assignment_adapter

        main_assignment_adapter.setOnItemClickListener { item, view ->
            var str = "期限: 12/25\\n科目: 情報倫理\\n詳細: 小課題"
            str = str.replace("\\n", "\n")
            AlertDialog.Builder(this)
                    .setTitle("課題")
                    .setMessage(str)
                    .setPositiveButton("OK", {dialog, which ->

                    })
                    .show()
        }
        floatingActionButton.setOnClickListener {
            val mdialogView = LayoutInflater.from(this).inflate(R.layout.add_assignment_dialog, null)
            val mBilder = AlertDialog.Builder(this)
                .setView(mdialogView)
                .setTitle("課題追加")
                .setPositiveButton("確定", {dialog, which ->
                    var add_assignment = add_assignment(mdialogView.dialog_deadline_day.text.toString(),
                                                        mdialogView.dialog_deadline_time.text.toString(),
                                                        mdialogView.dialog_subject.text.toString(),
                                                        mdialogView.dialog_assignment_title.text.toString(),
                                                        mdialogView.dialog_assignment_special_notes.text.toString())
                    Log.d("dialog", add_assignment.day)//上手く取り出せない
                })
                .setNegativeButton("破棄", {dialog, which ->

                })

            mBilder.show()

        }

    }


}

class MainAssignmentItem: Item<GroupieViewHolder>(){
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        //各レポートの内容をいじる
        //下のはサンプル
        val tmp = viewHolder.itemView.main_activity_info_title_textview.text

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