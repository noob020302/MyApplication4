// MainActivity.kt
package com.example.myapplication4

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.naver.maps.map.*
import com.naver.maps.map.util.FusedLocationSource

class MainActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var locationSource: FusedLocationSource
    private lateinit var naverMap: NaverMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 지역별 버튼 클릭 시 SubActivity로 이동
        findViewById<Button>(R.id.button1).setOnClickListener {
            startSubActivity("천전동")
        }
        findViewById<Button>(R.id.button2).setOnClickListener {
            startSubActivity("초장동")
        }
        findViewById<Button>(R.id.button3).setOnClickListener {
            startSubActivity("하대동")
        }
        findViewById<Button>(R.id.button4).setOnClickListener {
            startSubActivity("가호동")
        }
        findViewById<Button>(R.id.button5).setOnClickListener {
            startSubActivity("상봉동")
        }
        findViewById<Button>(R.id.button6).setOnClickListener {
            startSubActivity("이현동")
        }
        findViewById<Button>(R.id.button7).setOnClickListener {
            startSubActivity("판문동")
        }
        findViewById<Button>(R.id.button9).setOnClickListener {
            startSubActivity("평거,신안동")
        }
        findViewById<Button>(R.id.button11).setOnClickListener {
            startSubActivity("성북,중앙동")
        }
        findViewById<Button>(R.id.button12).setOnClickListener {
            startSubActivity("상평동")
        }
        findViewById<Button>(R.id.button13).setOnClickListener {
            startSubActivity("상대동")
        }
        findViewById<Button>(R.id.button14).setOnClickListener {
            startSubActivity("충무공동")
        }

        // FusedLocationSource 초기화
        locationSource = FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE)

        // MapFragment 설정
        val fm = supportFragmentManager
        val mapFragment = fm.findFragmentById(R.id.map_fragment) as MapFragment?
            ?: MapFragment.newInstance().also {
                fm.beginTransaction().add(R.id.map_fragment, it).commit()
            }
        mapFragment.getMapAsync(this)
    }

    private fun startSubActivity(region: String) {
        val intent = Intent(this, SubActivity::class.java)
        intent.putExtra("region", region)
        startActivity(intent)
    }

    override fun onMapReady(naverMap: NaverMap) {
        this.naverMap = naverMap
        naverMap.locationSource = locationSource

        // 위치 추적 모드를 Follow로 설정
        naverMap.locationTrackingMode = LocationTrackingMode.Follow
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (locationSource.onRequestPermissionsResult(requestCode, permissions, grantResults)) {
            if (!locationSource.isActivated) { // 권한 거부됨
                naverMap.locationTrackingMode = LocationTrackingMode.None
            }
            return
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1000
    }
}
