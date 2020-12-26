package com.example.kerkar


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item
import kotlinx.android.synthetic.main.activity_home.view.*
import kotlinx.android.synthetic.main.dialog_add_assignment.view.*
import kotlinx.android.synthetic.main.item_home_assignment_info.view.*


class HomeActivity_fragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?{
        val view = inflater.inflate(R.layout.activity_home, container, false)
        
        val frame_context = getContext()



        val main_assignment_adapter = GroupAdapter<GroupieViewHolder>()
        main_assignment_adapter.add(MainAssignmentItem())
        main_assignment_adapter.add(MainAssignmentItem())
        main_assignment_adapter.add(MainAssignmentItem())
        main_assignment_adapter.add(MainAssignmentItem())
        main_assignment_adapter.add(MainAssignmentItem())
        main_assignment_adapter.add(MainAssignmentItem())
        main_assignment_adapter.add(MainAssignmentItem())
        main_assignment_adapter.add(MainAssignmentItem())
        main_assignment_adapter.add(MainAssignmentItem())
        main_assignment_adapter.add(MainAssignmentItem())
        main_assignment_adapter.add(MainAssignmentItem())
        main_assignment_adapter.add(MainAssignmentItem())
        main_assignment_adapter.add(MainAssignmentItem())
        main_assignment_adapter.add(MainAssignmentItem())
        main_assignment_adapter.add(MainAssignmentItem())
        main_assignment_adapter.add(MainAssignmentItem())
        main_assignment_adapter.add(MainAssignmentItem())
        main_assignment_adapter.add(MainAssignmentItem())
        main_assignment_adapter.add(MainAssignmentItem())
        main_assignment_adapter.add(MainAssignmentItem())
        view.main_assignment_info_recyclerview.adapter = main_assignment_adapter



        main_assignment_adapter.setOnItemClickListener { item, view ->
            var str = "期限: 12/25\\n科目: 情報倫理\\n詳細: 小課題"
            str = str.replace("\\n", "\n")
            AlertDialog.Builder(frame_context!!)
                    .setTitle("課題")
                    .setMessage(str)
                    .setPositiveButton("OK", {dialog, which ->

                    })
                    .show()
        }


        view.floatingActionButton.setOnClickListener {
            val mdialogView = LayoutInflater.from(frame_context).inflate(R.layout.dialog_add_assignment, null)
            val mBilder = AlertDialog.Builder(frame_context!!)
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
        return view
    }


}

class MainAssignmentItem: Item<GroupieViewHolder>(){
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        //各レポートの内容をいじる
        //下のはサンプル
        val tmp = viewHolder.itemView.main_activity_info_title_textview.text

    }
    override fun getLayout(): Int {
        return R.layout.item_home_assignment_info
    }
}

class today_timetable_Item: Item<GroupieViewHolder>(){
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
    }
    override fun getLayout(): Int {
        return R.layout.today_taimetable
    }
}