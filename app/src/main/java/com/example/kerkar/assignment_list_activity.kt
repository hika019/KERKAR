package com.example.kerkar


import android.graphics.Typeface
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
import kotlinx.android.synthetic.main.activity_assignment_list.view.*
import kotlinx.android.synthetic.main.assignment_activity_item.view.*


class assignment_list_fragment :Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?{
        val view = inflater.inflate(R.layout.activity_assignment_list, container, false)
        val assignmentSwith=assignment_swith()

        val frame_context = getContext()
        Log.d("hoge", "this:"+ frame_context.toString())


        val main_assignment_adapter = GroupAdapter<GroupieViewHolder>()
        main_assignment_adapter.add(assignment_item())
        main_assignment_adapter.add(assignment_item())
        main_assignment_adapter.add(assignment_item())
        main_assignment_adapter.add(assignment_item())
        main_assignment_adapter.add(assignment_item())
        main_assignment_adapter.add(assignment_item())
        main_assignment_adapter.add(assignment_item())
        main_assignment_adapter.add(assignment_item())
        main_assignment_adapter.add(assignment_item())
        main_assignment_adapter.add(assignment_item())
        main_assignment_adapter.add(assignment_item())
        main_assignment_adapter.add(assignment_item())
        view.AssignmentActivity_assignment_recyclerView.adapter = main_assignment_adapter








        //提出済み,未提出の切り替え
        view.unsubmitted_or_submitted_switch.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked){
                view.submitted_textview.setTypeface(Typeface.DEFAULT_BOLD)
                view.unsubmitted_textview.setTypeface(Typeface.DEFAULT)
                assignmentSwith.flag=0
                Toast.makeText(frame_context!!, "true", Toast.LENGTH_SHORT).show()
            }else{
                view.unsubmitted_textview.setTypeface(Typeface.DEFAULT_BOLD)
                view.submitted_textview.setTypeface(Typeface.DEFAULT)
                Toast.makeText(frame_context!!, "false", Toast.LENGTH_SHORT).show()
                assignmentSwith.flag=1
            }
        }


        return view
    }

}
class assignment_item: Item<GroupieViewHolder>() {
    val assignmentSwith=assignment_swith()
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        Log.d("item", "call item")
        if(assignmentSwith.flag == 0){
            val tmp = viewHolder.itemView.assignment_activity_info_title_textview.text
        }
        else{
            viewHolder.itemView.assignment_activity_info_title_textview.text = "英語"
        }


    }

    override fun getLayout(): Int {
        return R.layout.assignment_activity_item
    }
}