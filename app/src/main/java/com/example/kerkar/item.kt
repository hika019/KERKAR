package com.example.kerkar

import java.util.*

data class Item(
    val mId: String = UUID.randomUUID().toString(),
    val mName:String
)

data class ItemList(
    val submitted_list: ArrayList<String>,
    val unsubmitted_list: ArrayList<String>)