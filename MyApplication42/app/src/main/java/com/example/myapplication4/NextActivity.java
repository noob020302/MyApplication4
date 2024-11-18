package com.example.myapplication4;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.Random;

public class NextActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next);

        ImageView restaurantImageView = findViewById(R.id.restaurantImageView);
        TextView restaurantTextView = findViewById(R.id.restaurantTextView);
        TextView restaurantUrlTextView = findViewById(R.id.restaurantUrlTextView);
        Button changeRestaurantButton = findViewById(R.id.changeRestaurantButton);

        ImageView cinemaImageView = findViewById(R.id.cinemaImageView);
        TextView cinemaTextView = findViewById(R.id.cinemaTextView);
        TextView cinemaUrlTextView = findViewById(R.id.cinemaUrlTextView);
        Button changeCinemaButton = findViewById(R.id.changeCinemaButton);

        // SubActivity에서 선택한 카테고리 받아오기
        ArrayList<String> selectedCategories = getIntent().getStringArrayListExtra("selectedCategories");

        // 랜덤으로 선택하여 표시할 수 있는 코드 예제
        if (selectedCategories != null && !selectedCategories.isEmpty()) {
            String randomCategory = selectedCategories.get(new Random().nextInt(selectedCategories.size()));

            if (randomCategory.equals("음식점")) {
                restaurantImageView.setVisibility(View.VISIBLE);
                restaurantTextView.setVisibility(View.VISIBLE);
                restaurantUrlTextView.setVisibility(View.VISIBLE);
                cinemaImageView.setVisibility(View.GONE);
                cinemaTextView.setVisibility(View.GONE);
                cinemaUrlTextView.setVisibility(View.GONE);
            } else if (randomCategory.equals("영화관")) {
                cinemaImageView.setVisibility(View.VISIBLE);
                cinemaTextView.setVisibility(View.VISIBLE);
                cinemaUrlTextView.setVisibility(View.VISIBLE);
                restaurantImageView.setVisibility(View.GONE);
                restaurantTextView.setVisibility(View.GONE);
                restaurantUrlTextView.setVisibility(View.GONE);
            }
        }
    }
}

