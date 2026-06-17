package com.example.dclassicbook.application.handler.ui.adapter;

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
import com.example.dclassicbook.application.handler.ui.util.FormatHelper;
import com.example.dclassicbook.application.handler.ui.util.RatingHelper;

import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {

    public interface OnBookClickListener {
        void onBookClick(Book book);
    }

    private final List<Book> books;
    private final OnBookClickListener listener;

    public BookAdapter(List<Book> books, OnBookClickListener listener) {
        this.books = books;
        this.listener = listener;
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_book, parent, false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        Book book = books.get(position);

        holder.ivCover.setImageResource(book.getImage());
        holder.tvTitle.setText(book.getTitle());
        holder.tvAuthor.setText(book.getAuthor());

        RatingHelper.renderStars(holder.llStars, book.getRating(), 12, 2);
        holder.tvRating.setText(String.valueOf(Math.round((float) book.getRating())));
        holder.tvUserCount.setText("(" + FormatHelper.formatUserCount(book.getUserCount()) + " users)");

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onBookClick(book);
            }
        });
    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    static class BookViewHolder extends RecyclerView.ViewHolder {
        ImageView ivCover;
        TextView tvTitle;
        TextView tvAuthor;
        TextView tvRating;
        TextView tvUserCount;
        LinearLayout llStars;

        BookViewHolder(@NonNull View itemView) {
            super(itemView);
            ivCover = itemView.findViewById(R.id.ivCover);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvAuthor = itemView.findViewById(R.id.tvAuthor);
            tvRating = itemView.findViewById(R.id.tvRating);
            tvUserCount = itemView.findViewById(R.id.tvUserCount);
            llStars = itemView.findViewById(R.id.llStars);
        }
    }
}
