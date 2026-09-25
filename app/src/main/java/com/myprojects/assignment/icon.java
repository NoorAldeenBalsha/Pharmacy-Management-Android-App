package com.myprojects.assignment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import androidx.appcompat.app.AppCompatActivity;

public class icon extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.icon);

        // قم بإظهار شاشة الافتتاح لمدة 3 ثوان
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            // انتقال إلى الأنشطة الرئيسية للتطبيق
            startActivity(new Intent(icon.this, LoginActivity.class));
            finish();
        }, 3000); // 3 ثوان
    }
}
