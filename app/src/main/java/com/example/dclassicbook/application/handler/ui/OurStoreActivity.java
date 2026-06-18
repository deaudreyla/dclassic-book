package com.example.dclassicbook.application.handler.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dclassicbook.R;
import com.example.dclassicbook.application.Mediator;
import com.example.dclassicbook.application.handler.ui.adapter.StoreAdapter;
import com.example.dclassicbook.application.handler.ui.util.NavbarHelper;
import com.example.dclassicbook.application.handler.ui.util.SidebarHelper;

public class OurStoreActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);
        setContentView(R.layout.activity_ourstore);

        LinearLayout mainContentLayout = findViewById(R.id.mainContentLayout);
        View headerBackground = findViewById(R.id.headerBackground);
        LinearLayout stickyHeaderLayout = findViewById(R.id.stickyHeaderLayout);
        androidx.core.widget.NestedScrollView scrollView = findViewById(R.id.scrollView);
        android.widget.TextView tvStickyTitle = findViewById(R.id.tvStickyTitle);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());

            stickyHeaderLayout.setPadding(
                    stickyHeaderLayout.getPaddingLeft(),
                    systemBars.top + 16, // +16dp untuk estetik
                    stickyHeaderLayout.getPaddingRight(),
                    stickyHeaderLayout.getPaddingBottom()
            );

            stickyHeaderLayout.post(() -> {
                int totalHeaderHeight = stickyHeaderLayout.getHeight();

                android.view.ViewGroup.LayoutParams bgParams = headerBackground.getLayoutParams();
                bgParams.height = totalHeaderHeight;
                headerBackground.setLayoutParams(bgParams);

                int extraGap = (int) (16 * getResources().getDisplayMetrics().density); // Jarak 16dp
                mainContentLayout.setPadding(
                        mainContentLayout.getPaddingLeft(),
                        totalHeaderHeight + extraGap,
                        mainContentLayout.getPaddingRight(),
                        mainContentLayout.getPaddingBottom()
                );
            });
            return insets;
        });

        scrollView.setOnScrollChangeListener((androidx.core.widget.NestedScrollView.OnScrollChangeListener)
                (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
                    int startScroll = 90;
                    int fadeDistance = 20;

                    float progress = (float) (scrollY - startScroll) / fadeDistance;
                    float alpha = Math.max(0f, Math.min(1f, progress));
                    headerBackground.setAlpha(alpha);
                    tvStickyTitle.setAlpha(alpha);
                });

        drawerLayout = findViewById(R.id.drawerLayout);

        RecyclerView rvStoreList = findViewById(R.id.rvStoreList);
        rvStoreList.setLayoutManager(new LinearLayoutManager(this));
        rvStoreList.setAdapter(new StoreAdapter(Mediator.getStoreList()));

        SidebarHelper.setup(this, drawerLayout, SidebarHelper.Page.STORE);
        NavbarHelper.setup(this, NavbarHelper.Page.STORE);
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
