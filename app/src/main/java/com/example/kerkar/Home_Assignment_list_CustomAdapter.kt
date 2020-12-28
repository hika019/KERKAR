package com.example.kerkar

import android.content.ClipData
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_home_assignment_info.view.*


class Home_Assignment_list_CustomAdapter(private val teache_List: ArrayList<String>, private val context: Context)
    : RecyclerView.Adapter<Home_Assignment_list_CustomAdapter.CustomViewHolder>() {

    lateinit var listener: OnItemClickListener

    class CustomViewHolder(val view: View): RecyclerView.ViewHolder(view) {
        val day = view.item_homeactivity_assignment_day_textview
        val lecture_title = view.item_homeactivity_assignment_title_textview
        val assignment_details = view.item_homeactivity_assignment_details_textview
    }

    // getItemCount onCreateViewHolder onBindViewHolderを実装
    // 上記のViewHolderクラスを使ってViewHolderを作成
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val item = layoutInflater.inflate(R.layout.item_home_assignment_info, parent, false)

        item.setOnClickListener { view ->
            Log.d("hoge", "click")

        }

        return CustomViewHolder(item)
    }

    override fun getItemCount(): Int {
        return teache_List.size
    }

    //ここで挿入
    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.lecture_title.text = teache_List[position]
        //タップ
        holder.view.setOnClickListener {
            val assignment_dialog_class = assignment_dialog_class()
            Log.d("HomeActivity", "select assignment item: $position")

            //表示する内容
            val id = "unique_id"
            val str = "期限: 12/25\n科目: 情報倫理\n詳細: 小課題\n$position"
            assigmenment_ditail_dialog(context, str, position)

            //消す
            //removeItem(position)
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

    // Itemを追加する
    fun addListItem (item: ClipData.Item) {
        teache_List.add(item.toString())
        notifyDataSetChanged() // これを忘れるとRecyclerViewにItemが反映されない
    }

    // Itemを削除する
    private fun removeItem(position: Int) {
        teache_List.removeAt(position)
        notifyItemRemoved(position)
        notifyDataSetChanged() // これを忘れるとRecyclerViewにItemが反映されない
    }


    private fun assigmenment_ditail_dialog(context: Context, str:String, position: Int){


        AlertDialog.Builder(context)
                .setTitle("課題")
                .setMessage(str)
                .setPositiveButton("OK") { dialog, which ->

                }
                .setNeutralButton("提出済みにする") {dialog, which ->
                    Log.d("Assignment", "$position　を提出済みにする")
                    removeItem(position)
                }
                .show()
    }

}