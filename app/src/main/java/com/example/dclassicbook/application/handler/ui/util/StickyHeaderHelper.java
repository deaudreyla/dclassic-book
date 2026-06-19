package com.example.dclassicbook.application.handler.ui.util;

import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.widget.NestedScrollView;

public class StickyHeaderHelper {

    public interface OnHeaderScrollCallback {
        void onAlphaChanged(float alpha);
    }

    public static void setup(
            AppCompatActivity activity,
            LinearLayout stickyHeaderLayout,
            View headerBackground,
            LinearLayout mainContentLayout,
            NestedScrollView scrollView,
            TextView tvStickyTitle,
            int extraGapDp,
            int startScroll,
            int fadeDistance,
            OnHeaderScrollCallback callback
    ) {
        ViewCompat.setOnApplyWindowInsetsListener(activity.findViewById(android.R.id.content), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());

            if (stickyHeaderLayout != null) {
                stickyHeaderLayout.setPadding(
                        stickyHeaderLayout.getPaddingLeft(),
                        systemBars.top + 16,
                        stickyHeaderLayout.getPaddingRight(),
                        stickyHeaderLayout.getPaddingBottom()
                );

                if (headerBackground != null) {
                    stickyHeaderLayout.post(() -> {
                        int totalHeaderHeight = stickyHeaderLayout.getHeight();

                        ViewGroup.LayoutParams bgParams = headerBackground.getLayoutParams();
                        bgParams.height = totalHeaderHeight;
                        headerBackground.setLayoutParams(bgParams);

                        if (mainContentLayout != null) {
                            int extraGapPx = (int) (extraGapDp * activity.getResources().getDisplayMetrics().density);
                            mainContentLayout.setPadding(
                                    mainContentLayout.getPaddingLeft(),
                                    totalHeaderHeight + extraGapPx,
                                    mainContentLayout.getPaddingRight(),
                                    mainContentLayout.getPaddingBottom()
                            );
                        }
                    });
                }
            }
            return insets;
        });

        if (scrollView != null && headerBackground != null) {
            scrollView.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener)
                    (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {

                        float progress = (float) (scrollY - startScroll) / fadeDistance;
                        float alpha = Math.max(0f, Math.min(1f, progress));

                        headerBackground.setAlpha(alpha);

                        if (tvStickyTitle != null) {
                            tvStickyTitle.setAlpha(alpha);
                        }

                        if (callback != null) {
                            callback.onAlphaChanged(alpha);
                        }
                    });
        }
    }
}