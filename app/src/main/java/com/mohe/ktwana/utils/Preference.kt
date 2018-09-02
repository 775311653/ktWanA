package com.mohe.ktwana.utils

import com.blankj.utilcode.util.SPUtils
import kotlin.reflect.KProperty

/**
 * Created by xiePing on 2018/9/1 0001.
 * Description:kotlin委托属性+SharedPreference实例
 */
class Preference<T> (val key:String,val defaultValue: T){

    operator fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return getSpValue(key,defaultValue)
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, t1: T) {
        setSpValut(key,t1)
    }

    private fun setSpValut(key: String, value: T) {
        SPUtils.getInstance().run {
            when(value){
                is String -> put(key,value)
                is Long ->put(key,value)
                is Int ->put(key,value)
                is Boolean ->put(key,value)
                is Float ->put(key,value)
            }
        }
    }

    @Suppress("UNCHECKED_CAST")
    private fun getSpValue(key: String, defaultValue: T): T {
        SPUtils.getInstance().run {
            val res:Any?=when(defaultValue){
                is String -> getString(key)
                is Long ->getLong(key)
                is Int ->getInt(key)
                is Boolean ->getBoolean(key)
                is Float ->getFloat(key)
                else ->null
            }
            return res as T
        }

    }


}