package com.example.kerkar


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item
import kotlinx.android.synthetic.main.activity_home.view.*
import kotlinx.android.synthetic.main.dialog_add_assignment.view.*
import kotlinx.android.synthetic.main.dialog_add_class_editer.view.*
import kotlinx.android.synthetic.main.item_home_assignment_info.view.*
import java.util.*


class HomeActivity_fragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?{
        val view = inflater.inflate(R.layout.activity_home, container, false)
        val this_context = getContext()

        today_class(view, this_context!!)



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
        view.main_assignment_info_recyclerview.adapter = main_assignment_adapter

        main_assignment_adapter.setOnItemClickListener { item, view ->
            var str = "期限: 12/25\\n科目: 情報倫理\\n詳細: 小課題"
            str = str.replace("\\n", "\n")
            AlertDialog.Builder(this_context!!)
                    .setTitle("課題")
                    .setMessage(str)
                    .setPositiveButton("OK", { dialog, which ->

                    })
                    .show()
        }

        view.floatingActionButton.setOnClickListener {
            val mdialogView = LayoutInflater.from(this_context).inflate(
                R.layout.dialog_add_assignment,
                null
            )
            val mBilder = AlertDialog.Builder(this_context!!)
                .setView(mdialogView)
                .setTitle("課題追加")
                .setPositiveButton("確定") { dialog, which ->
                    var add_assignment = add_assignment(
                        mdialogView.dialog_deadline_day.text.toString(),
                        mdialogView.dialog_deadline_time.text.toString(),
                        mdialogView.dialog_subject.text.toString(),
                        mdialogView.dialog_assignment_title.text.toString(),
                        mdialogView.dialog_assignment_special_notes.text.toString()
                    )
                    Log.d("dialog", add_assignment.day)
                }
                .setNegativeButton("破棄") { dialog, which ->

                }

            mBilder.show()
        }





        return view
    }


    private fun today_class(view: View, context: Context) {
        val week_name = arrayOf("sun", "mon", "tue", "wen", "thu", "fri", "sat")
        val calendar: Calendar = Calendar.getInstance()
        val week = week_name[calendar.get(Calendar.DAY_OF_WEEK)-1]
        //Log.d("week", week_name[calendar.get(Calendar.DAY_OF_WEEK)-1])


        val timetable_dialog_class = timetable_dialog_class()
        view.today_first_period.setOnClickListener {
            timetable_dialog_class.timetable_dialog(week+"1", context)
        }
        view.today_second_period.setOnClickListener {
            timetable_dialog_class.timetable_dialog(week+"2", context)
        }
        view.today_third_period.setOnClickListener {
            timetable_dialog_class.timetable_dialog(week+"3", context)
        }
        view.today_fourth_period.setOnClickListener {
            timetable_dialog_class.timetable_dialog(week+"4", context)
        }
        view.today_fifth_period.setOnClickListener {
            timetable_dialog_class.timetable_dialog(week+"5", context)
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
        return R.layout.item_home_assignment_info
    }
}

class today_timetable_Item: Item<GroupieViewHolder>(){
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
    }
    override fun getLayout(): Int {
        return R.layout.item_taimetable
    }
}