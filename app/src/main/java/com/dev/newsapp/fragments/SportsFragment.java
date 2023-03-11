package com.dev.newsapp.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.dev.newsapp.adaptor.NewsAdaptor;
import com.dev.newsapp.databinding.FragmentSportsBinding;
import com.dev.newsapp.models.NewsData;
import com.dev.newsapp.models.NewsResponse;
import com.dev.newsapp.network.ApiClient;
import com.dev.newsapp.network.ApiInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SportsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SportsFragment extends Fragment {

    private FragmentSportsBinding binding;
    List<NewsData> list;
    NewsAdaptor adaptor;
    ApiInterface apiInterface;
    ProgressDialog progressDialog;

    public SportsFragment() {
        // Required empty public constructor
    }


    public static SportsFragment newInstance(String param1, String param2) {
        SportsFragment fragment = new SportsFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentSportsBinding.inflate(getLayoutInflater(), container, false);
        list = new ArrayList<>();
        progressDialog = new ProgressDialog(requireActivity());
        progressDialog.setMessage("Fetching news..");
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        progressDialog.show();
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        apiInterface.getNewsByCategory("2").enqueue(new Callback<NewsResponse>() {
            @Override
            public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {
                progressDialog.dismiss();
                if (response.isSuccessful() && response.code() == 200) {
                    list = response.body().getItems();
                    if (list == null) {
                        list = new ArrayList<>();
                    }

                    binding.rvNews.setLayoutManager(new LinearLayoutManager(requireContext()));
                    adaptor= new NewsAdaptor(list,requireContext());
                    binding.rvNews.setAdapter(adaptor);
                } else {
                    Toast.makeText(requireActivity(), "Error Fetching News", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<NewsResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(requireActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }
}
