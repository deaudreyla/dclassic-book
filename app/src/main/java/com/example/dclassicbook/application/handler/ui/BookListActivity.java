package com.example.dclassicbook.application.handler.ui;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager2.widget.ViewPager2;

import com.example.dclassicbook.R;
import com.example.dclassicbook.application.handler.ui.adapter.BookListPagerAdapter;
import com.example.dclassicbook.application.handler.ui.util.NavbarHelper;
import com.example.dclassicbook.application.handler.ui.util.SidebarHelper;

public class BookListActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private TextView tabFiction;
    private TextView tabNonFiction;
    private ViewPager2 viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);
        setContentView(R.layout.activity_booklist);

        drawerLayout = findViewById(R.id.drawerLayout);
        tabFiction = findViewById(R.id.tabFiction);
        tabNonFiction = findViewById(R.id.tabNonFiction);
        viewPager = findViewById(R.id.viewPager);

        viewPager.setAdapter(new BookListPagerAdapter(this));

        tabFiction.setOnClickListener(v -> viewPager.setCurrentItem(0, true));
        tabNonFiction.setOnClickListener(v -> viewPager.setCurrentItem(1, true));

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                updateTabAppearance(position);
            }
        });

        SidebarHelper.setup(this, drawerLayout, SidebarHelper.Page.BOOKLIST);
        NavbarHelper.setup(this, NavbarHelper.Page.BOOKLIST);
    }

    private void updateTabAppearance(int position) {
        boolean isFiction = position == 0;

        tabFiction.setBackgroundResource(isFiction ? R.drawable.bg_tab_active : R.drawable.bg_tab_inactive);
        tabFiction.setTextColor(getColor(isFiction ? R.color.neutral10 : R.color.neutral50));

        tabNonFiction.setBackgroundResource(isFiction ? R.drawable.bg_tab_inactive : R.drawable.bg_tab_active);
        tabNonFiction.setTextColor(getColor(isFiction ? R.color.neutral50 : R.color.neutral10));
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
