package com.student.student_searchmap

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.student.student_searchmap.Data.GoogleMapPlaceDetailsData
import com.student.student_searchmap.Data.GoogleResponseData

class MyMapActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnCameraMoveListener, GoogleMap.OnCameraMoveCanceledListener
        , LocationListener, GoogleMap.OnCameraMoveStartedListener, GoogleMap.OnCameraIdleListener, View.OnClickListener, GoogleMapAPISerive.GetResponse {


    override fun getData(googleResponseData: GoogleResponseData?) {
        var icon: Int = R.drawable.ic_local_parking_black_24dp
        if (googleResponseData != null) {
            for (result in googleResponseData.results) {
                if (result.vicinity == null) {
                    result.vicinity = "no address"
                }
                if (result.types.size != 0) {

                    when (result.types[0]) {
                        GoogleMapAPISerive.TYPE_PARKING -> {

                            addMarker(LatLng(result.geometry.location.lat, result.geometry.location.lng),
                                    result.name,
                                    result.vicinity,R.drawable.ic_park_car,result.place_id)
                        }
                        GoogleMapAPISerive.TYPE_CARWASH -> {

                            addMarker(LatLng(result.geometry.location.lat, result.geometry.location.lng),
                                    result.name,
                                    result.vicinity,R.drawable.ic_wash_car,result.place_id)
                        }
                        GoogleMapAPISerive.TYPE_CARREPAIR -> {
                            addMarker(LatLng(result.geometry.location.lat, result.geometry.location.lng),
                                    result.name,
                                    result.vicinity,R.drawable.ic_fix_car,result.place_id)

                        }
                    }


                }




            }
        }
    }


    override fun getDetailData(googleMapPlaceDetailsData: GoogleMapPlaceDetailsData?) {

    }


    override fun onClick(p0: View?) {
        when (p0!!.id) {

            R.id.button_1 -> {
                mMap.clear()
                setToast("搜尋停車場")
                GoogleMapAPISerive.setPlaceForRestaurant(this@MyMapActivity, latlon, GoogleMapAPISerive.TYPE_PARKING, this@MyMapActivity)

            }
            R.id.button_2 -> {
                mMap.clear()
                setToast("搜尋保養廠")
                GoogleMapAPISerive.setPlaceForRestaurant(this@MyMapActivity, latlon, GoogleMapAPISerive.TYPE_CARREPAIR, this@MyMapActivity)


            }
            R.id.button_3 -> {
                mMap.clear()
                setToast("搜尋洗車場")
                GoogleMapAPISerive.setPlaceForRestaurant(this@MyMapActivity, latlon, GoogleMapAPISerive.TYPE_CARWASH, this@MyMapActivity)


            }

        }
    }

    override fun onCameraMove() {

    }

    override fun onCameraMoveCanceled() {

    }

    override fun onLocationChanged(p0: Location?) {

    }

    override fun onCameraMoveStarted(p0: Int) {
    }

    override fun onCameraIdle() {
        latlon = mMap.cameraPosition.target.latitude.toString() + "," + mMap.cameraPosition.target.longitude.toString()

    }

    private lateinit var mMap: GoogleMap
    private var locationManager: LocationManager? = null
    val MY_PERMISSIONS_REQUEST_LOCATION = 100
    lateinit var mButton: Button
    lateinit var mButton2: Button
    lateinit var mButton3: Button
    lateinit var latlon: String
    var mFistBoolean : Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_map)
        checkPermission()
        initLayout()
    }
    fun  setToast(s:String){


        Toast.makeText(this@MyMapActivity,s,Toast.LENGTH_SHORT).show()

    }
    @SuppressLint("ServiceCast")
    fun initLayout() {
        mButton = findViewById(R.id.button_1)
        mButton2 = findViewById(R.id.button_2)
        mButton3 = findViewById(R.id.button_3)
        mButton.setOnClickListener(this)
        mButton2.setOnClickListener(this)
        mButton3.setOnClickListener(this)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        locationManager = getSystemService(LOCATION_SERVICE) as LocationManager?

        try {
            // Request location updates
            locationManager?.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0L, 0f, locationListener)
        } catch (ex: SecurityException) {

        }

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */

    // 在地圖加入指定位置與標題的標記
    private fun addMarker(place: LatLng, title: String, context: String, int: Int,id:String) {
        val markerOptions = MarkerOptions()
        val marker :Marker
        markerOptions.position(place)
                .title(title)
                .snippet(context)
                .icon(BitmapDescriptorFactory.fromResource(int))
        marker =  mMap.addMarker(markerOptions)
        marker.tag = id
    }

    @SuppressLint("MissingPermission")
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.setMyLocationEnabled(true)
        mMap.setOnCameraMoveStartedListener(this)
        mMap.setOnCameraMoveListener(this)
        mMap.setOnCameraMoveCanceledListener(this)
        mMap.setOnCameraIdleListener(this)
        mMap.setOnMarkerClickListener(gmapListener)

    }
    private val gmapListener = GoogleMap.OnMarkerClickListener { marker ->
        marker.showInfoWindow()
        // 用吐司顯示註解
        Log.d("Jack",marker.tag.toString())
//        GoogleMapAPISerive.getPlaceDeatail(this@MyMapActivity,marker.tag.toString(),this)

//        Log.d("Jack",marker.position.latitude.toString())
//        Log.d("Jack",marker.position.longitude.toString())
//        Log.d("Jack",Distance(mLat,mLon,marker.position.latitude,marker.position.longitude).toString())
//        Toast.makeText(this@MapsActivity,
//                "距離"+Distance(mLat,mLon,marker.position.latitude,marker.position.longitude)+
//                        "公尺",Toast.LENGTH_SHORT).show()

        true
    }

    private val locationListener: LocationListener = object : LocationListener {
        override fun onStatusChanged(p0: String?, p1: Int, p2: Bundle?) {

        }

        override fun onProviderEnabled(p0: String?) {
        }

        override fun onProviderDisabled(p0: String?) {
        }

        override fun onLocationChanged(location: Location) {
            if (mFistBoolean) {
                latlon = location.latitude.toString() + "," + location.longitude.toString()

                GoogleMapAPISerive.setPlaceForRestaurant(this@MyMapActivity, latlon, GoogleMapAPISerive.TYPE_PARKING, this@MyMapActivity)

                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(LatLng(location.latitude, location.longitude), 18.0f))
                mFistBoolean = false
            }
        }


    }

    override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
    override fun onProviderEnabled(provider: String) {}
    override fun onProviderDisabled(provider: String) {}
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == MY_PERMISSIONS_REQUEST_LOCATION) {

            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            } else {
                Toast.makeText(this, "需要定位功能,才能使用喔", Toast.LENGTH_SHORT).show()
                return
            }
        }
    }

    fun checkPermission() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), MY_PERMISSIONS_REQUEST_LOCATION)
        }

    }


}
