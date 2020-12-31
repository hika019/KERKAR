package com.example.kerkar

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class User_data_class (val e_mail: String, val u_id: String, val college: String): Parcelable {
        constructor(): this("", "", "")
        }