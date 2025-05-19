package com.saint.struct.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.saint.struct.R

class HeaderAndFooterAdapter(private val retry: () -> Unit) :
    LoadStateAdapter<HeaderAndFooterAdapter.LoadStateViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder {
        Log.e("HeaderAndFooterAdapter", "onCreateViewHolder" )
        return when (loadState) {
            is LoadState.Error -> {
                Log.e("HeaderAndFooterAdapter", "ErrorViewHolder" )
                return ErrorViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_error, parent, false), retry
                )
            }
            else -> {
                Log.e("HeaderAndFooterAdapter", "LoadStateAdapter" )
                return LoadingViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_loading, parent, false)
                )
            }
        }
    }

    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {
        when (holder) {
            is ErrorViewHolder -> holder.bind(loadState as LoadState.Error)
            is LoadingViewHolder -> holder.bind(loadState)
        }
    }

    abstract class LoadStateViewHolder(view: View) : RecyclerView.ViewHolder(view)

    class LoadingViewHolder(view: View) : LoadStateViewHolder(view) {
        private val progressBar: ProgressBar = view.findViewById(R.id.progress_bar)

        fun bind(loadState: LoadState) {
            progressBar.isVisible = loadState is LoadState.Loading
        }
    }

    class ErrorViewHolder(view: View, retry: () -> Unit) : LoadStateViewHolder(view) {
        private val errorMsg: TextView = view.findViewById(R.id.error_msg)
        private val retryButton: Button = view.findViewById<Button>(R.id.retry_button).also {
            it.setOnClickListener { retry() }
        }

        fun bind(loadState: LoadState.Error) {
            errorMsg.text = loadState.error.localizedMessage
        }
    }
}
