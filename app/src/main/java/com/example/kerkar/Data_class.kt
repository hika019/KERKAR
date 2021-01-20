package com.example.kerkar

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Data_class (var e_mail: String, var password: String, var u_id: String, var college: String): Parcelable {
        constructor(): this("", "", "", "")
        }

class task_data_class(var id: String, var week_to_day: String, var course: String, var task: Map<String, String>)