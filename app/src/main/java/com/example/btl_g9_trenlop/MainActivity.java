package com.example.btl_g9_trenlop;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // AndroidManifest hiện đang trỏ tới MainActivity (package gốc).
        // TV4 lại nằm trong com.example.btl_g9_trenlop.view.MainActivity, nên mình chuyển hướng sang đó.
        startActivity(new Intent(this, com.example.btl_g9_trenlop.view.MainActivity.class));
        finish();
    }
}