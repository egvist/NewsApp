package com.dev.newsapp.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.dev.newsapp.R;
import com.dev.newsapp.adaptor.NewsAdaptor;
import com.dev.newsapp.databinding.FragmentPoliticsBinding;
import com.dev.newsapp.models.NewsData;
import com.dev.newsapp.models.NewsResponse;
import com.dev.newsapp.network.ApiClient;
import com.dev.newsapp.network.ApiInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PoliticsFragment extends Fragment {

    FragmentPoliticsBinding binding;
    List<NewsData> list;
    NewsAdaptor adaptor;
    ApiInterface apiInterface;
    ProgressDialog progressDialog;
    public PoliticsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding=FragmentPoliticsBinding.inflate(getLayoutInflater(),container,false);
        list=new ArrayList<>();
        progressDialog=new ProgressDialog(requireContext());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        progressDialog.setMessage("Fetching News..");
        progressDialog.show();
        apiInterface= ApiClient.getClient().create(ApiInterface.class);
        apiInterface.getNewsByCategory("3").enqueue(new Callback<NewsResponse>() {
            @Override
            public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {
                progressDialog.dismiss();
                if(response.isSuccessful() && response.code()==200){
                    list=response.body().getItems();
                    if(list==null){
                        list=new ArrayList<>();
                    }
                    binding.rvNews.setLayoutManager(new LinearLayoutManager(requireContext()));
                    adaptor= new NewsAdaptor(list,requireContext());
                    binding.rvNews.setAdapter(adaptor);
                }else {
                    Toast.makeText(requireContext(), "Error Fetching NEws", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<NewsResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(requireContext(), t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        if(progressDialog.isShowing()){
            progressDialog.dismiss();
        }
    }
}
