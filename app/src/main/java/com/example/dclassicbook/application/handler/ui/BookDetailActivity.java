package com.example.dclassicbook.application.handler.ui;

import android.graphics.RenderEffect;
import android.graphics.Shader;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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
        TextView btnBuyBook = findViewById(R.id.btnBuyBook);
        LinearLayout llStars = findViewById(R.id.llStars);

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
        btnBuyBook.setOnClickListener(v ->
                Toast.makeText(this, "Order feature is coming soon", Toast.LENGTH_SHORT).show());
    }
}
