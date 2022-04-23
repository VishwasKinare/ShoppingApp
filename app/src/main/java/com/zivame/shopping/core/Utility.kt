package com.zivame.shopping.core

import android.content.Context

object Utility {
    fun Context.getMessage(msg: Any?): String{
        return when(msg){
            null -> {
                ""
            }
            is Int ->{
                getString(msg)
            }
            else ->{
                msg.toString()
            }
        }
    }
}