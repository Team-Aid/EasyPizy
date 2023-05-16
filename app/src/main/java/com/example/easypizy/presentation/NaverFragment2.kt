package com.example.easypizy.presentation

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.example.easypizy.databinding.NaverFragment2Binding
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.*
import com.naver.maps.map.overlay.CircleOverlay
import com.naver.maps.map.util.FusedLocationSource
import org.json.JSONObject

class NaverFragment2 : FragmentActivity(), OnMapReadyCallback {
    private lateinit var binding: NaverFragment2Binding
    private lateinit var naverMap: NaverMap
    private lateinit var locationSource: FusedLocationSource


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = NaverFragment2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.radioButton.setOnClickListener {

            val intent = Intent(this, NaverFragment::class.java)
            startActivity(intent)

        }



        val fm = supportFragmentManager
        val mapFragment = fm.findFragmentById(com.example.easypizy.R.id.map2) as MapFragment?
            ?: MapFragment.newInstance().also {
                fm.beginTransaction().add(com.example.easypizy.R.id.map2, it).commit()
            }

        mapFragment.getMapAsync(this)
    }


    override fun onMapReady(map: NaverMap) {
        naverMap = map


        naverMap.maxZoom = 18.0
        naverMap.minZoom = 10.0

        val cameraUpdate = CameraUpdate.scrollTo(LatLng(37.5833,127.0096))
        naverMap.moveCamera(cameraUpdate)



        // 현재 위치를 찾는 버튼 활성화
        val uiSetting = naverMap.uiSettings
        uiSetting.isLocationButtonEnabled = false


        binding.currentLocationButton.map = naverMap

        // 위치를 반환하는 FusedLocationSource 생성
        locationSource = FusedLocationSource(this@NaverFragment2, LOCATION_PERMISSION_REQUEST_CODE)
        // 위치소스 지정
        naverMap.locationSource = locationSource

        val jsonString = assets.open("yongsanschool.json").reader().readText()
        val jsonString2 = assets.open("gangjinschool.json").reader().readText()
        val jsonString3 = assets.open("sungbokschool.json").reader().readText()
        val jsonString4 = assets.open("subway.json").reader().readText()

        val jsonObject = JSONObject(jsonString)
        val jsonObject2 = JSONObject(jsonString2)
        val jsonObject3 = JSONObject(jsonString3)
        val jsonObject4 = JSONObject(jsonString4)


        val jsonArray = jsonObject.getJSONArray("data")
        val jsonArray2 = jsonObject2.getJSONArray("data")
        val jsonArray3 = jsonObject3.getJSONArray("data")
        val jsonArray4 = jsonObject4.getJSONArray("data")


        for (i in 0 until jsonArray.length()) {
            val lat = jsonArray.getJSONObject(i).getString("위도")
            val latnum: Double = lat.toDouble()

            val lng = jsonArray.getJSONObject(i).getString("경도")
            val lngnum: Double = lng.toDouble()


            val mutableList = mutableListOf(latnum, lngnum)

            for (yongsan in  mutableList) {
                val circle = CircleOverlay()
                circle.center = LatLng(latnum, lngnum)
                circle.color = Color.RED
                circle.radius = 50.0
                circle.map = naverMap

            }
        }

        for (i in 0 until jsonArray2.length()) {
            val lat2 = jsonArray2.getJSONObject(i).getString("위도")
            val latnum2: Double = lat2.toDouble()


            val lng2 = jsonArray2.getJSONObject(i).getString("경도")
            val lngnum2: Double = lng2.toDouble()


            val mutableList = mutableListOf(latnum2, lngnum2)

            for (yongsan in  mutableList) {
                val circle = CircleOverlay()
                circle.center = LatLng(latnum2, lngnum2)
                circle.color = Color.RED
                circle.radius = 50.0
                circle.map = naverMap
            }
        }

        for (i in 0 until jsonArray3.length()) {
            val lat3 = jsonArray3.getJSONObject(i).getString("위도")
            val latnum3: Double = lat3.toDouble()


            val lng3 = jsonArray3.getJSONObject(i).getString("경도")
            val lngnum3: Double = lng3.toDouble()


            val mutableList = mutableListOf(latnum3, lngnum3)

            for (yongsan in  mutableList) {
                val circle = CircleOverlay()
                circle.center = LatLng(latnum3, lngnum3)
                circle.color = Color.RED
                circle.radius = 50.0
                circle.map = naverMap
            }
        }

        for (i in 0 until jsonArray4.length()) {
            val lat4 = jsonArray4.getJSONObject(i).getString("위도")
            val latnum4: Double = lat4.toDouble()


            val lng4 = jsonArray4.getJSONObject(i).getString("경도")
            val lngnum4: Double = lng4.toDouble()


            val mutableList = mutableListOf(latnum4, lngnum4)

            for (yongsan in  mutableList) {
                val circle = CircleOverlay()
                circle.center = LatLng(latnum4, lngnum4)
                circle.color = Color.GREEN
                circle.radius = 100.0
                circle.map = naverMap
            }
        }








    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        // requestCode 확인
        if(requestCode != LOCATION_PERMISSION_REQUEST_CODE)
            return

        // 권한 팝업을 쉽게 구현하기 위해서 google 에서 제공하는 라이브러리를 사용
        if(locationSource.onRequestPermissionsResult(requestCode,permissions,grantResults)){
            if(!locationSource.isActivated){
                naverMap.locationTrackingMode = LocationTrackingMode.None
            }
            return
        }

    }


    companion object{
        private const val LOCATION_PERMISSION_REQUEST_CODE = 10000
    }

}


