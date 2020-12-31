package com.example.kerkar

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class User_data_class (var e_mail: String,  var password: String, var u_id: String, var college: String): Parcelable {
        constructor(): this("", "", "", "")
        }