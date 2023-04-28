package com.example.easypizy.presentation

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.PermissionChecker
import com.example.easypizy.databinding.ActivityMainBinding

import com.naver.maps.geometry.LatLng
import com.naver.maps.map.*
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.util.FusedLocationSource
import org.json.JSONObject

import retrofit2.*


class NaverFragment : AppCompatActivity(), /*OnMapReadyCallback*/ LocationListener {

    private lateinit var naverMap: NaverMap
    private lateinit var locationSource: FusedLocationSource
    private var map: NaverMap? = null
    private var locationManager: LocationManager? = null

    //viewBinding 사용!
    private var mBinding: ActivityMainBinding? = null
    private val binding get() = mBinding!!


    companion object {
        private const val PERMISSION_REQUEST_CODE = 100
        private val PERMISSIONS = arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
    }


    //권한 가져오기

    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        //TODO
//        setContentView(R.layout.naver_fragment)
//
//        val fm = supportFragmentManager
//        val mapFragment = fm.findFragmentById(R.id.map) as MapFragment?
//            ?: MapFragment.newInstance().also {
//                fm.beginTransaction().add(R.id.map, it).commit()
//            }
//        mapFragment.getMapAsync { naverMap ->
//            map = naverMap
//        }

        locationManager = getSystemService(LOCATION_SERVICE) as LocationManager?

        locationSource = FusedLocationSource(this, PERMISSION_REQUEST_CODE)

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (hasPermission()) {
                if (ActivityCompat.checkSelfPermission(
                        this,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                        this,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    return
                }
                //TODO
//                locationManager?.requestLocationUpdates(
//                    LocationManager.GPS_PROVIDER,
//                    1000,
//                    10f,
//                    this
//                )
            }
            return
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onStart() {
        super.onStart()

        if (hasPermission()) {
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {

                return
            }
            //TODO
            //locationManager?.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 10f, this)
        } else {
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_REQUEST_CODE)
        }

    }
    /* override fun onMapReady(p0: NaverMap) {

     } */

    override fun onStop() {
        super.onStop()
        locationManager?.removeUpdates(this)
    }

    override fun onLocationChanged(location: Location) {
        // Condition 'location == null' is always 'false'
//        if (location == null) {
//            return
//        }
        map?.let {

            val coord = LatLng(location)

            val locationOverlay = it.locationOverlay
            locationOverlay.isVisible = true
            locationOverlay.position = coord
            locationOverlay.bearing = location.bearing

            it.moveCamera(CameraUpdate.scrollTo(coord))

            map?.locationSource = locationSource
            map?.locationTrackingMode = LocationTrackingMode.Follow

            val uiSettings = map?.uiSettings
            uiSettings?.isLocationButtonEnabled = true
            uiSettings?.isCompassEnabled = true
            uiSettings?.isRotateGesturesEnabled = true

            val jsonString = assets.open("yongsan.json").reader().readText() //용산구 흡연구역 json파일
            val jsonString2 = assets.open("sungbok.json").reader().readText() //성북구 흡연구역 json파일

            val jsonObject = JSONObject(jsonString)
            val jsonObject2 = JSONObject(jsonString2)


            val jsonArray = jsonObject.getJSONArray("data")
            val jsonArray2 = jsonObject2.getJSONArray("data")

            for (i in 0 until jsonArray2.length()) {
                val lng2 = jsonArray2.getJSONObject(i).getString("경도")
                val lngnum2: Double = lng2.toDouble()
                Log.i("경도 ", lng2)

                val location2 = jsonArray2.getJSONObject(i).getString("서울특별시 성북구 설치 위치")
                Log.i("위치", location2)

                val inout2 = jsonArray2.getJSONObject(i).getString("시설형태")
                Log.i("시설형태", inout2)

                val lat2 = jsonArray2.getJSONObject(i).getString("위도")
                val latnum2: Double = lat2.toDouble()
                Log.i("위도", lat2)


                val mutableList = mutableListOf(latnum2, lngnum2)
                Log.i("확인", mutableList.toString())

                for (sungbok in mutableList) {
                    val latlng = LatLng(latnum2, lngnum2)
                    val marker = Marker()
                    marker.position = latlng
                    marker.map = map
                }


            }


            for (i in 0 until jsonArray.length()) {
                val lng = jsonArray.getJSONObject(i).getString("경도")
                val lngnum: Double = lng.toDouble()
                Log.i("경도 ", lng)


                val locationInItem = jsonArray.getJSONObject(i).getString("서울특별시 용산구 설치 위치")
                Log.i("위치", locationInItem)

                val inout = jsonArray.getJSONObject(i).getString("시설형태")
                Log.i("시설형태", inout)

                val lat = jsonArray.getJSONObject(i).getString("위도")
                val latnum: Double = lat.toDouble()
                Log.i("위도", lat)


                val mutableList = mutableListOf(latnum, lngnum)
                Log.i("확인", mutableList.toString())

                for (yongsan in mutableList) {
                    val latlng = LatLng(latnum, lngnum)
                    val marker = Marker()
                    marker.position = latlng
                    marker.map = map

                    /* marker.icon = OverlayImage.fromResource(R.drawable.baseline_smoking_rooms_24) */

                }


            }

        }
    }

    /*  fun showLocation() {
          val name = mutableListOf<YongsanDtoItem>()
          for (yongsan in name) {
              val latLng = LatLng(yongsan.lat, yongsan.lng)
              val marker = Marker()
              marker.position = latLng
              marker.map = map
          }

      }

       fun locationService() {
           val retrofit = SmokeClient.getInstance()

           val service = retrofit.create(SmokeApi::class.java)


           service.getAllSmoke()
               .enqueue(object: Callback<YongsanDtoItem>{
                   override fun onResponse(
                       call: Call<YongsanDtoItem>,
                       response: Response<YongsanDtoItem>
                   ) {
                       Log.d("성공", response.body().toString())
                   }

                   override fun onFailure(call: Call<YongsanDtoItem>, t: Throwable) {
                       Log.d("SmokeClient", "에러: " + t.message.toString())
                   }
               })

       }*/


    override fun onStatusChanged(
        provider: String, status: Int,
        extras: Bundle
    ) {
    }

    override fun onProviderEnabled(provider: String) {
    }

    override fun onProviderDisabled(provider: String) {
    }

    private fun hasPermission(): Boolean {
        return PermissionChecker.checkSelfPermission(this, PERMISSIONS[0]) ==
                PermissionChecker.PERMISSION_GRANTED &&
                PermissionChecker.checkSelfPermission(this, PERMISSIONS[1]) ==
                PermissionChecker.PERMISSION_GRANTED
    }


}







