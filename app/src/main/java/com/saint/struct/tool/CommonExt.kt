package com.saint.struct.tool

import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.saint.struct.StructApp

/**
 * Toast扩展函数
 */
fun Context.showToast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, duration).show()
}

fun showToast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(StructApp.Companion.application, message, duration).show()
}

fun log(message: String) {
    Log.e("Tag", message)
}

fun View?.visible() {
    if (this?.visibility != View.VISIBLE) {
        this?.visibility = View.VISIBLE
    }
}

fun View?.invisible() {
    if (this?.visibility != View.INVISIBLE) {
        this?.visibility = View.INVISIBLE
    }
}

fun View?.gone() {
    if (this?.visibility != View.GONE) {
        this?.visibility = View.GONE
    }
}

fun Activity.showToast(msg: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, msg, duration).show()
}

fun Activity.showToast(@StringRes msg: Int, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, msg, duration).show()
}

fun Fragment.showToast(msg: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(requireContext(), msg, duration).show()
}

fun Fragment.showToast(@StringRes message: Int, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(requireContext(), message, duration).show()
}



