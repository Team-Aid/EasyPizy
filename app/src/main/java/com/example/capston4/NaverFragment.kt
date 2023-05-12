package com.example.capston4

import android.app.Application
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.capston4.databinding.NaverFragmentBinding



import com.naver.maps.geometry.LatLng
import com.naver.maps.map.*
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.Overlay
import com.naver.maps.map.util.FusedLocationSource

import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

class MyApp: Application() {
    lateinit var context: Context
    init {instance=this}
    companion object {
        private var instance: MyApp? = null
        fun applicationContext(): Context {
            return instance!!.applicationContext
        }
    }
}

class NaverFragment : FragmentActivity(), OnMapReadyCallback, Overlay.OnClickListener {
    private lateinit var binding: NaverFragmentBinding
    private lateinit var naverMap: NaverMap
    private lateinit var locationSource: FusedLocationSource

   // private val recyclerView: RecyclerView by lazy {
        //findViewById(R.id.recyclerView)
   // }
   // private val recycleAdapter = HouseListAdapter()
    private val viewPagerAdapter = HouseViewPagerAdapter()



    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = NaverFragmentBinding.inflate(layoutInflater)
        setContentView(binding.root)




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

        binding.houseViewPager.adapter = viewPagerAdapter
       // recyclerView.adapter = recycleAdapter
       // recyclerView.layoutManager = LinearLayoutManager(this)

        binding.houseViewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)

                val selectedHouseModel = viewPagerAdapter.currentList[position]
                val cameraUpdate = CameraUpdate.scrollTo(LatLng(selectedHouseModel.longitude, selectedHouseModel.latitude))
                    .animate(CameraAnimation.Easing)
                naverMap.moveCamera(cameraUpdate)

            }
        })

    }





    private fun getSmokeListAPI() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://run.mocky.io")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(SmokeApi::class.java).also {
            it.getAllSmoke()
                .enqueue(object : Callback<SmokeDto>{
                    override fun onResponse(
                        call: Call<SmokeDto>,
                        response: Response<SmokeDto>
                    ) {
                        if(!response.isSuccessful) {
                            return
                        }
                        response.body()?.let { dto ->
                            updateMarker(dto.data)
                            viewPagerAdapter.submitList(dto.data)
                           // recycleAdapter.submitList(dto.data)
                        }
                    }

                    override fun onFailure(call: Call<SmokeDto>, t: Throwable) {

                    }
                })
        }
    }

    private fun updateMarker(smokes: List<SmokeArea>) {
        smokes.forEach {
            smoke -> val marker = Marker()
            marker.position = LatLng(smoke.longitude, smoke.latitude)
            marker.map = naverMap
            marker.tag = smoke.areaName
            marker.onClickListener = this

        }
    }

    override fun onClick(overlay: Overlay): Boolean {
        val selectedModel = viewPagerAdapter.currentList.firstOrNull {
            it.areaName == overlay.tag
        }
        selectedModel?.let {
            val position = viewPagerAdapter.currentList.indexOf(it)
            binding.houseViewPager.currentItem = position
        }
        return true


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
        locationSource = FusedLocationSource(this@NaverFragment, LOCATION_PERMISSION_REQUEST_CODE)
        // 위치소스 지정
        naverMap.locationSource = locationSource
      getSmokeListAPI()

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


