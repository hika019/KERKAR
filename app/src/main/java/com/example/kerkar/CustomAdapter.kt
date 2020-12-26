package com.example.kerkar

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_add_class_searcher.view.*

class add_class_search_CustomAdapter(private val customList: Array<String>): RecyclerView.Adapter<add_class_search_CustomAdapter.CustomViewHolder>() {

    class CustomViewHolder(val view: View): RecyclerView.ViewHolder(view) {
        val week_to_day = view.item_class_search_week_to_day
        val lecture_title = view.item_class_search_lecture_title
        val teacher_name = view.item_class_search_teacher_name
    }

    // getItemCount onCreateViewHolder onBindViewHolderを実装
    // 上記のViewHolderクラスを使ってViewHolderを作成
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {

        val layoutInflater = LayoutInflater.from(parent.context)
        val item = layoutInflater.inflate(R.layout.item_add_class_searcher, parent, false)
        return CustomViewHolder(item)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.view.item_class_search_teacher_name.text = customList[position]
    }

    override fun getItemCount(): Int {
        return customList.size
    }

}