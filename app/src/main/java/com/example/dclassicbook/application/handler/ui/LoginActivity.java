package com.example.dclassicbook.application.handler.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.dclassicbook.R;
import com.example.dclassicbook.application.Mediator;

public class LoginActivity extends AppCompatActivity {

    private boolean isPasswordVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        EditText etUsername = findViewById(R.id.etUsername);
        EditText etPassword = findViewById(R.id.etPassword);
        ImageView ivEyeToggle = findViewById(R.id.ivEyeToggle);
        Button btnSignIn = findViewById(R.id.btnSignIn);
        TextView tvUsernameError = findViewById(R.id.tvUsernameError);
        TextView tvPasswordError = findViewById(R.id.tvPasswordError);

        btnSignIn.setEnabled(false);

        TextWatcher inputWatcher = new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                boolean hasInput = etUsername.getText().length() > 0 || etPassword.getText().length() > 0;
                btnSignIn.setEnabled(hasInput);
            }
        };

        etUsername.addTextChangedListener(inputWatcher);
        etPassword.addTextChangedListener(inputWatcher);

        ivEyeToggle.setOnClickListener(v -> {
            isPasswordVisible = !isPasswordVisible;
            if (isPasswordVisible) {
                etPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            } else {
                etPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            }
            etPassword.setSelection(etPassword.getText().length());
        });

        btnSignIn.setOnClickListener(v -> {
            String username = etUsername.getText().toString().trim();
            String password = etPassword.getText().toString().trim();
            boolean valid = true;

            if (username.isEmpty()) {
                tvUsernameError.setText("Username tidak boleh kosong");
                tvUsernameError.setVisibility(View.VISIBLE);
                valid = false;
            } else {
                tvUsernameError.setVisibility(View.GONE);
            }

            if (password.isEmpty()) {
                tvPasswordError.setText("Password tidak boleh kosong");
                tvPasswordError.setVisibility(View.VISIBLE);
                valid = false;
            } else if (!isAlphanumeric(password)) {
                tvPasswordError.setText("Password harus mengandung huruf dan angka");
                tvPasswordError.setVisibility(View.VISIBLE);
                valid = false;
            } else {
                tvPasswordError.setVisibility(View.GONE);
            }

            if (valid) {
                Mediator.loggedInUsername = username;
                Intent intent = new Intent(this, HomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });
    }

    private boolean isAlphanumeric(String password) {
        boolean hasLetter = false;
        boolean hasDigit = false;
        for (char c : password.toCharArray()) {
            if (Character.isLetter(c)) hasLetter = true;
            if (Character.isDigit(c)) hasDigit = true;
        }
        return hasLetter && hasDigit;
    }
}
