package com.saint.struct.adapter.base

import android.content.Context
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.core.view.size

class BaseViewHolder private constructor(val convertView: View) : RecyclerView.ViewHolder(
    convertView
) {
    private val mViews: SparseArray<View> = SparseArray<View>()

    /**
     * 通过id获得控件
     *
     * @param viewId
     * @param <T>
     * @return
    </T> */
    fun <T : View> getView(viewId: Int): T {
        var view = mViews.get(viewId)
        if (view == null) {
            view = convertView.findViewById<View>(viewId)
            mViews.put(viewId, view)
        }
        return view as T
    }

    val swipeView: View?
        get() {
            val itemLayout: ViewGroup = (this.convertView as ViewGroup)
            if (itemLayout.size == 2) {
                return itemLayout.getChildAt(1)
            }
            return null
        }

    fun setText(viewId: Int, text: String?) {
        val textView: TextView = getView<TextView>(viewId)
        textView.text = text
    }

    fun setText(viewId: Int, textId: Int) {
        val textView: TextView = getView<TextView>(viewId)
        textView.setText(textId)
    }

    fun setTextColor(viewId: Int, colorId: Int) {
        val textView: TextView = getView<TextView>(viewId)
        textView.setTextColor(colorId)
    }

    fun setOnClickListener(viewId: Int, clickListener: View.OnClickListener?) {
        val view = getView<View>(viewId)
        view.setOnClickListener(clickListener)
    }

    fun setBgRes(viewId: Int, resId: Int) {
        val view = getView<View>(viewId)
        view.setBackgroundResource(resId)
    }

    fun setBgColor(viewId: Int, colorId: Int) {
        val view = getView<View>(viewId)
        view.setBackgroundColor(colorId)
    }

    companion object {
        fun create(context: Context, layoutId: Int, parent: ViewGroup): BaseViewHolder {
            val itemView: View = LayoutInflater.from(context).inflate(layoutId, parent, false)
            return BaseViewHolder(itemView)
        }

        fun create(itemView: View): BaseViewHolder {
            return BaseViewHolder(itemView)
        }
    }
}
