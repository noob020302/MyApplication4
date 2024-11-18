package com.example.myapplication4

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.LocationTrackingMode
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.Align
import com.naver.maps.map.util.FusedLocationSource

class NextActivity3 : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var naverMap: NaverMap
    private lateinit var locationSource: FusedLocationSource
    private val markers = mutableListOf<Marker>() // 마커 리스트 관리

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_next3)

        findViewById<Button>(R.id.button_previous2).setOnClickListener {
            onBackPressed() // 이전 화면으로 돌아가기
        }
        findViewById<Button>(R.id.button_home2).setOnClickListener {
            // MainActivity로 이동
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        locationSource = FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE)

        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
        }

        initializeMap()
    }

    private fun initializeMap() {
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map_fragment) as? MapFragment
        if (mapFragment == null) {
            MapFragment.newInstance().also {
                supportFragmentManager.beginTransaction().add(R.id.map_fragment, it).commit()
            }.getMapAsync(this)
        } else {
            mapFragment.getMapAsync(this)
        }
    }

    override fun onMapReady(naverMap: NaverMap) {
        this.naverMap = naverMap
        naverMap.locationSource = locationSource
        naverMap.uiSettings.isLocationButtonEnabled = true

        // 위치 추적 활성화
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            naverMap.locationTrackingMode = LocationTrackingMode.Follow
        }

        val places = intent.getParcelableArrayListExtra<Place>("selectedPlaces") ?: emptyList()
        refreshMap(places)
    }

    private fun refreshMap(places: List<Place>) {
        clearMarkers() // 기존 마커 제거
        addMarkers(places) // 새로운 마커 추가

        if (places.isNotEmpty()) {
            val firstPlacePosition = LatLng(places[0].latitude ?: 0.0, places[0].longitude ?: 0.0)
            val cameraUpdate = CameraUpdate.scrollTo(firstPlacePosition)
            naverMap.moveCamera(cameraUpdate)
        }
    }

    private fun clearMarkers() {
        markers.forEach { marker ->
            marker.map = null // 지도에서 마커 제거
        }
        markers.clear() // 리스트 초기화
    }

    private fun addMarkers(places: List<Place>) {
        places.forEach { place ->
            val marker = Marker().apply {
                position = LatLng(place.latitude ?: 0.0, place.longitude ?: 0.0)
                captionText = place.name
                setCaptionAligns(Align.Top)
                map = naverMap
            }
            markers.add(marker) // 마커 리스트에 추가
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (locationSource.onRequestPermissionsResult(requestCode, permissions, grantResults)) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // 위치 권한이 승인되면 위치 추적 활성화
                naverMap.locationTrackingMode = LocationTrackingMode.Follow
            } else {
                // 위치 권한이 거부되면 추적 비활성화
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
