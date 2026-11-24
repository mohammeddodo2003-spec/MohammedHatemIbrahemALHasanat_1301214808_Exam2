package com.example.exam2;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    private EditText etEmail, etPassword;
    private Button btnLogin;
    private boolean backPressedOnce = false;
    private final Handler handler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);

        // TextWatcher: بيراقب أي تغيير بالنص
        TextWatcher watcher = new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override public void afterTextChanged(Editable s) { validate(); }
        };
        etEmail.addTextChangedListener(watcher);
        etPassword.addTextChangedListener(watcher);

        // زر Login بينقلك لـ ModeActivity
        btnLogin.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, ModeActivity.class)));

        // زر Back: ضغطتين للخروج
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (backPressedOnce) {
                    finish();
                    return;
                }
                backPressedOnce = true;
                Snackbar.make(findViewById(R.id.main),
                        "Press back again to exit", Snackbar.LENGTH_SHORT).show();
                handler.postDelayed(() -> backPressedOnce = false, 2000);
            }
        });
    }

    // بتفعّل الزر فقط إذا الإيميل صحيح والباسورد 6 أحرف أو أكتر
    private void validate() {
        String email = etEmail.getText().toString().trim();
        String pass = etPassword.getText().toString();
        boolean valid = Patterns.EMAIL_ADDRESS.matcher(email).matches() && pass.length() >= 6;
        btnLogin.setEnabled(valid);
    }
}