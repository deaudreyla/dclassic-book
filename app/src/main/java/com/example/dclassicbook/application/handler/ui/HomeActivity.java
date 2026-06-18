package com.example.dclassicbook.application.handler.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dclassicbook.R;
import com.example.dclassicbook.application.Mediator;
import com.example.dclassicbook.application.handler.models.Store;
import com.example.dclassicbook.application.handler.ui.adapter.FeaturedBookAdapter;
import com.example.dclassicbook.application.handler.ui.adapter.StoreCarouselAdapter;
import com.example.dclassicbook.application.handler.ui.util.NavbarHelper;
import com.example.dclassicbook.application.handler.ui.util.SidebarHelper;

import android.widget.TextView;

import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private static final float SCALE_MIN = 0.85f;

    private DrawerLayout drawerLayout;
    private int currentCarouselPos = 0;
    private LinearLayoutManager carouselLayoutManager;
    private PagerSnapHelper snapHelper;
    private int storeCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);
        setContentView(R.layout.activity_home);

        View headerBackground = findViewById(R.id.headerBackground);
        LinearLayout stickyHeaderLayout = findViewById(R.id.stickyHeaderLayout);
        androidx.core.widget.NestedScrollView scrollView = findViewById(R.id.scrollView);
        android.widget.TextView tvStickyTitle = findViewById(R.id.tvStickyTitle);
        LinearLayout heroSectionLayout = findViewById(R.id.heroSectionLayout);

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
                heroSectionLayout.setPadding(
                        heroSectionLayout.getPaddingLeft(),
                        totalHeaderHeight + extraGap,
                        heroSectionLayout.getPaddingRight(),
                        heroSectionLayout.getPaddingBottom()
                );
            });
            return insets;
        });

        scrollView.setOnScrollChangeListener((androidx.core.widget.NestedScrollView.OnScrollChangeListener)
                (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {

                    float progress = (float) scrollY / 150;
                    float alpha = Math.max(0f, Math.min(1f, progress));
                    headerBackground.setAlpha(alpha);
                    tvStickyTitle.setAlpha(alpha);
                });

        drawerLayout = findViewById(R.id.drawerLayout);

        TextView tvGreeting = findViewById(R.id.tvGreeting);
        tvGreeting.setText("Hello " + Mediator.loggedInUsername + "!");

        SidebarHelper.setup(this, drawerLayout, SidebarHelper.Page.HOME);
        NavbarHelper.setup(this, NavbarHelper.Page.HOME);

        setupFeaturedBooks();
        setupStoreCarousel();

        findViewById(R.id.btnViewAllBooks).setOnClickListener(v -> {
            Intent intent = new Intent(this, BookListActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(intent);
        });

        findViewById(R.id.btnViewAllStores).setOnClickListener(v -> {
            Intent intent = new Intent(this, OurStoreActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(intent);
        });
    }

    private void setupFeaturedBooks() {
        RecyclerView rv = findViewById(R.id.rvFeaturedBooks);
        rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rv.setAdapter(new FeaturedBookAdapter(Mediator.getFeaturedBooks()));
        rv.setHasFixedSize(true);
    }

    private void setupStoreCarousel() {
        List<Store> stores = Mediator.getStoreList();
        storeCount = stores.size();

        RecyclerView rv = findViewById(R.id.rvStoreCarousel);
        carouselLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rv.setLayoutManager(carouselLayoutManager);
        rv.setAdapter(new StoreCarouselAdapter(stores));

        snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(rv);

        // Start at the middle so both directions loop infinitely
        int startPos = (Integer.MAX_VALUE / 2) / storeCount * storeCount;
        carouselLayoutManager.scrollToPositionWithOffset(startPos, rv.getPaddingStart());
        currentCarouselPos = startPos;

        setupDots(storeCount);

        rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                applyScaleEffect(recyclerView);
            }

            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    View snapView = snapHelper.findSnapView(carouselLayoutManager);
                    if (snapView != null) {
                        int pos = carouselLayoutManager.getPosition(snapView);
                        if (pos != currentCarouselPos) {
                            currentCarouselPos = pos;
                            updateDots(pos % storeCount);
                        }
                    }
                }
            }
        });

        // Trigger PagerSnapHelper to center the initial item, then apply scale
        rv.post(() -> {
            View snapView = snapHelper.findSnapView(carouselLayoutManager);
            if (snapView != null) {
                int[] dist = snapHelper.calculateDistanceToFinalSnap(carouselLayoutManager, snapView);
                if (dist != null && (dist[0] != 0 || dist[1] != 0)) {
                    rv.scrollBy(dist[0], dist[1]);
                }
            }
            applyScaleEffect(rv);
        });

        findViewById(R.id.btnNextStore).setOnClickListener(v -> {
            currentCarouselPos++;
            rv.smoothScrollToPosition(currentCarouselPos);
            updateDots(currentCarouselPos % storeCount);
        });

        findViewById(R.id.btnPrevStore).setOnClickListener(v -> {
            currentCarouselPos--;
            rv.smoothScrollToPosition(currentCarouselPos);
            updateDots(currentCarouselPos % storeCount);
        });
    }

    private void applyScaleEffect(RecyclerView rv) {
        int rvCenter = rv.getWidth() / 2;
        for (int i = 0; i < rv.getChildCount(); i++) {
            View child = rv.getChildAt(i);
            int childCenter = child.getLeft() + child.getWidth() / 2;
            float distance = Math.abs(rvCenter - childCenter);
            float maxDistance = rv.getWidth() / 2f;
            float ratio = Math.min(distance / maxDistance, 1f);
            float scale = 1f - (1f - SCALE_MIN) * ratio;
            child.setScaleX(scale);
            child.setScaleY(scale);
        }
    }

    private void setupDots(int count) {
        LinearLayout llDots = findViewById(R.id.llStoreDots);
        llDots.removeAllViews();
        float density = getResources().getDisplayMetrics().density;
        for (int i = 0; i < count; i++) {
            View dot = new View(this);
            int size = (int) ((i == 0 ? 8 : 6) * density);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(size, size);
            params.setMarginEnd((int) (5 * density));
            dot.setLayoutParams(params);
            dot.setBackground(getDrawable(i == 0 ? R.drawable.bg_dot_active : R.drawable.bg_dot_inactive));
            llDots.addView(dot);
        }
    }

    private void updateDots(int activePos) {
        LinearLayout llDots = findViewById(R.id.llStoreDots);
        float density = getResources().getDisplayMetrics().density;
        for (int i = 0; i < llDots.getChildCount(); i++) {
            View dot = llDots.getChildAt(i);
            boolean isActive = i == activePos;
            int size = (int) ((isActive ? 8 : 6) * density);
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) dot.getLayoutParams();
            params.width = size;
            params.height = size;
            dot.setLayoutParams(params);
            dot.setBackground(getDrawable(isActive ? R.drawable.bg_dot_active : R.drawable.bg_dot_inactive));
        }
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
