package com.saint.struct.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.paging.PagingDataAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.saint.struct.R;
import com.saint.struct.bean.Project;

public class PagingKtAdapter extends PagingDataAdapter<Project, PagingKtAdapter.ViewHolder> {
    public PagingKtAdapter() {
        super(itemCallback);
    }

    @NonNull
    @Override
    public PagingKtAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_page_recycle, parent, false);
        return new PagingKtAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PagingKtAdapter.ViewHolder holder, int position) {
        Project bean = getItem(position);
        if (bean != null) {
            holder.desc.setText(bean.getDesc());
            holder.date.setText(String.valueOf(bean.getNiceDate()));
            holder.title.setText(bean.getTitle());
            holder.auth.setText(bean.getAuthor());
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView desc;
        TextView title;
        TextView date;
        TextView auth;

        public ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            desc = itemView.findViewById(R.id.desc);
            date = itemView.findViewById(R.id.date);
            auth = itemView.findViewById(R.id.author);
        }
    }

    private static DiffUtil.ItemCallback<Project> itemCallback = new DiffUtil.ItemCallback<Project>() {
        @Override
        public boolean areItemsTheSame(@NonNull Project oldItem, @NonNull Project newItem) {
            return oldItem.getNiceDate().equals(newItem.getNiceDate());
        }

        @SuppressLint("DiffUtilEquals")
        @Override
        public boolean areContentsTheSame(@NonNull Project oldItem, @NonNull Project newItem) {
            return oldItem.equals(newItem);
        }
    };
}
