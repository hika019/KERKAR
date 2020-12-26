package com.example.kerkar

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_timetable.*
import kotlinx.android.synthetic.main.activity_timetable.view.*
import kotlinx.android.synthetic.main.item_taimetable.view.*

class Timetable_flagment : Fragment(){
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?{
        val view = inflater.inflate(R.layout.activity_timetable, container, false)
        val this_context = getContext()

        view.timetable_include_mon3.timetable_title_textView.text = "国語"

        if (this_context != null) {
            timetable_onclick_event(view, this_context)
        }


        return view
    }

    private fun timetable_onclick_event(view: View, context: Context){
        //月
        view.timetable_include_mon1.setOnClickListener {
            timetable_dialog("mon1", context)
        }
        view.timetable_include_mon2.setOnClickListener {
            timetable_dialog("mon2", context)
        }
        view.timetable_include_mon3.setOnClickListener {
            timetable_dialog("mon3", context)
        }
        view.timetable_include_mon4.setOnClickListener {
            timetable_dialog("mon4", context)
        }
        view.timetable_include_mon5.setOnClickListener {
            timetable_dialog("mon5", context)
        }
        //火
        view.timetable_include_tue1.setOnClickListener {
            timetable_dialog("tue1", context)
        }
        view.timetable_include_tue2.setOnClickListener {
            timetable_dialog("tue2", context)
        }
        view.timetable_include_tue3.setOnClickListener {
            timetable_dialog("tue3", context)
        }
        view.timetable_include_tue4.setOnClickListener {
            timetable_dialog("tue4", context)
        }
        view.timetable_include_tue5.setOnClickListener {
            timetable_dialog("tue5", context)
        }
        //水
        view.timetable_include_wen1.setOnClickListener {
            timetable_dialog("wen1", context)
        }
        view.timetable_include_wen2.setOnClickListener {
            timetable_dialog("wen2", context)
        }
        view.timetable_include_wen3.setOnClickListener {
            timetable_dialog("wen3", context)
        }
        view.timetable_include_wen4.setOnClickListener {
            timetable_dialog("wen4", context)
        }
        view.timetable_include_wen5.setOnClickListener {
            timetable_dialog("wen5", context)
        }
    }





    private fun timetable_dialog(time:String, context: Context){
        var message = get_timetable_details(time)
        message = message.replace("\\n", "\n")
        AlertDialog.Builder(context)
            .setTitle("授業")
            .setMessage(message)
            .setPositiveButton("OK") { dialog, which ->

            }
            .setNegativeButton("授業登録"){dialog, which ->

            }
            .show()
    }

    private fun get_timetable_details(time: String): String {
        //str = fun 授業詳細を取得(time)
        var str = "教科:情報倫理\\n教室:951\\n教員:中川　太郎"//一時的
        if(str.isEmpty()){
            str = "授業が登録されていません"
        }
        return str
    }

}