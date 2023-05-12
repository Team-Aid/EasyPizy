package com.example.capston4

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.capston4.databinding.ActivityMainBinding
import com.example.capston4.databinding.NaverFragmentBinding
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.*
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.Overlay
import com.naver.maps.map.util.FusedLocationSource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : FragmentActivity(), OnMapReadyCallback {
    private lateinit var binding: NaverFragmentBinding
    private lateinit var naverMap: NaverMap
    private lateinit var locationSource: FusedLocationSource
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = NaverFragmentBinding.inflate(layoutInflater)
        setContentView(binding.root)



        binding.radioButton.setOnClickListener {

            val intent = Intent(this, NaverFragment::class.java)
            startActivity(intent)


        }
        binding.radioButton2.setOnClickListener {
            val intent = Intent(this, NaverFragment2::class.java)
            startActivity(intent)

        }
        val fm = supportFragmentManager
        val mapFragment = fm.findFragmentById(R.id.map) as MapFragment?
            ?: MapFragment.newInstance().also {
                fm.beginTransaction().add(R.id.map, it).commit()
            }

        mapFragment.getMapAsync(this)




    }



    override fun onMapReady(map: NaverMap) {
        naverMap = map

        naverMap.maxZoom = 18.0
        naverMap.minZoom = 10.0


        // 지도 초기 위치
        //val cameraUpdate = CameraUpdate.scrollTo(LatLng(37.497885,127.02751))
        // naverMap.moveCamera(cameraUpdate)

        // 현재 위치를 찾는 버튼 활성화
        val uiSetting = naverMap.uiSettings
        uiSetting.isLocationButtonEnabled = false


        binding.currentLocationButton.map = naverMap

        // 위치를 반환하는 FusedLocationSource 생성
        locationSource = FusedLocationSource(this@MainActivity, LOCATION_PERMISSION_REQUEST_CODE)
        // 위치소스 지정
        naverMap.locationSource = locationSource


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



