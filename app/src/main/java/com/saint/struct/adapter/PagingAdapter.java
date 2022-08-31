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
import com.saint.struct.bean.WanListBean;

public class PagingAdapter extends PagingDataAdapter<WanListBean, PagingAdapter.ViewHolder> {
    public PagingAdapter() {
        super(itemCallback);
    }

    @NonNull
    @Override
    public PagingAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_page_recycle, parent, false);
        return new PagingAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PagingAdapter.ViewHolder holder, int position) {
        WanListBean bean = getItem(position);
        if (bean != null) {
            holder.desc.setText(bean.desc);
            holder.date.setText(bean.niceDate);
            holder.title.setText(bean.title);
            holder.auth.setText(bean.author);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView desc;
        TextView date;
        TextView title;
        TextView auth;

        public ViewHolder(View itemView) {
            super(itemView);
            desc = itemView.findViewById(R.id.desc);
            date = itemView.findViewById(R.id.date);
            title = itemView.findViewById(R.id.title);
            auth = itemView.findViewById(R.id.author);
        }
    }

    private static DiffUtil.ItemCallback<WanListBean> itemCallback = new DiffUtil.ItemCallback<WanListBean>() {
        @Override
        public boolean areItemsTheSame(@NonNull WanListBean oldItem, @NonNull WanListBean newItem) {
            return oldItem.id.equals(newItem.id);
        }

        @SuppressLint("DiffUtilEquals")
        @Override
        public boolean areContentsTheSame(@NonNull WanListBean oldItem, @NonNull WanListBean newItem) {
            return oldItem.equals(newItem);
        }
    };
}
