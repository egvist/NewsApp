package com.dev.newsapp.adaptor;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dev.newsapp.NewsDetailsActivity;
import com.dev.newsapp.R;
import com.dev.newsapp.databinding.ListNewsBinding;
import com.dev.newsapp.models.NewsData;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class NewsAdaptor extends RecyclerView.Adapter<NewsAdaptor.Vh> {

    List<NewsData> list;
    Context context;

    public NewsAdaptor(List<NewsData> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public Vh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_news,parent,false);
        return new Vh(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Vh holder, int position) {
        NewsData data=list.get(position);
        Glide.with(context).load(data.getImage()).into(holder.binding.imgNews);
        holder.binding.tvDate.setText(data.getText());
        holder.binding.tvTitle.setText(data.getTitle());

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        Date date = null;
        try {
            date = sdf.parse(data.getDate());
        } catch (ParseException e) {
            e.printStackTrace();
            holder.binding.tvDate.setText(data.getDate());

        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateOnly = dateFormat.format(date);
        holder.binding.tvDate.setText(dateOnly);


        holder.binding.imgNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, NewsDetailsActivity.class);
                intent.putExtra("id",data.getId());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class Vh extends RecyclerView.ViewHolder {
        ListNewsBinding binding;
        public Vh(@NonNull View itemView) {
            super(itemView);
            binding=ListNewsBinding.bind(itemView);
        }
    }
}
