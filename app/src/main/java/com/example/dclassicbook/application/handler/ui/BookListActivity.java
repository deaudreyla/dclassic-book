package com.example.dclassicbook.application.handler.ui;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dclassicbook.R;
import com.example.dclassicbook.application.Mediator;
import com.example.dclassicbook.application.handler.ui.adapter.BookAdapter;
import com.example.dclassicbook.application.handler.ui.util.NavbarHelper;
import com.example.dclassicbook.application.handler.ui.util.SidebarHelper;

public class BookListActivity extends AppCompatActivity {

    private static final String CATEGORY_FICTION = "Fiction";
    private static final String CATEGORY_NON_FICTION = "Non-Fiction";

    private TextView tabFiction;
    private TextView tabNonFiction;
    private RecyclerView rvBookList;
    private DrawerLayout drawerLayout;
    private String activeCategory = CATEGORY_FICTION;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_booklist);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        drawerLayout = findViewById(R.id.drawerLayout);
        tabFiction = findViewById(R.id.tabFiction);
        tabNonFiction = findViewById(R.id.tabNonFiction);
        rvBookList = findViewById(R.id.rvBookList);

        rvBookList.setLayoutManager(new GridLayoutManager(this, 2));

        int gap = (int) (16 * getResources().getDisplayMetrics().density);
        rvBookList.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                int position = parent.getChildAdapterPosition(view);
                if (position % 2 == 0) {
                    outRect.right = gap / 2;
                } else {
                    outRect.left = gap / 2;
                }
                outRect.bottom = gap;
            }
        });

        SidebarHelper.setup(this, drawerLayout, SidebarHelper.Page.BOOKLIST);
        NavbarHelper.setup(this, NavbarHelper.Page.BOOKLIST);

        tabFiction.setOnClickListener(v -> selectCategory(CATEGORY_FICTION));
        tabNonFiction.setOnClickListener(v -> selectCategory(CATEGORY_NON_FICTION));

        selectCategory(activeCategory);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void selectCategory(String category) {
        activeCategory = category;
        updateTabAppearance();
        rvBookList.setAdapter(new BookAdapter(Mediator.getBookList(category), book -> {
            Intent intent = new Intent(this, BookDetailActivity.class);
            intent.putExtra(BookDetailActivity.EXTRA_BOOK_TITLE, book.getTitle());
            startActivity(intent);
        }));
    }

    private void updateTabAppearance() {
        boolean isFiction = activeCategory.equals(CATEGORY_FICTION);

        tabFiction.setBackgroundResource(isFiction ? R.drawable.bg_tab_active : R.drawable.bg_tab_inactive);
        tabFiction.setTextColor(getColor(isFiction ? R.color.neutral10 : R.color.neutral50));

        tabNonFiction.setBackgroundResource(isFiction ? R.drawable.bg_tab_inactive : R.drawable.bg_tab_active);
        tabNonFiction.setTextColor(getColor(isFiction ? R.color.neutral50 : R.color.neutral10));
    }
}
