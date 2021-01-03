package com.example.kerkar.login_and_register

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kerkar.R

import com.example.kerkar.main_activity
import kotlinx.android.synthetic.main.activity_select_university.*
import kotlinx.android.synthetic.main.item_serch_university.view.*

class SelectUniversityActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_university)

        val list = arrayListOf("中部大学", "中京大学", "名古屋大学")

        val adapter = select_university_CustomAdapter(list, this)
        val layoutManager = LinearLayoutManager(this)

        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))


        confirm_button.setOnClickListener{
            val intent = Intent(this, main_activity::class.java)
            startActivity(intent)
        }
        add_university_button.setOnClickListener{

        }




    }

}

class select_university_CustomAdapter(private val univarsity_list: ArrayList<String>, private val context: Context)
    : RecyclerView.Adapter<select_university_CustomAdapter.CustomViewHolder>() {
    class CustomViewHolder(val view: View): RecyclerView.ViewHolder(view) {
        val university_name = view.university_name
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val item = layoutInflater.inflate(R.layout.item_serch_university, parent, false)
        return select_university_CustomAdapter.CustomViewHolder(item)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.university_name.text = univarsity_list[position]

        holder.view.setOnClickListener {
            Toast.makeText(context, univarsity_list[position], Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int {
        return univarsity_list.size
    }

}
