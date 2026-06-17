package com.example.dclassicbook.application.handler.ui.util;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.dclassicbook.R;

public class RatingHelper {

    public static void renderStars(LinearLayout container, double rating, int starSizeDp, int marginEndDp) {
        Context context = container.getContext();
        float density = context.getResources().getDisplayMetrics().density;
        int filledStars = Math.round((float) rating);

        container.removeAllViews();
        for (int i = 0; i < 5; i++) {
            ImageView star = new ImageView(context);
            int starSize = (int) (starSizeDp * density);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(starSize, starSize);
            params.setMarginEnd((int) (marginEndDp * density));
            star.setLayoutParams(params);
            star.setImageResource(R.drawable.ic_starrating);
            int color = i < filledStars
                    ? context.getResources().getColor(R.color.primary150, null)
                    : context.getResources().getColor(R.color.neutral50, null);
            star.setColorFilter(color);
            container.addView(star);
        }
    }
}
