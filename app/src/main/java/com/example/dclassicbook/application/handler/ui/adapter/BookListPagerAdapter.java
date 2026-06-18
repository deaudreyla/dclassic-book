package com.example.dclassicbook.application.handler.ui.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.dclassicbook.application.handler.ui.BookListFragment;

public class BookListPagerAdapter extends FragmentStateAdapter {

    private static final String[] CATEGORIES = {"Fiction", "Non-Fiction"};

    public BookListPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return BookListFragment.newInstance(CATEGORIES[position]);
    }

    @Override
    public int getItemCount() {
        return CATEGORIES.length;
    }
}
