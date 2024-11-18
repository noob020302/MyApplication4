
package com.example.myapplication4;

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random

class NextActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_next2)

        val restaurantImageView = findViewById<ImageView>(R.id.restaurantImageView)
        val cinemaImageView = findViewById<ImageView>(R.id.cinemaImageView)
        val changeRestaurantButton = findViewById<Button>(R.id.changeRestaurantButton)
        val changeCinemaButton = findViewById<Button>(R.id.changeCinemaButton)

        // 음식점과 영화관 각각의 이미지 리스트
        val restaurantImages = listOf(R.drawable.p1, R.drawable.p2, R.drawable.p3)
        val cinemaImages = listOf(R.drawable.p4, R.drawable.p5, R.drawable.p6)

        // 무작위 이미지를 설정하는 함수
        fun setRandomImage(imageView: ImageView, images: List<Int>) {
            val randomImage = images[Random.nextInt(images.size)]
            imageView.setImageResource(randomImage)
        }

        // 초기 이미지 설정
        setRandomImage(restaurantImageView, restaurantImages)
        setRandomImage(cinemaImageView, cinemaImages)

        // 버튼 클릭 시 무작위 이미지 변경
        changeRestaurantButton.setOnClickListener {
            setRandomImage(restaurantImageView, restaurantImages)
        }

        changeCinemaButton.setOnClickListener {
            setRandomImage(cinemaImageView, cinemaImages)
        }
    }
}
