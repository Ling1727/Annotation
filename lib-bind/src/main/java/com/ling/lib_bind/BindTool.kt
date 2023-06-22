package com.ling.lib_bind

import android.app.Activity
import androidx.fragment.app.Fragment

object BindTool {
    fun bind(activity: Activity) {
        activity.javaClass.canonicalName?.let {
            Class.forName("${it}LingMyBinding").getConstructor(activity.javaClass).newInstance(activity)
        }
    }

    fun bind(fragment: Fragment) {
        fragment.javaClass.canonicalName?.let {
            Class.forName(it).newInstance()
        }
    }
}