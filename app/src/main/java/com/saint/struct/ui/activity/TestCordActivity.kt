package com.saint.struct.ui.activity

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.saint.struct.adapter.CordTypeAdapter
import com.saint.struct.adapter.base.BaseViewHolder
import com.saint.struct.adapter.base.interfaces.OnItemClickListener
import com.saint.struct.databinding.ActivityCoordTest4Binding

class TestCordActivity: BaseActivity<ActivityCoordTest4Binding>() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val list: MutableList<String> = mutableListOf()
        for (i in 0..19) {
            list.add((i + 1).toString() + "")
        }
        val adapter = CordTypeAdapter(this, list, false)
        adapter.setOnItemClickListener(object: OnItemClickListener<String>{
            override fun onItemClick(
                baseViewHolder: BaseViewHolder?,
                data: String?,
                position: Int
            ) {

            }

        })
        val layoutManager = LinearLayoutManager(this)
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL)
        binding.commentList.setLayoutManager(layoutManager)
        binding.commentList.setAdapter(adapter)
    }

    override fun getViewBinding(): ActivityCoordTest4Binding {
       return ActivityCoordTest4Binding.inflate(layoutInflater)
    }
}