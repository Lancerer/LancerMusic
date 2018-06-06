package com.example.lancer.lancermusic.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lancer.lancermusic.R;
import com.example.lancer.lancermusic.bean.MusicBean;

import java.util.List;


public class singAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context mContext;
    private List<MusicBean> lists;

    public singAdapter(Context context, List<MusicBean> list) {
        mContext = context;
        lists = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(mContext, R.layout.item_sing, null);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder MyViewHolder = (ViewHolder) holder;
        MyViewHolder.tvItemSing.setText(lists.get(position).getTitle());
        MyViewHolder.tvItemSinger.setText(lists.get(position).getArtist());

    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvItemSing;
        TextView tvItemSinger;

        public ViewHolder(View itemView) {
            super(itemView);
            tvItemSing = itemView.findViewById(R.id.tv_item_sing);
            tvItemSinger = itemView.findViewById(R.id.tv_item_singer);
        }
    }
}
