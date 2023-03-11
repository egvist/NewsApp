package com.dev.newsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dev.newsapp.databinding.ActivityNewsDetailsBinding;
import com.dev.newsapp.models.NewsData;
import com.dev.newsapp.models.NewsResponse;
import com.dev.newsapp.network.ApiClient;
import com.dev.newsapp.network.ApiInterface;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class NewsDetailsActivity extends AppCompatActivity {
    private ActivityNewsDetailsBinding binding;
    String id="";
    ApiInterface apiInterface;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityNewsDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        id=getIntent().getStringExtra("id");

        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Fetching Data..");

        binding.backMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        binding.tvShowComments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(NewsDetailsActivity.this,CommentsActivity.class);
                intent.putExtra("id",id);
                startActivity(intent);
            }
        });

        progressDialog.show();
        apiInterface= ApiClient.getClient().create(ApiInterface.class);
        apiInterface.getNewsById(id).enqueue(new Callback<NewsResponse>() {
            @Override
            public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {
                progressDialog.dismiss();
                if (response.isSuccessful() && response.code()==200) {
                    Toast.makeText(NewsDetailsActivity.this, "Success", Toast.LENGTH_SHORT).show();
                    List<NewsData> data=response.body().getItems();
                    Glide.with(NewsDetailsActivity.this).load(data.get(0).getImage()).into(binding.imgNews);
                    binding.textView.setText(data.get(0).getTitle());
                    binding.tvDate.setText(data.get(0).getDate());
                    binding.tvDatails.setText(data.get(0).getText());

                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
                    Date date = null;
                    try {
                        date = sdf.parse(data.get(0).getDate());
                    } catch (ParseException e) {
                        e.printStackTrace();
                        binding.tvDate.setText(data.get(0).getDate());

                    }
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    String dateOnly = dateFormat.format(date);
                    binding.tvDate.setText(dateOnly);


                }else {
                    Toast.makeText(NewsDetailsActivity.this, "Error Fetching Details", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<NewsResponse> call, Throwable t) {
                progressDialog.dismiss();
                Log.e("TAG", "onFailure: " );
                Toast.makeText(NewsDetailsActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}