package com.example.kerkar.assignment_list


import android.content.Context
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kerkar.ItemList
import com.example.kerkar.R
import com.example.kerkar.assignment_swith
import kotlinx.android.synthetic.main.activity_assignment_list.view.*


class Assignment_list_fragment() :Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?{
        val view = inflater.inflate(R.layout.activity_assignment_list, container, false)
        val assignmentSwith= assignment_swith()

        val frame_context = getContext()
        Log.d("hoge", "this:"+ frame_context.toString())


        //未提出list
        var unsubmitted_lecture_titel_list = arrayListOf("哲学", "英語", "創造理工実験", "現代社会経済", "データベース", "オブジェクト指向言語",
                "国語", "C言語", "流体力学", "電磁気学")
        var submitted_lecture_titel_list: ArrayList<String> = ArrayList()

        val list = ItemList(submitted_lecture_titel_list, unsubmitted_lecture_titel_list)

        val submmitted_list = list.submitted_list
        val unsubmmitted_list = list.unsubmitted_list
        val switch = view.unsubmitted_or_submitted_switch

        list(view, unsubmmitted_list, submmitted_list, context)

        view.AssignmentActivity_assignment_recyclerView.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))

        //提出済み,未提出の切り替え
        view.unsubmitted_or_submitted_switch_cover.setOnClickListener {
            if(switch.isChecked){
                switch.setChecked(false)
            }else{
                switch.setChecked(true)
            }

        }
        switch.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked){
                //提出済み
                view.submitted_textview.setTypeface(Typeface.DEFAULT_BOLD)
                view.unsubmitted_textview.setTypeface(Typeface.DEFAULT)
                assignmentSwith.flag=0

//                Toast.makeText(frame_context!!, "true", Toast.LENGTH_SHORT).show()
                //未提出list取得
//                lecture_titel_list = arrayListOf("環境論", "現代社会経済", "カーネル", "オブジェクト指向言語", "英語", "生命倫理学",)
                list(view,  submmitted_list, unsubmmitted_list, frame_context)

            }else{
                //未提出
                view.unsubmitted_textview.setTypeface(Typeface.DEFAULT_BOLD)
                view.submitted_textview.setTypeface(Typeface.DEFAULT)
//                Toast.makeText(frame_context!!, "false", Toast.LENGTH_SHORT).show()
                assignmentSwith.flag=1

                //提出list取得
//                lecture_titel_list = arrayListOf("哲学", "英語", "創造理工実験", "現代社会経済", "データベース", "オブジェクト指向言語")
                list(view, unsubmmitted_list, submmitted_list, frame_context)
            }
        }

        Log.d("AssignmentActivity", submmitted_list.toString())



        return view
    }

    private fun list(view: View, read_list: ArrayList<String>, write_list: ArrayList<String>, context: Context?){
        val recyclerView = view.AssignmentActivity_assignment_recyclerView


        val adapter = assignment_list_CustomAdapter(read_list, write_list, context)
        val layoutManager = LinearLayoutManager(context)

        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
        recyclerView.setHasFixedSize(true)



    }

}