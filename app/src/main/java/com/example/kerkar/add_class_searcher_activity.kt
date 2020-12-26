package com.example.kerkar

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item
import kotlinx.android.synthetic.main.dialog_add_class_searcher.view.*

class add_class_searcher_fragment: Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.dialog_add_class_searcher, container, false)
        val this_context = getContext()

        val add_class_search_adaper = GroupAdapter<GroupieViewHolder>()
        add_class_search_adaper.add(add_class_search_Item())
        add_class_search_adaper.add(add_class_search_Item())
        add_class_search_adaper.add(add_class_search_Item())
        add_class_search_adaper.add(add_class_search_Item())
        add_class_search_adaper.add(add_class_search_Item())
        add_class_search_adaper.add(add_class_search_Item())
        add_class_search_adaper.add(add_class_search_Item())
        add_class_search_adaper.add(add_class_search_Item())
        add_class_search_adaper.add(add_class_search_Item())
        add_class_search_adaper.add(add_class_search_Item())
        add_class_search_adaper.add(add_class_search_Item())
        add_class_search_adaper.add(add_class_search_Item())
        view.dialog_add_class_serchaer_recycleview.adapter = add_class_search_adaper

        add_class_search_adaper.setOnItemClickListener { item, view ->
            Toast.makeText(this_context, "選択された", Toast.LENGTH_SHORT).show()
        }

        view.search_button.setOnClickListener{
            Log.d("add_class_search", "serch_button pressed")
        }

        return view
    }
}


class add_class_search_Item: Item<GroupieViewHolder>(){
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        //各レポートの内容をいじる
        //下のはサンプル
//        val tmp = viewHolder.itemView.main_activity_info_title_textview.text

    }
    override fun getLayout(): Int {
        return R.layout.item_add_class_searcher
    }
}
