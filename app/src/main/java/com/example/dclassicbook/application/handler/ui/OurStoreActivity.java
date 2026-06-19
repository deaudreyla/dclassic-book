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
import com.example.dclassicbook.application.handler.ui.util.StickyHeaderHelper;

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

        StickyHeaderHelper.setup(
                this, stickyHeaderLayout, headerBackground, mainContentLayout,
                scrollView, tvStickyTitle, 6, 80, 40,
                null
        );

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
