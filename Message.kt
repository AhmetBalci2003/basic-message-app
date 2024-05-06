package com.example.message_app

import android.os.Build
import androidx.annotation.RequiresApi

data class Message @RequiresApi(Build.VERSION_CODES.O) constructor(
    var gonderen:String?=null, var mesaj:String?=null,
    var zaman: com.google.firebase.Timestamp?=null
){

}

