package com.example.dclassicbook.application.handler.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dclassicbook.R;
import com.example.dclassicbook.application.handler.models.Store;

import java.util.List;

public class HomeStoreAdapter extends RecyclerView.Adapter<HomeStoreAdapter.HomeStoreViewHolder> {

    private final List<Store> stores;

    public HomeStoreAdapter(List<Store> stores) {
        this.stores = stores;
    }

    @NonNull
    @Override
    public HomeStoreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_home_store, parent, false);
        return new HomeStoreViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeStoreViewHolder holder, int position) {
        Store store = stores.get(position);
        holder.ivHomeStore.setImageResource(store.getImage());
        holder.tvHomeStoreName.setText(store.getName());
        holder.tvHomeStoreLocation.setText(store.getLocation());
    }

    @Override
    public int getItemCount() {
        return stores.size();
    }

    static class HomeStoreViewHolder extends RecyclerView.ViewHolder {
        ImageView ivHomeStore;
        TextView tvHomeStoreName;
        TextView tvHomeStoreLocation;

        HomeStoreViewHolder(@NonNull View itemView) {
            super(itemView);
            ivHomeStore = itemView.findViewById(R.id.ivHomeStore);
            tvHomeStoreName = itemView.findViewById(R.id.tvHomeStoreName);
            tvHomeStoreLocation = itemView.findViewById(R.id.tvHomeStoreLocation);
        }
    }
}
