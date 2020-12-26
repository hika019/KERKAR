package com.example.kerkar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

//        view.include2.timetable_title_textView.text = "国語"

        return view
    }
}