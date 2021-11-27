package com.orbital.core.utils

class StringUtils {
    companion object{
        fun removePlusToSpace(value:String):String{
            return value.replace("+"," ")
        }
    }
}