package com.example.dclassicbook.application.handler.ui;

import android.os.Bundle;
import android.widget.LinearLayout;
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

        LinearLayout staticHeaderLayout = findViewById(R.id.staticHeaderLayout);
        // 1. Panggil ID bungkus konten yang baru
        LinearLayout mainContentLayout = findViewById(R.id.mainContentLayout);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());

            // 2. Padding kamera untuk header (+16)
            if (staticHeaderLayout != null) {
                staticHeaderLayout.setPadding(
                        staticHeaderLayout.getPaddingLeft(),
                        systemBars.top + 16,
                        staticHeaderLayout.getPaddingRight(),
                        staticHeaderLayout.getPaddingBottom()
                );
            }

            // 3. Atur gap (jarak) header dengan tulisan dari Java
            if (mainContentLayout != null) {
                // Samakan angka 6 ini dengan OurStoreActivity
                int extraGap = (int) (6 * getResources().getDisplayMetrics().density);

                mainContentLayout.setPadding(
                        mainContentLayout.getPaddingLeft(),
                        extraGap, // Terapkan gap-nya di sisi atas
                        mainContentLayout.getPaddingRight(),
                        mainContentLayout.getPaddingBottom()
                );
            }
            return insets;
        });

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
