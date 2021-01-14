package com.example.kerkar.home


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kerkar.*
import kotlinx.android.synthetic.main.activity_home.view.*
import java.util.*

val TAG = "home"

class Home_fragment() : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?{
        val view = inflater.inflate(R.layout.activity_home, container, false)
        val this_context = getContext()
        today_class(view, this_context!!)

        val teacher_list = arrayListOf("哲学", "英語", "創造理工実験", "現代社会経済", "データベース", "オブジェクト指向言語")

        val adapter = Home_Assignment_list_CustomAdapter(teacher_list, this_context)
        val layoutManager = LinearLayoutManager(this_context)

        view.main_assignment_info_recyclerview.layoutManager = layoutManager
        view.main_assignment_info_recyclerview.adapter = adapter
        view.main_assignment_info_recyclerview.setHasFixedSize(true)


        //時間の更新&取得
        get_timetable_list(this_context!!, view, 0)



        view.floatingActionButton.setOnClickListener {
//            fab(this_context)
            val add_task_class = assignment_dialog_class(this_context)
            add_task_class.start()
//            show_course_list(this_context)


        }

        return view
    }



    private fun today_class(view: View, context: Context) {
        val week_name = arrayOf("sun", "mon", "tue", "wen", "thu", "fri", "sat")
        val calendar: Calendar = Calendar.getInstance()
        val week = week_name[calendar.get(Calendar.DAY_OF_WEEK)-1]
        //Log.d("week", week_name[calendar.get(Calendar.DAY_OF_WEEK)-1])


        val timetable_dialog_class = timetable_dialog_class()
        view.today_first_period.setOnClickListener {
            timetable_dialog_class.timetable_dialog_rapper(week, 1, context)
        }
        view.today_second_period.setOnClickListener {
            timetable_dialog_class.timetable_dialog_rapper(week, 2, context)
        }
        view.today_third_period.setOnClickListener {
            timetable_dialog_class.timetable_dialog_rapper(week, 3, context)
        }
        view.today_fourth_period.setOnClickListener {
            timetable_dialog_class.timetable_dialog_rapper(week, 4, context)
        }
        view.today_fifth_period.setOnClickListener {
            timetable_dialog_class.timetable_dialog_rapper(week, 5, context)
        }
    }

}
