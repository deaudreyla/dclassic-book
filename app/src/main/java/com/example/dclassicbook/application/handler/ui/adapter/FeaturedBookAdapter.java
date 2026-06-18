package com.example.dclassicbook.application.handler.ui.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dclassicbook.R;
import com.example.dclassicbook.application.handler.models.Book;
import com.example.dclassicbook.application.handler.ui.BookDetailActivity;
import com.example.dclassicbook.application.handler.ui.util.FormatHelper;
import com.example.dclassicbook.application.handler.ui.util.RatingHelper;

import java.util.List;

public class FeaturedBookAdapter extends RecyclerView.Adapter<FeaturedBookAdapter.FeaturedBookViewHolder> {

    private final List<Book> books;

    public FeaturedBookAdapter(List<Book> books) {
        this.books = books;
    }

    @NonNull
    @Override
    public FeaturedBookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_featured_book, parent, false);
        return new FeaturedBookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FeaturedBookViewHolder holder, int position) {
        Book book = books.get(position);

        holder.ivFeaturedCover.setImageResource(book.getImage());
        holder.tvRank.setText(String.valueOf(position + 1));
        holder.tvFeaturedTitle.setText(book.getTitle());
        holder.tvFeaturedAuthor.setText(book.getAuthor());

        RatingHelper.renderStars(holder.llFeaturedStars, book.getRating(), 10, 2);
        holder.tvFeaturedCount.setText("(" + FormatHelper.formatUserCount(book.getUserCount()) + " users)");

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), BookDetailActivity.class);
            intent.putExtra(BookDetailActivity.EXTRA_BOOK_TITLE, book.getTitle());
            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    static class FeaturedBookViewHolder extends RecyclerView.ViewHolder {
        ImageView ivFeaturedCover;
        TextView tvRank;
        TextView tvFeaturedTitle;
        TextView tvFeaturedAuthor;
        TextView tvFeaturedCount;
        LinearLayout llFeaturedStars;

        FeaturedBookViewHolder(@NonNull View itemView) {
            super(itemView);
            ivFeaturedCover = itemView.findViewById(R.id.ivFeaturedCover);
            tvRank = itemView.findViewById(R.id.tvRank);
            tvFeaturedTitle = itemView.findViewById(R.id.tvFeaturedTitle);
            tvFeaturedAuthor = itemView.findViewById(R.id.tvFeaturedAuthor);
            tvFeaturedCount = itemView.findViewById(R.id.tvFeaturedCount);
            llFeaturedStars = itemView.findViewById(R.id.llFeaturedStars);
        }
    }
}
