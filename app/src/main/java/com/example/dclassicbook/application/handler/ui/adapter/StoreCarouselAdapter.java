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

public class StoreCarouselAdapter extends RecyclerView.Adapter<StoreCarouselAdapter.ViewHolder> {

    private final List<Store> stores;

    public StoreCarouselAdapter(List<Store> stores) {
        this.stores = stores;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_carousel_store, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Store store = stores.get(position % stores.size());
        holder.ivStore.setImageResource(store.getImage());
        holder.tvName.setText(store.getName());
        holder.tvLocation.setText(store.getLocation());
    }

    @Override
    public int getItemCount() {
        return Integer.MAX_VALUE;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivStore;
        TextView tvName;
        TextView tvLocation;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivStore = itemView.findViewById(R.id.ivCarouselStore);
            tvName = itemView.findViewById(R.id.tvCarouselStoreName);
            tvLocation = itemView.findViewById(R.id.tvCarouselStoreLocation);
        }
    }
}
