package com.example.capston4

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.fragment.app.FragmentActivity
import com.example.capston4.databinding.NaverFragment2Binding
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.*
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.util.FusedLocationSource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

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
        val mapFragment = fm.findFragmentById(R.id.map2) as MapFragment?
            ?: MapFragment.newInstance().also {
                fm.beginTransaction().add(R.id.map2, it).commit()
            }

        mapFragment.getMapAsync(this)
    }
    private fun getNoSmokeListAPI() {
        val retrofit2: Retrofit = Retrofit.Builder()
            .baseUrl("https://run.mocky.io")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit2.create(NoSmokeApi::class.java).also {
            it.getNoSmoke()
                .enqueue(object : Callback<NoSmokeDto> {
                    override fun onResponse(
                        call: Call<NoSmokeDto>,
                        response: Response<NoSmokeDto>
                    ) {
                        if(!response.isSuccessful) {
                            return
                        }
                        response.body()?.let { dto ->

                            updateMarker2(dto.Data)

                        }
                    }

                    override fun onFailure(call: Call<NoSmokeDto>, t: Throwable) {

                    }
                })
        }
    }

    private fun updateMarker2(nosmokes: List<NoSmokeArea>){
        nosmokes.forEach {
            nosmoke -> val marker = Marker()
            marker.position = LatLng(nosmoke.longitude, nosmoke.latitude)
            marker.map = naverMap

        }
    }


    override fun onMapReady(map: NaverMap) {
        naverMap = map


        naverMap.maxZoom = 18.0
        naverMap.minZoom = 10.0




        // 현재 위치를 찾는 버튼 활성화
        val uiSetting = naverMap.uiSettings
        uiSetting.isLocationButtonEnabled = false


        binding.currentLocationButton.map = naverMap

        // 위치를 반환하는 FusedLocationSource 생성
        locationSource = FusedLocationSource(this@NaverFragment2, LOCATION_PERMISSION_REQUEST_CODE)
        // 위치소스 지정
        naverMap.locationSource = locationSource


        getNoSmokeListAPI()

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


