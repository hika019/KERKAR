package com.example.kerkar

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_timetable.view.*
import kotlinx.android.synthetic.main.item_taimetable.view.*

class Timetable_flagment : Fragment(){
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?{
        val view = inflater.inflate(R.layout.activity_timetable, container, false)
        val this_context = getContext()

        view.timetable_include_mon3.timetable_title_textView.text = "国語"

        timetable_onclick_event(view, this_context)


        return view
    }

    private fun timetable_onclick_event(view: View, context: Context?){
        val timetable_dialog_class = timetable_dialog_class()

        //月
        view.timetable_include_mon1.setOnClickListener {
            timetable_dialog_class.timetable_dialog("mon1", context)
        }
        view.timetable_include_mon2.setOnClickListener {
            timetable_dialog_class.timetable_dialog("mon2", context)
        }
        view.timetable_include_mon3.setOnClickListener {
            timetable_dialog_class.timetable_dialog("mon3", context)
        }
        view.timetable_include_mon4.setOnClickListener {
            timetable_dialog_class.timetable_dialog("mon4", context)
        }
        view.timetable_include_mon5.setOnClickListener {
            timetable_dialog_class.timetable_dialog("mon5", context)
        }
        //火
        view.timetable_include_tue1.setOnClickListener {
            timetable_dialog_class.timetable_dialog("tue1", context)
        }
        view.timetable_include_tue2.setOnClickListener {
            timetable_dialog_class.timetable_dialog("tue2", context)
        }
        view.timetable_include_tue3.setOnClickListener {
            timetable_dialog_class.timetable_dialog("tue3", context)
        }
        view.timetable_include_tue4.setOnClickListener {
            timetable_dialog_class.timetable_dialog("tue4", context)
        }
        view.timetable_include_tue5.setOnClickListener {
            timetable_dialog_class.timetable_dialog("tue5", context)
        }
        //水
        view.timetable_include_wen1.setOnClickListener {
            timetable_dialog_class.timetable_dialog("wen1", context)
        }
        view.timetable_include_wen2.setOnClickListener {
            timetable_dialog_class.timetable_dialog("wen2", context)
        }
        view.timetable_include_wen3.setOnClickListener {
            timetable_dialog_class.timetable_dialog("wen3", context)
        }
        view.timetable_include_wen4.setOnClickListener {
            timetable_dialog_class.timetable_dialog("wen4", context)
        }
        view.timetable_include_wen5.setOnClickListener {
            timetable_dialog_class.timetable_dialog("wen5", context)
        }
    }







}