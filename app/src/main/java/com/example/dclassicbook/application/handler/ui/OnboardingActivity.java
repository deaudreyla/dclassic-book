package com.example.dclassicbook.application.handler.ui;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.dclassicbook.R;

public class OnboardingActivity extends AppCompatActivity {

    private static final int PAGE_COUNT = 3;
    private static final int ANIM_DURATION = 250;

    private final int[] pageImages = {
            R.drawable.img_lamp,
            R.drawable.img_bookfly,
            R.drawable.img_readbook
    };

    private final String[] pageTitles = {
            "A World of Books Awaits",
            "Easy Ordering and Delivery",
            "Read Anytime, Anywhere"
    };

    private final String[] pageDescriptions = {
            "Discover countless titles, from inspiring nonfiction to captivating fiction. Start curating your home library, fill your shelves with stories that matter, and bring every book closer to you.",
            "Order with ease and let us handle the rest. With secure packaging and fast delivery, your stories arrive safely, ready to be enjoyed. From checkout to doorstep, we make sure every book reaches you quickly and in perfect condition.",
            "Open your book and dive right in. Enjoy every chapter at your own pace, letting stories unfold wherever you are. Reading is more than turning pages, it's finding moments of peace, joy, and discovery that become part of your everyday life."
    };

    private int currentPage = 0;

    private LinearLayout llContent;
    private ImageView ivOnboarding;
    private TextView tvTitle;
    private TextView tvDescription;
    private Button btnNext;
    private View[] dots;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_onboarding);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        llContent = findViewById(R.id.llContent);
        ivOnboarding = findViewById(R.id.ivOnboarding);
        tvTitle = findViewById(R.id.tvTitle);
        tvDescription = findViewById(R.id.tvDescription);
        btnNext = findViewById(R.id.btnNext);
        dots = new View[]{
                findViewById(R.id.dot0),
                findViewById(R.id.dot1),
                findViewById(R.id.dot2)
        };

        TextView btnGoToLogin = findViewById(R.id.btnGoToLogin);
        btnGoToLogin.setPaintFlags(btnGoToLogin.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        btnGoToLogin.setOnClickListener(v -> goToLogin());

        updateContent(0);

        btnNext.setOnClickListener(v -> {
            if (currentPage < PAGE_COUNT - 1) {
                animateToNext();
            } else {
                goToLogin();
            }
        });
    }

    private void animateToNext() {
        float slideDistance = llContent.getWidth();
        llContent.animate()
                .translationX(-slideDistance)
                .alpha(0f)
                .setDuration(ANIM_DURATION)
                .withEndAction(() -> {
                    currentPage++;
                    updateContent(currentPage);
                    llContent.setTranslationX(slideDistance);
                    llContent.setAlpha(0f);
                    llContent.animate()
                            .translationX(0f)
                            .alpha(1f)
                            .setDuration(ANIM_DURATION)
                            .start();
                })
                .start();
    }

    private void updateContent(int page) {
        ivOnboarding.setImageResource(pageImages[page]);
        tvTitle.setText(pageTitles[page]);
        tvDescription.setText(pageDescriptions[page]);
        btnNext.setText(page == PAGE_COUNT - 1 ? "Get Started" : "Next");
        updateDots(page);
    }

    private void updateDots(int activePage) {
        float density = getResources().getDisplayMetrics().density;
        for (int i = 0; i < PAGE_COUNT; i++) {
            ViewGroup.LayoutParams params = dots[i].getLayoutParams();
            params.width = (int) ((i == activePage ? 71 : 20) * density);
            dots[i].setLayoutParams(params);
            dots[i].setBackground(getDrawable(i == activePage
                    ? R.drawable.bg_onboarding_dot_active
                    : R.drawable.bg_onboarding_dot_inactive));
        }
    }

    private void goToLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}
