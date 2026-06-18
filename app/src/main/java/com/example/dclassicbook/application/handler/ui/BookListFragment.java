package com.example.dclassicbook.application.handler.ui;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dclassicbook.R;
import com.example.dclassicbook.application.Mediator;
import com.example.dclassicbook.application.handler.ui.adapter.BookAdapter;

public class BookListFragment extends Fragment {

    private static final String ARG_CATEGORY = "category";

    public static BookListFragment newInstance(String category) {
        BookListFragment fragment = new BookListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_CATEGORY, category);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book_list, container, false);

        String category = getArguments() != null ? getArguments().getString(ARG_CATEGORY) : "";

        RecyclerView rv = view.findViewById(R.id.rvBookList);
        rv.setLayoutManager(new GridLayoutManager(getContext(), 2));

        int gap = (int) (16 * getResources().getDisplayMetrics().density);
        rv.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View v,
                                       @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                int position = parent.getChildAdapterPosition(v);
                if (position % 2 == 0) {
                    outRect.right = gap / 2;
                } else {
                    outRect.left = gap / 2;
                }
                outRect.bottom = gap;
            }
        });

        rv.setAdapter(new BookAdapter(Mediator.getBookList(category), book -> {
            Intent intent = new Intent(getActivity(), BookDetailActivity.class);
            intent.putExtra(BookDetailActivity.EXTRA_BOOK_TITLE, book.getTitle());
            startActivity(intent);
        }));

        return view;
    }
}
