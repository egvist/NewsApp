package com.dev.newsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.dev.newsapp.adaptor.CommentsAdaptor;
import com.dev.newsapp.databinding.ActivityCommentsBinding;
import com.dev.newsapp.models.CommentsData;
import com.dev.newsapp.models.CommentsResponse;
import com.dev.newsapp.network.ApiClient;
import com.dev.newsapp.network.ApiInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommentsActivity extends AppCompatActivity {
    ActivityCommentsBinding binding;
    ApiInterface apiInterface;
    CommentsAdaptor commentsAdaptor;
    String id="";
    ProgressDialog progressDialog;
    List<CommentsData> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityCommentsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        list=new ArrayList<>();
        id=getIntent().getStringExtra("id");

        binding.itemToolbar.menuDownloads.setVisibility(View.VISIBLE);
        binding.itemToolbar.menuDownloads.setImageResource(R.drawable.ic_baseline_mode_edit_24);
        binding.itemToolbar.menuDownloads.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(CommentsActivity.this,AddCommentActivity.class);
                intent.putExtra("id",id);
                startActivity(intent);
            }
        });
        binding.itemToolbar.actionMenu.setImageResource(R.drawable.ic_baseline_arrow_back_24);
        binding.itemToolbar.actionMenu.setBackgroundColor(Color.TRANSPARENT);
        binding.itemToolbar.actionMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            finish();
            }
        });

        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Fetching Data...");
        progressDialog.show();
        apiInterface= ApiClient.getClient().create(ApiInterface.class);
        apiInterface.getComments(id).enqueue(new Callback<CommentsResponse>() {
            @Override
            public void onResponse(Call<CommentsResponse> call, Response<CommentsResponse> response) {
                progressDialog.dismiss();
                if(response.isSuccessful() && response.code()==200){
                list=response.body().getItems();
                if(list==null){
                    list=new ArrayList<>();
                }
                binding.tvComments.setLayoutManager(new LinearLayoutManager(CommentsActivity.this));
                commentsAdaptor=new CommentsAdaptor(list,CommentsActivity.this);
                binding.tvComments.setAdapter(commentsAdaptor);
            }else {
                    Toast.makeText(CommentsActivity.this, response.errorBody().toString(), Toast.LENGTH_SHORT).show();
            }
            }

            @Override
            public void onFailure(Call<CommentsResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(CommentsActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}