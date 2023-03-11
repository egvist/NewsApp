package com.dev.newsapp.adaptor;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dev.newsapp.R;
import com.dev.newsapp.models.CommentsData;

import java.util.List;

public class CommentsAdaptor extends RecyclerView.Adapter<CommentsAdaptor.Vh> {
    List<CommentsData> list;
    Context context;

    public CommentsAdaptor(List<CommentsData> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public Vh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view= LayoutInflater.from(context).inflate(R.layout.list_comments,parent,false);
       return new Vh(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Vh holder, int position) {
        CommentsData item=list.get(position);
        holder.binding.tvUser.setText(item.getName());
        holder.binding.tvComments.setText(item.getText());

    }



    @Override
    public int getItemCount() {
        return list.size();
    }

    static class Vh extends RecyclerView.ViewHolder {
        com.dev.newsapp.databinding.ListCommentsBinding binding;
        public Vh(@NonNull View itemView) {
            super(itemView);
            binding= com.dev.newsapp.databinding.ListCommentsBinding.bind(itemView);
        }

    }
}
