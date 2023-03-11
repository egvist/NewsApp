package com.dev.newsapp;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.dev.newsapp.adaptor.TabLoutAdaptor;
import com.dev.newsapp.databinding.ActivityMainBinding;
import com.dev.newsapp.fragments.EcnomicsFragment;
import com.dev.newsapp.fragments.PoliticsFragment;
import com.dev.newsapp.fragments.SportsFragment;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    TabLoutAdaptor adaptor;
    List<Fragment> list = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        list.add(new EcnomicsFragment());
        list.add(new SportsFragment());
        list.add(new PoliticsFragment());
        adaptor = new TabLoutAdaptor(this, list);

        binding.vpTabs.setAdapter(adaptor);
        new TabLayoutMediator(binding.tabLayout, binding.vpTabs, true, (tab, position) -> {
            switch (position) {
                case 0:
                    tab.setText("ECONOMICS");
                    break;
                case 1:
                    tab.setText("SPORTS");
                    break;
                case 2:
                    tab.setText("POLITICS");
                    break;

            }
        }).attach();

        binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                binding.vpTabs.setCurrentItem(tab.getPosition());

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

}

