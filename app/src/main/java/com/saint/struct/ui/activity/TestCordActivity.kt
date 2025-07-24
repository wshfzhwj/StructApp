package com.saint.struct.ui.activity

import android.os.Bundle
import android.view.LayoutInflater
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.saint.struct.R
import com.saint.struct.adapter.CordTypeAdapter
import com.saint.struct.adapter.base.BaseViewHolder
import com.saint.struct.adapter.base.interfaces.OnItemClickListener
import com.saint.struct.databinding.ActivityCoordTest4Binding

class TestCordActivity: BaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(ActivityCoordTest4Binding.inflate(layoutInflater).root)
        setContentView(R.layout.activity_coord_test4)

        val commentList: RecyclerView = findViewById(R.id.comment_list)
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
        commentList.setLayoutManager(layoutManager)
        commentList.setAdapter(adapter)
    }
}