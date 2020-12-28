package com.example.kerkar


import android.content.Context
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item
import kotlinx.android.synthetic.main.activity_assignment_list.view.*
import kotlinx.android.synthetic.main.item_assignment_activity.view.*


class Assignment_list_fragment() :Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?{
        val view = inflater.inflate(R.layout.activity_assignment_list, container, false)
        val assignmentSwith=assignment_swith()

        val frame_context = getContext()
        Log.d("hoge", "this:"+ frame_context.toString())


        //未提出list
        var lecture_titel_list = arrayListOf("哲学", "英語", "創造理工実験", "現代社会経済", "データベース", "オブジェクト指向言語")
        list(view, lecture_titel_list, frame_context)


        //提出済み,未提出の切り替え
        view.unsubmitted_or_submitted_switch.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked){
                //提出済み
                view.submitted_textview.setTypeface(Typeface.DEFAULT_BOLD)
                view.unsubmitted_textview.setTypeface(Typeface.DEFAULT)
                assignmentSwith.flag=0

//                Toast.makeText(frame_context!!, "true", Toast.LENGTH_SHORT).show()
                //未提出list取得
                lecture_titel_list = arrayListOf("環境論", "現代社会経済", "カーネル", "オブジェクト指向言語", "英語", "生命倫理学",)
                list(view, lecture_titel_list, frame_context)

            }else{
                //未提出
                view.unsubmitted_textview.setTypeface(Typeface.DEFAULT_BOLD)
                view.submitted_textview.setTypeface(Typeface.DEFAULT)
//                Toast.makeText(frame_context!!, "false", Toast.LENGTH_SHORT).show()
                assignmentSwith.flag=1

                //提出list取得
                lecture_titel_list = arrayListOf("哲学", "英語", "創造理工実験", "現代社会経済", "データベース", "オブジェクト指向言語")
                list(view, lecture_titel_list, frame_context)
            }
        }

        Log.d("AssignmentActivity", lecture_titel_list.toString())



        return view
    }

    private fun list(view: View, list: ArrayList<String>, context: Context?){
        val recyclerView = view.AssignmentActivity_assignment_recyclerView


        val adapter = assignment_list_CustomAdapter(list, context)
        val layoutManager = LinearLayoutManager(context)

        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
        recyclerView.setHasFixedSize(true)

//        recyclerView.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))

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
        return R.layout.item_assignment_activity
    }
}