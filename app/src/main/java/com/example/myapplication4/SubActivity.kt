package com.example.myapplication4

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import androidx.appcompat.app.AppCompatActivity

class SubActivity : AppCompatActivity() {
    private var selectedRegion: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sub)
        val backButton: Button = findViewById(R.id.backButton)
        backButton.setOnClickListener {
            onBackPressed() // 뒤로가기 버튼 클릭 시 이전 화면으로 이동

        }

        // MainActivity에서 전달된 지역 정보를 받음
        selectedRegion = intent.getStringExtra("region")

        val checkRestaurant = findViewById<CheckBox>(R.id.checkRestaurant)
        val checkCafe = findViewById<CheckBox>(R.id.checkCafe)
        val checkLodging = findViewById<CheckBox>(R.id.checkLodging)
        val checkDateSpot = findViewById<CheckBox>(R.id.checkDateSpot)
        val nextButton = findViewById<Button>(R.id.nextButton)

        nextButton.setOnClickListener {
            val selectedCategories = mutableListOf<String>()
            if (checkRestaurant.isChecked) selectedCategories.add("음식점")
            if (checkCafe.isChecked) selectedCategories.add("카페")
            if (checkLodging.isChecked) selectedCategories.add("숙박")
            if (checkDateSpot.isChecked) selectedCategories.add("데이트 명소")

            // NextActivity2로의 Intent 설정
            val intent = Intent(this@SubActivity, NextActivity2::class.java).apply {
                putExtra("region", selectedRegion)
                putStringArrayListExtra("selectedCategories", ArrayList(selectedCategories))
            }
            startActivity(intent)
        }
    }
}
