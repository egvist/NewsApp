package com.dev.newsapp.adaptor;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.List;

public class TabLoutAdaptor extends FragmentStateAdapter {
    List<Fragment> list;
    public TabLoutAdaptor(@NonNull FragmentActivity fragmentActivity,List<Fragment> list) {
        super(fragmentActivity);
        this.list=list;
    }

    public TabLoutAdaptor(@NonNull Fragment fragment) {
        super(fragment);
    }

    public TabLoutAdaptor(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return list.get(position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
