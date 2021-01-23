package com.example.kerkar.assignment_list


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.kerkar.*
import com.google.android.material.tabs.TabLayout
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item
import kotlinx.android.synthetic.main.activity_assignment_list.view.*
import kotlinx.android.synthetic.main.item_assignment_activity.view.*



class Assignment_list_fragment() :Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?{
        val view = inflater.inflate(R.layout.activity_assignment_list, container, false)
        val assignmentSwith= assignment_swith()

        val frame_context = getContext()

        //未提出list
//        var unsubmitted_lecture_titel_list = arrayListOf("哲学", "英語", "創造理工実験", "現代社会経済", "データベース", "オブジェクト指向言語",
//                "国語", "C言語", "流体力学", "電磁気学")
//        var submitted_lecture_titel_list: ArrayList<String> = ArrayList()
//
//        val list = ItemList(submitted_lecture_titel_list, unsubmitted_lecture_titel_list)
//
//        val submmitted_list = list.submitted_list
//        val unsubmmitted_list = list.unsubmitted_list
//
//        list(view, unsubmmitted_list, submmitted_list, context)
        view.AssignmentActivity_assignment_recyclerView.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))

        firedb_load_task_class(frame_context!!).assignment_get_notcomp_task(view)

        //提出済み,未提出の切り替え
        view.unsubmitted_or_submitted_tabs.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
//                Toast.makeText(context, tab?.text, Toast.LENGTH_SHORT).show()
                if(tab?.text == "提出済課題") {
                    assignmentSwith.flag=0
//                    list(view,  submmitted_list, unsubmmitted_list, frame_context)
                    firedb_load_task_class(frame_context!!).assignment_get_comp_task(view)
                }
                else {
                    assignmentSwith.flag=1
//                    list(view, unsubmmitted_list, submmitted_list, frame_context)
                    firedb_load_task_class(frame_context!!).assignment_get_notcomp_task(view)
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })

//        Log.d("AssignmentActivity", submmitted_list.toString())




        return view
    }

//    private fun list(view: View, read_list: ArrayList<String>, write_list: ArrayList<String>, context: Context?){
//        val recyclerView = view.AssignmentActivity_assignment_recyclerView
//
//
//        val adapter = assignment_notcmp_list_CustomAdapter(read_list, write_list, context)
//        val layoutManager = LinearLayoutManager(context)
//
//        recyclerView.layoutManager = layoutManager
//        recyclerView.adapter = adapter
//        recyclerView.setHasFixedSize(true)
//
//    }

}


class TaskListItem(private val day: String,
                    private val class_name: String,
                    private val task_title: String) : Item<GroupieViewHolder>() {

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.itemView.assignment_activity_info_day_textview.text = day
        viewHolder.itemView.assignment_activity_info_title_textview.text = class_name
        viewHolder.itemView.assignment_activity_info_details_textview.text = task_title
    }

    override fun getLayout(): Int{
        return R.layout.item_assignment_activity
    }


}