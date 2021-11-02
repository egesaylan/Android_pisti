package com.example.pisti;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.pisti.databinding.ActivityFirstScreenBinding;

public class firstScreen extends AppCompatActivity {

    private ActivityFirstScreenBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFirstScreenBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);


    }

    public void play(View view) {
        Intent intent = new Intent(firstScreen.this, MainActivity.class);
        startActivity(intent);


    }

}