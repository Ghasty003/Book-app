package com.example.bookapp;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.bookapp.fragments.BooksFragment;
import com.example.bookapp.fragments.LibraryFragment;
import com.example.bookapp.fragments.SettingsFragment;

public class ViewPageAdapter extends FragmentStateAdapter {

    public ViewPageAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
       switch (position) {
           case 1:
               return new BooksFragment();
           case 2:
               return new SettingsFragment();
           default:
               return new LibraryFragment();
       }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
