package com.example.dclassicbook.application.handler.ui;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.RenderEffect;
import android.graphics.Shader;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.WindowCompat;

import com.example.dclassicbook.R;
import com.example.dclassicbook.application.Mediator;
import com.example.dclassicbook.application.handler.models.Book;
import com.example.dclassicbook.application.handler.ui.util.FormatHelper;
import com.example.dclassicbook.application.handler.ui.util.RatingHelper;

public class BookDetailActivity extends AppCompatActivity {

    public static final String EXTRA_BOOK_TITLE = "extra_book_title";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);
        setContentView(R.layout.activity_bookdetail);

        String title = getIntent().getStringExtra(EXTRA_BOOK_TITLE);
        Book book = Mediator.getBookDetail(title);
        if (book == null) {
            finish();
            return;
        }

        ImageView ivBackground = findViewById(R.id.ivBackground);
        ImageView ivCover = findViewById(R.id.ivCover);
        TextView tvCategory = findViewById(R.id.tvCategory);
        TextView tvTitle = findViewById(R.id.tvTitle);
        TextView tvAuthor = findViewById(R.id.tvAuthor);
        TextView tvRatingInfo = findViewById(R.id.tvRatingInfo);
        TextView tvSynopsis = findViewById(R.id.tvSynopsis);
        TextView tvPrice = findViewById(R.id.tvPrice);
        TextView tvBack = findViewById(R.id.tvBack);
        ImageView tvArrowBack = findViewById(R.id.tvArrowBack);
        Button btnBuyBook = findViewById(R.id.btnBuyBook);
        EditText etPhoneNumber = findViewById(R.id.etPhoneNumber);
        EditText etAddress = findViewById(R.id.etAddress);
        LinearLayout llStars = findViewById(R.id.llStars);

        View headerBackground = findViewById(R.id.headerBackground);
        LinearLayout stickyHeaderLayout = findViewById(R.id.stickyHeaderLayout);
        androidx.core.widget.NestedScrollView scrollView = findViewById(R.id.scrollView);

        tvBack.setOnClickListener(v -> finish());
        tvArrowBack.setOnClickListener(v -> finish());

        androidx.core.view.ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content), (v, insets) -> {
            androidx.core.graphics.Insets systemBars = insets.getInsets(androidx.core.view.WindowInsetsCompat.Type.systemBars());

            stickyHeaderLayout.setPadding(
                    stickyHeaderLayout.getPaddingLeft(),
                    systemBars.top + 8,
                    stickyHeaderLayout.getPaddingRight(),
                    stickyHeaderLayout.getPaddingBottom()
            );

            stickyHeaderLayout.post(() -> {
                int totalHeaderHeight = stickyHeaderLayout.getHeight();
                android.view.ViewGroup.LayoutParams bgParams = headerBackground.getLayoutParams();
                bgParams.height = totalHeaderHeight;
                headerBackground.setLayoutParams(bgParams);
            });

            return insets;
        });

        int colorLight = ContextCompat.getColor(this, R.color.neutral10); // Putih
        int colorDark = ContextCompat.getColor(this, R.color.neutral200);

        scrollView.setOnScrollChangeListener((androidx.core.widget.NestedScrollView.OnScrollChangeListener)
                (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {

                    int startScroll = 180;
                    int fadeDistance = 80;

                    float progress = (float) (scrollY - startScroll) / fadeDistance;
                    float alpha = Math.max(0f, Math.min(1f, progress));

                    headerBackground.setAlpha(alpha);

                    if (alpha > 0.5f) {
                        tvBack.setTextColor(colorDark);
                        tvArrowBack.setColorFilter(colorDark, PorterDuff.Mode.SRC_IN);
                    } else {
                        tvBack.setTextColor(colorLight);
                        tvArrowBack.setColorFilter(colorLight, PorterDuff.Mode.SRC_IN);
                    }
                });

        ivBackground.setImageResource(book.getImage());
        ivBackground.setRenderEffect(RenderEffect.createBlurEffect(25f, 25f, Shader.TileMode.CLAMP));
        ivCover.setImageResource(book.getImage());

        tvCategory.setText(book.getCategory());
        tvTitle.setText(book.getTitle());
        tvAuthor.setText(book.getAuthor());

        RatingHelper.renderStars(llStars, book.getRating(), 16, 2);
        int rounded = Math.round((float) book.getRating());
        tvRatingInfo.setText(rounded + " (" + FormatHelper.formatUserCount(book.getUserCount()) + " user)");

        tvSynopsis.setText(book.getSynopsis());
        tvPrice.setText(FormatHelper.formatPrice(book.getPrice()));

        tvBack.setOnClickListener(v -> finish());

        TextWatcher inputWatcher = new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                boolean hasInput = etPhoneNumber.getText().length() > 0 || etAddress.getText().length() > 0;
                btnBuyBook.setEnabled(hasInput);
            }
        };

        etPhoneNumber.addTextChangedListener(inputWatcher);
        etAddress.addTextChangedListener(inputWatcher);

        btnBuyBook.setOnClickListener(v -> {
            String phone = etPhoneNumber.getText().toString().trim();
            String address = etAddress.getText().toString().trim();

            if (phone.isEmpty()) {
                showDialog("Phone Number Kosong", "Phone number tidak boleh kosong.");
                return;
            }

            if (!phone.matches("[0-9]+")) {
                showDialog("Phone Number Tidak Valid", "Phone number hanya boleh berisi angka.");
                return;
            }

            if (address.isEmpty()) {
                showDialog("Address Kosong", "Address tidak boleh kosong.");
                return;
            }

            new AlertDialog.Builder(this)
                    .setTitle("Pesanan Berhasil!")
                    .setMessage("Email konfirmasi pesanan telah dikirimkan kepada Anda.")
                    .setPositiveButton("OK", (dialog, which) -> {
                        Intent intent = new Intent(this, BookListActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                        startActivity(intent);
                        finish();
                    })
                    .setCancelable(false)
                    .show();
        });
    }

    private void showDialog(String title, String message) {
        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", null)
                .show();
    }
}
