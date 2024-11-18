package com.example.myapplication4

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Parcelable
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.overlay.Marker
import kotlinx.parcelize.Parcelize

@Parcelize
data class Place(
    val name: String,
    val rating: Double,
    val reviews: String,
    val imageResId: Int? = null,
    val url: String? = null,
    val latitude: Double? = null,
    val longitude: Double? = null
) : Parcelable

class Category(val categoryName: String) {
    private val places = mutableListOf<Place>()
    fun addPlace(place: Place) = places.add(place)
    fun getRandomPlace(): Place? = if (places.isNotEmpty()) places.random() else null
    fun getPlaces(): List<Place> = places
}

class NextActivity2 : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var restaurantCategory: Category
    private lateinit var cafeCategory: Category
    private lateinit var lodgingCategory: Category
    private lateinit var dateSpotCategory: Category
    private lateinit var naverMap: NaverMap
    private val selectedPlaces = mutableListOf<Place>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_next2)

        findViewById<Button>(R.id.button_home).setOnClickListener {
            // MainActivity로 이동
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        findViewById<Button>(R.id.button_previous).setOnClickListener {
            onBackPressed() // 이전 화면으로 돌아가기
        }
        findViewById<Button>(R.id.button_next3).setOnClickListener {
            moveToNextActivity()
        }

        val region = intent.getStringExtra("region")
        val selectedCategories = intent.getStringArrayListExtra("selectedCategories") ?: arrayListOf()

        initializeCategories(region)

        if (selectedCategories.isNotEmpty()) {
            selectedCategories.forEach { category ->
                when (category) {
                    "음식점" -> setupCategoryUI(
                        restaurantCategory,
                        R.id.restaurantTextView,
                        R.id.restaurantImageView,
                        R.id.restaurantRerollButton,
                        R.id.restaurantUrlTextView
                    )
                    "카페" -> setupCategoryUI(
                        cafeCategory,
                        R.id.cafeTextView,
                        R.id.cafeImageView,
                        R.id.cafeRerollButton,
                        R.id.cafeUrlTextView
                    )
                    "숙박" -> setupCategoryUI(
                        lodgingCategory,
                        R.id.lodgingTextView,
                        R.id.lodgingImageView,
                        R.id.lodgingRerollButton,
                        R.id.lodgingUrlTextView
                    )
                    "데이트 명소" -> setupCategoryUI(
                        dateSpotCategory,
                        R.id.dateSpotTextView,
                        R.id.dateSpotImageView,
                        R.id.dateSpotRerollButton,
                        R.id.dateSpotUrlTextView
                    )
                }
            }
        } else {
            findViewById<TextView>(R.id.noDataTextView).apply {
                text = "선택한 지역에 대한 데이터가 없습니다."
                visibility = View.VISIBLE
            }
        }
    }

    private fun moveToNextActivity() {
        val intent = Intent(this, NextActivity3::class.java)

        // Place 리스트를 Intent로 전달
        intent.putParcelableArrayListExtra("selectedPlaces", ArrayList(selectedPlaces))

        // selectedPlaces에서 좌표만 추출하여 LatLng 리스트로 변환
        val coordinates = selectedPlaces.mapNotNull { place ->
            if (place.latitude != null && place.longitude != null) {
                LatLng(place.latitude!!, place.longitude!!)
            } else null
        }

        // LatLng 리스트를 Intent로 전달
        intent.putParcelableArrayListExtra("coordinates", ArrayList(coordinates))

        // startActivity를 한 번만 호출
        startActivity(intent)
    }






    private fun initializeCategories(region: String?) {
        when (region) {
            "천전동" -> {
                restaurantCategory = Category("음식점").apply {
                    addPlace(Place("권돼지국밥", 4.54, "614개", R.drawable.cr1, "https://naver.me/FCAw2v1d", 35.18413, 128.0922))
                    addPlace(Place("오사장횟집", 4.27, "84개", R.drawable.cr2, "https://naver.me/xbw9WHuS", 35.18591, 128.0884))
                    addPlace(Place("은성게장더크랩", 4.41, "200개", R.drawable.cr2, "https://naver.me/xUSFqeCy", 35.18273, 128.0903))

                }


                cafeCategory = Category("카페").apply {
                    addPlace(Place("깅코이터리", 4.81, "84개", R.drawable.cc1, "https://naver.me/53lAa3Dw", 35.18312, 128.0915))
                    addPlace(Place("퍼시먼", 4.65, "61개", R.drawable.cc2, "https://naver.me/xaP54wm8", 35.18818, 128.0892))
                    addPlace(Place("커피빅스", 4.19, "169개", R.drawable.cc2, "https://naver.me/GWFlMFfE", 35.18650, 128.0814))

                }
                lodgingCategory = Category("숙박").apply {
                    addPlace(Place("그린모텔", 4.42, "63개", R.drawable.cl1, "https://naver.me/Goz4LkdF", 35.17817, 128.0925))
                    addPlace(Place("뭉클 진주게스트하우스", 4.35, "242개", R.drawable.cl2, "https://naver.me/G0f7AN2W", 35.18787, 128.0906))
                }
                dateSpotCategory = Category("데이트 명소").apply {
                    addPlace(Place("경남문화예술회관", 4.45, "100개", R.drawable.cd1, "https://naver.me/xaP1tgZt", 35.18727, 128.0917))
                    addPlace(Place("동성락볼링장", 4.58, "68개", R.drawable.cd2, "https://naver.me/5KZmNZpV", 35.18092, 128.0893))
                }
            }
            "가호동" -> {
                restaurantCategory = Category("음식점").apply {
                    addPlace(Place("오다가다", 4.67, "1,571개", R.drawable.gr1, "https://naver.me/GY2xxnUi", 35.15266, 128.1068))
                    addPlace(Place("스시하루", 4.47, "1,180개", R.drawable.gr2, "https://naver.me/xswll3aJ", 35.15266, 128.1068))
                }
                cafeCategory = Category("카페").apply {
                    addPlace(Place("TALE coffee", 4.79, "158개", R.drawable.gc1, "https://naver.me/IG6G7cOm", 35.15970, 128.1056))
                    addPlace(Place("베이커리925", 4.6, "513개", R.drawable.gc2, "https://naver.me/5VeAe8pW", 35.15507, 128.1074))
                }
                lodgingCategory = Category("숙박").apply {
                    addPlace(Place("호텔보보", 4.48, "69개", R.drawable.gl1, "https://naver.me/5B1tzoW2", 35.17001, 128.1140))
                    addPlace(Place("부메랑 모텔", 4.24, "52개", R.drawable.gl2, "https://naver.me/Gz5gyPru", 35.17821, 128.1403))
                }
                dateSpotCategory = Category("데이트 명소").apply {
                    addPlace(Place("팜스보드게임카페", 4.74, "769개", R.drawable.gd1, "https://naver.me/x6PAXQlW", 35.15296, 128.1061))
                    addPlace(Place("고양이모자", 4.67, "138개", R.drawable.gd2, "https://naver.me/FRDCWZyc", 35.16025, 128.1067))
                }
            }
            "판문동" -> {
                restaurantCategory = Category("음식점").apply {
                    addPlace(Place("진양호짬뽕", 4.34, "509개", R.drawable.pr1, "https://naver.me/Fca6oL8n"))
                    addPlace(Place("한우소리", 4.41, "375개", R.drawable.pr2, "https://naver.me/xDsjxD1T"))
                }
                cafeCategory = Category("카페").apply {
                    addPlace(Place("커피플라워", 4.3, "1,124개", R.drawable.pc1, "https://naver.me/5h3JeW7p"))
                    addPlace(Place("차분히커피part2", 4.6, "421개", R.drawable.pc2, "https://naver.me/xUSFeNmT"))
                }
                lodgingCategory = Category("숙박").apply {
                    addPlace(Place("아시아레이크사이드 호텔", 4.35, "355개", R.drawable.pl1, "https://naver.me/GMR3tatn"))
                    addPlace(Place("호원", 4.29, "157개", R.drawable.pl2, "https://naver.me/xxxhvnVs"))
                }
                dateSpotCategory = Category("데이트 명소").apply {
                    addPlace(Place("진양호동물원", 4.4, "443개", R.drawable.pd1, "https://naver.me/FPseEzIp"))
                    addPlace(Place("진양호공원", 4.45, "455개", R.drawable.pd2, "https://naver.me/xxx8jnbO"))
                }
            }
            "평거,신안동" -> {
                restaurantCategory = Category("음식점").apply {
                    addPlace(Place("상문이숯불치킨", 4.41, "152개", R.drawable.psr1, "https://naver.me/55nQ77wN"))
                    addPlace(Place("섭지코지", 4.38, "80개", R.drawable.psr2, "https://naver.me/GqBMJV4x"))
                }
                cafeCategory = Category("카페").apply {
                    addPlace(Place("올디스", 4.54, "791개", R.drawable.psc1, "https://naver.me/xjeYZqdP"))
                    addPlace(Place("3시15분카페", 4.39, "207개", R.drawable.psc2, "https://naver.me/GeW46tY6"))
                }
                lodgingCategory = Category("숙박").apply {
                    addPlace(Place("브룩스호텔", 4.26, "58개", R.drawable.psl1, "https://naver.me/FrK0UBhq"))
                    addPlace(Place("호텔카리브", 4.03, "54개", R.drawable.psl2, "https://naver.me/G3Jh0if4"))
                }
                dateSpotCategory = Category("데이트 명소").apply {
                    addPlace(Place("성지원골프연습장", 4.32, "197개", R.drawable.psd1, "https://naver.me/5DHj7EN7"))
                    addPlace(Place("스타일그라운드", 4.41, "42개", R.drawable.psd2, "https://naver.me/x4bu3GLw"))
                }
            }
            "성북,중앙동" -> {
                restaurantCategory = Category("음식점").apply {
                    addPlace(Place("안의갈비탕", 4.32, "1,283개", R.drawable.sjr1, "https://naver.me/F5bFMPkA"))
                    addPlace(Place("진주초밥", 4.74, "241개", R.drawable.sjr2, "https://naver.me/xiv5Uqhg"))
                }
                cafeCategory = Category("카페").apply {
                    addPlace(Place("허니타임", 4.5, "435개", R.drawable.sjc1, "https://naver.me/x4buYPzT"))
                    addPlace(Place("카페 이층남자", 4.42, "310개", R.drawable.sjc2, "https://naver.me/Gvkd1abp"))
                }
                lodgingCategory = Category("숙박").apply {
                    addPlace(Place("더패밀리호텔", 4.76, "576개", R.drawable.sjl1, "https://naver.me/xZDP365A"))
                    addPlace(Place("뉴요커호텔", 4.23, "112개", R.drawable.sjl2, "https://naver.me/xIhlsRXD"))
                }
                dateSpotCategory = Category("데이트 명소").apply {
                    addPlace(Place("진주성", 4.43, "2,468개", R.drawable.sjd1, "https://naver.me/F6mleDGi"))
                    addPlace(Place("촉석루", 4.43, "1,045개", R.drawable.sjd2, "https://naver.me/5fn5odFc"))
                }
            }
            "하대동" -> {
                restaurantCategory = Category("음식점").apply {
                    addPlace(Place("미소부엌 본점", 4.9, "1,579개", R.drawable.hr1, "https://naver.me/FIgxefbI"))
                    addPlace(Place("두레숯불갈비", 4.49, "789개", R.drawable.hr2, "https://naver.me/F8KnWset"))
                }
                cafeCategory = Category("카페").apply {
                    addPlace(Place("피베리브라더스", 4.65, "1,592개", R.drawable.hc1, "https://naver.me/FFviRbTO"))
                    addPlace(Place("하대동팥빙수", 4.3, "180개", R.drawable.hc2, "https://example.com"))
                }
                lodgingCategory = Category("숙박").apply {
                    addPlace(Place("맨하탄모텔", 4.21, "32개", R.drawable.hl1, "https://naver.me/5uxfZlox"))
                    addPlace(Place("호텔온도", 4.18, "143개", R.drawable.hl2, "https://naver.me/5vIcZ3jK"))
                }
                dateSpotCategory = Category("데이트 명소").apply {
                    addPlace(Place("삼전볼링센타", 4.42, "123개", R.drawable.hd1, "https://naver.me/G3KQ6xwl"))
                    addPlace(Place("더킹노래연습장", 4.46, "65개", R.drawable.hd2, "https://naver.me/xevCj65S"))
                }
            }
            "상대동" -> {
                restaurantCategory = Category("음식점").apply {
                    addPlace(Place("송기원진주냉면", 4.47, "3,312개", R.drawable.sr1, "https://naver.me/FXZwXdkS"))
                    addPlace(Place("진주냉면산홍", 4.4, "1,547개", R.drawable.sr2, "https://naver.me/GJrFyQBL"))
                }
                cafeCategory = Category("카페").apply {
                    addPlace(Place("카페 테린너", 4.3, "196개", R.drawable.sc1, "https://naver.me/G14NukPh"))
                    addPlace(Place("정동근과자점", 4.36, "554개", R.drawable.sc2, "https://naver.me/GTWG8got"))
                }
                lodgingCategory = Category("숙박").apply {
                    addPlace(Place("호텔온도", 4.18, "143개", R.drawable.sl1, "https://naver.me/5vIcZ3jK"))
                    addPlace(Place("GuestH", 4.38, "80개", R.drawable.sl2,"https://naver.me/xqfBeyuV"))
                }
                dateSpotCategory = Category("데이트 명소").apply {
                    addPlace(Place("진주시립연암도서관", 4.0, "297개", R.drawable.sd1, "https://naver.me/5CzLJ7A3"))
                    addPlace(Place("선학산전망대", 4.0, "238개", R.drawable.sd2, "https://naver.me/Gi9jZij6"))
                }
            }
            "상평동" -> {
                restaurantCategory = Category("음식점").apply {
                    addPlace(Place("박혜경밀양돼지국밥", 4.38, "381개", R.drawable.spr1, "https://naver.me/FOv9MPe7"))
                    addPlace(Place("고집센그집갈비탕", 4.35, "158개", R.drawable.spr2, "https://naver.me/xtW5S9SI"))
                }
                cafeCategory = Category("카페").apply {
                    addPlace(Place("시골하우스", 4.97, "157개", R.drawable.spc1, "https://naver.me/GqB3mNO0"))
                    addPlace(Place("솔밭거닐다", 4.85, "55개", R.drawable.spc2, "https://naver.me/GUvViVNw"))
                }
                lodgingCategory = Category("숙박").apply {
                    addPlace(Place("호텔보보", 4.48, "69개", R.drawable.spl1, "https://naver.me/5B1tzoW2"))
                    addPlace(Place("넘버25 호텔", 4.11, "24개", R.drawable.spl2, "https://naver.me/xswu5mUz"))
                }
                dateSpotCategory = Category("데이트 명소").apply {
                    addPlace(Place("정보 없음", 5.0, "밝은 노동자들", R.drawable.spd1, "https://naver.me/GsTk95LE"))
                }
            }
            "충무공동" -> {
                restaurantCategory = Category("음식점").apply {
                    addPlace(Place("시오일식", 5.0, "163개", R.drawable.cmr1, "https://naver.me/Fa37Tcdq"))
                    addPlace(Place("또오리식육식당", 4.52, "1,160개", R.drawable.cmr2, "https://naver.me/5Y1acrCj"))
                }
                cafeCategory = Category("카페").apply {
                    addPlace(Place("달나라토끼", 4.39, "381개", R.drawable.cmc1, "https://naver.me/GrSmFeSF"))
                    addPlace(Place("HUB roastery & bakery", 4.48, "442개", R.drawable.cmc2, "https://naver.me/F0KwD1dz"))
                }
                lodgingCategory = Category("숙박").apply {
                    addPlace(Place("뉴 라온스테이호텔", 4.22, "499개", R.drawable.cml1, "https://naver.me/5lZf402C"))
                    addPlace(Place("브라운도트 호텔 ", 4.44, "213개 이상", R.drawable.cml2, "https://naver.me/xqfo520B"))
                }
                dateSpotCategory = Category("데이트 명소").apply {
                    addPlace(Place("피어리스", 4.0, "231개", R.drawable.cmd1, "https://naver.me/GPB0jBvv"))
                    addPlace(Place("진주익룡발자국전시관", 4.29, "156개", R.drawable.cmd2, "https://naver.me/FornQCME"))
                }
            }
            "이현동" -> {
                restaurantCategory = Category("음식점").apply {
                    addPlace(Place("참 지대루", 4.51, "564개", R.drawable.er1, "https://naver.me/5h31nchC"))
                    addPlace(Place("타토루", 4.67, "102개", R.drawable.er2, "https://naver.me/FOvqT6nG"))
                }
                cafeCategory = Category("카페").apply {
                    addPlace(Place("북파", 4.71, "75개", R.drawable.ec1, "https://naver.me/xw6cd9Jj"))
                    addPlace(Place("더가야", 4.55, "165개", R.drawable.ec2, "https://naver.me/5X9tRVfA"))
                }
                lodgingCategory = Category("숙박").apply {
                    addPlace(Place("브라운도트 호텔", 4.38, "72개", R.drawable.el1, "https://naver.me/Fn2IHLek"))
                    addPlace(Place("호텔카리브", 4.03, "54개", R.drawable.el2, "https://naver.me/G3Jh0if4"))
                }
                dateSpotCategory = Category("데이트 명소").apply {
                    addPlace(Place("윈골프랜드", 4.15, "259개", R.drawable.ed1, "https://naver.me/FyeQLMaU"))
                    addPlace(Place("진주볼링장", 4.71, "60개", R.drawable.ed2, "https://naver.me/GWF4tDpA"))
                }
            }
            "상봉동" -> {
                restaurantCategory = Category("음식점").apply {
                    addPlace(Place("못안오리탕", 4.22, "57개", R.drawable.sbr1, "https://naver.me/5eTEA0IO", 35.20489, 128.0745))
                    addPlace(Place("놀부네약초백숙", 4.58, "120개", R.drawable.sbr2, "https://naver.me/FpXPwOMi", 35.20214, 128.0731))
                    addPlace(Place("산청돼지국밥", 4.37, "51개", R.drawable.sbr1, "https://naver.me/F0KtGBHQ", 35.1948586, 128.068995))
                    addPlace(Place("고기달인 진주냉면", 4.33, "856개", R.drawable.sbr2, "https://naver.me/FeOwD85D", 35.1973506, 128.0701012))
                    addPlace(Place("진가옥", 4.38, "999+개", R.drawable.sbr1, "https://naver.me/IFjs1d4w", 35.1990897, 128.0811508))
                    addPlace(Place("히요리당", 4.46, "886개", R.drawable.sbr2, "https://naver.me/5HStxJQb", 35.1975741, 128.0695789))
                    addPlace(Place("착한명태 진주점", 4.43, "609개", R.drawable.sbr1, "https://naver.me/FIgPkamy", 35.197494, 128.0757378))
                    addPlace(Place("삼가한우소리", 4.29, "359개", R.drawable.sbr2, "https://naver.me/GjRyj0WD", 35.198284, 128.0679576))
                    addPlace(Place("포항댁", 4.38, "19개", R.drawable.sbr1, "https://naver.me/GvkNmCW7", 35.19702972879579, 128.06992292404175))
                    addPlace(Place("엉뚱한 수제돈까스", 4.63, "118개", R.drawable.sbr2, "https://naver.me/FnnFz0uj", 35.19937714862122, 128.07595789432526))
                }
                cafeCategory = Category("카페").apply {
                    addPlace(Place("쿠키레이디", 4.87, "282개", R.drawable.sbc1, "https://naver.me/FKK5Xhnt", 35.19922, 128.0755))
                    addPlace(Place("브릭", 4.63, "97개", R.drawable.sbc2, "https://naver.me/xuIp98dM", 35.19722, 128.0698))
                }
                lodgingCategory = Category("숙박").apply {
                    addPlace(Place("이프호텔", 4.8, "115개", R.drawable.sbl1, "https://naver.me/GkKIGq5L", 35.19317, 128.0870))
                    addPlace(Place("더 클라우드 호텔", 4.66, "68개", R.drawable.sbl2, "https://naver.me/5kL12dTb", 35.19084, 128.0867))
                }
                dateSpotCategory = Category("데이트 명소").apply {
                    addPlace(Place("비봉산", 4.0, "133개", R.drawable.sbd1, "https://naver.me/xAtlqCfF", 35.20268, 128.0787))
                    addPlace(Place("봉산사", 4.0, "29개", R.drawable.sbd2, "https://naver.me/GOuDE2Aj", 35.20236, 128.0752))
                }
            }
            "초장동" -> {
                restaurantCategory = Category("음식점").apply {
                    addPlace(Place("인생극장", 4.57, "452개", R.drawable.cjr1, "https://naver.me/FzHQRuMy", 35.21311, 128.1117))
                    addPlace(Place("필립", 4.78, "671개", R.drawable.cjr2, "https://naver.me/5Amf9WMj", 35.20915, 128.1099))
                }
                cafeCategory = Category("카페").apply {
                    addPlace(Place("cafe AAM", 4.52, "804개", R.drawable.cjc1, "https://naver.me/x4buw5qn", 35.20025, 128.1061))
                    addPlace(Place("엘더프랑", 4.71, "644개", R.drawable.cjc2, "https://naver.me/5Q4GgaOw", 35.19963, 128.1140))
                }
                lodgingCategory = Category("숙박").apply {
                    addPlace(Place("호텔온도", 4.18, "143개", R.drawable.cjl1, "https://naver.me/5vIcZ3jK", 35.18602, 128.1180))
                    addPlace(Place("장재수월", 4.0, "54개", R.drawable.cjl2, "https://naver.me/5vMNnV7T", 35.22402, 128.1000))
                }
                dateSpotCategory = Category("데이트 명소").apply {
                    addPlace(Place("초전공원", 4.0, "233개", R.drawable.cjd1, "https://naver.me/5FeEuVDr", 35.20569, 128.1259))
                    addPlace(Place("휴노래연습장", 4.61, "63개", R.drawable.cjd2, "https://naver.me/FrKGVbaD", 35.21271, 128.1118))
                }
            }
            else -> {
                restaurantCategory = Category("음식점")
                cafeCategory = Category("카페")
                lodgingCategory = Category("숙박")
                dateSpotCategory = Category("데이트 명소")
            }
        }
    }





    override fun onMapReady(naverMap: NaverMap) {
        this.naverMap = naverMap
        displayMarkerOnMap()
    }

    private fun displayMarkerOnMap() {
        selectedPlaces.forEach { place ->
            val position = LatLng(place.latitude ?: 0.0, place.longitude ?: 0.0)
            val marker = Marker().apply {
                this.position = position
                this.map = naverMap
            }
        }
    }

    private fun setupCategoryUI(
        category: Category,
        textViewId: Int,
        imageViewId: Int,
        rerollButtonId: Int,
        urlTextViewId: Int
    ) {
        displayRandomPlace(category, textViewId, imageViewId, urlTextViewId)

        findViewById<Button>(rerollButtonId).apply {
            visibility = View.VISIBLE
            setOnClickListener {
                displayRandomPlace(category, textViewId, imageViewId, urlTextViewId)
            }
        }
    }

    private fun displayRandomPlace(category: Category, textViewId: Int, imageViewId: Int, urlTextViewId: Int) {
        val place = category.getRandomPlace()
        place?.let { selectedPlace ->
            if (!selectedPlaces.contains(selectedPlace)) {
                selectedPlaces.add(selectedPlace)
            }
            findViewById<TextView>(textViewId).apply {
                text = "이름: ${selectedPlace.name}\n별점: ${selectedPlace.rating}\n리뷰: ${selectedPlace.reviews}"
                visibility = View.VISIBLE
            }
            findViewById<ImageView>(imageViewId).apply {
                setImageResource(selectedPlace.imageResId ?: R.drawable.cr1)
                visibility = View.VISIBLE
            }
            findViewById<TextView>(urlTextViewId).apply {
                text = "지도로 보기"
                visibility = View.VISIBLE
                setOnClickListener { openUrl(selectedPlace.url) }
            }
        }
    }

    private fun openUrl(url: String?) {
        url?.let {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(it))
            startActivity(intent)
        }
    }
}