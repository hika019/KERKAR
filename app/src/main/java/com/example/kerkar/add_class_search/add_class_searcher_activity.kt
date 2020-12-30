package com.example.kerkar.add_class_search

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kerkar.R
import kotlinx.android.synthetic.main.dialog_add_class_searcher.*

class add_class_searcher_activity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_add_class_searcher)


        val teache_list = arrayListOf<String>("山田", "山本", "鈴木", "田中")
        val lecture_list = Array<String>(10) {"テキスト$it"}//list
        val adapter = add_class_search_CustomAdapter(teache_list)
        val layoutManager = LinearLayoutManager(this)


        dialog_add_class_serchaer_recycleview.layoutManager = layoutManager
        dialog_add_class_serchaer_recycleview.adapter = adapter
        dialog_add_class_serchaer_recycleview.setHasFixedSize(true)

        //線表示
        dialog_add_class_serchaer_recycleview.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))

        adapter.setOnItemClickListener(object: add_class_search_CustomAdapter.OnItemClickListener {
            override fun onItemClickListener(view: View, position: Int, clickedText: String) {
                Toast.makeText(applicationContext, "${clickedText}がタップされました", Toast.LENGTH_SHORT).show()
            }
        })

        search_button.setOnClickListener{
            Log.d("add_class_search", "serch_button pressed")
        }



    }
}



