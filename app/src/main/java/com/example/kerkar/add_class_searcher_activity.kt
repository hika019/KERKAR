package com.example.kerkar

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.dialog_add_class_searcher.view.*

class add_class_searcher_fragment: Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.dialog_add_class_searcher, container, false)
        val this_context = getContext()


        val list = Array<String>(10) {"テキスト$it"}
        val adapter = add_class_search_CustomAdapter(list)
        val layoutManager = LinearLayoutManager(this_context)

        Log.d("adapter:", adapter.toString())
        Log.d("layoutM:", layoutManager.toString())


        view.dialog_add_class_serchaer_recycleview.layoutManager = layoutManager

        view.dialog_add_class_serchaer_recycleview.adapter = adapter
        view.dialog_add_class_serchaer_recycleview.setHasFixedSize(true)



        view.search_button.setOnClickListener{
            Log.d("add_class_search", "serch_button pressed")
        }

        return view
    }
}



