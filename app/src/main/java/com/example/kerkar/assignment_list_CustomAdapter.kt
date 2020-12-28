package com.example.kerkar

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_assignment_activity.view.*


class assignment_list_CustomAdapter(private val lecture_List: ArrayList<String>, private val context: Context?)
    : RecyclerView.Adapter<assignment_list_CustomAdapter.CustomViewHolder>() {

    lateinit var listener: OnItemClickListener

    class CustomViewHolder(val view: View): RecyclerView.ViewHolder(view) {
        val day = view.assignment_activity_info_day_textview
        val lecture_title = view.assignment_activity_info_title_textview
        val assignment_details = view.assignment_activity_info_details_textview
    }

    // getItemCount onCreateViewHolder onBindViewHolderを実装
    // 上記のViewHolderクラスを使ってViewHolderを作成
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        Log.d("assignment_list_adapter", "1")
        val layoutInflater = LayoutInflater.from(parent.context)
        val item = layoutInflater.inflate(R.layout.item_assignment_activity, parent, false)
        return CustomViewHolder(item)
    }

    override fun getItemCount(): Int {
        Log.d("assignment_list_adapter", "2")
        return lecture_List.size
    }

    //ここで挿入
    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        Log.d("assignment_list_adapter", "3")
        holder.lecture_title.text = lecture_List[position]
        //タップ
        holder.view.setOnClickListener {
            val assignment_dialog_class = assignment_dialog_class()
            Log.d("AssignmentActivity", "select assignment item: $position")

//            assignment_dialog_class.assohmenment_ditail_dialog(context)
        }
    }

    //インターフェースの作成
    interface OnItemClickListener{
        fun onItemClickListener(view: View, position: Int, clickedText: String)
    }

    // リスナー
    fun setOnItemClickListener(listener: OnItemClickListener){
        this.listener = listener
    }

}