package com.student.student_searchmap.Data

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.widget.Toast
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.BitmapDescriptorFactory

import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.gson.Gson
import com.jackpan.libs.mfirebaselib.MfiebaselibsClass
import com.jackpan.libs.mfirebaselib.MfirebaeCallback
import com.student.student_searchmap.MainActivity
import com.student.student_searchmap.R
import java.util.*

class Map2Activity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnCameraMoveListener, GoogleMap.OnCameraMoveCanceledListener
        , LocationListener, GoogleMap.OnCameraMoveStartedListener, GoogleMap.OnCameraIdleListener, View.OnClickListener , MfirebaeCallback {


    override fun onCameraMove() {

    }

    override fun onCameraMoveCanceled() {

    }

    override fun onLocationChanged(p0: Location?) {

    }

    override fun onCameraMoveStarted(p0: Int) {
    }

    override fun onCameraIdle() {
//        latlon = mMap.cameraPosition.target.latitude.toString() + "," + mMap.cameraPosition.target.longitude.toString()

    }
    override fun onClick(p0: View?) {
    }
    override fun getUserLogoutState(p0: Boolean) {
    }

    override fun resetPassWordState(p0: Boolean) {
    }

    override fun getsSndPasswordResetEmailState(p0: Boolean) {
    }

    override fun getFirebaseStorageType(p0: String?, p1: String?) {
    }

    override fun getUpdateUserName(p0: Boolean) {
    }

    override fun getDatabaseData(p0: Any?) {
        if (p0!=null){
            val gson = Gson()
            val jsonInString = gson.toJson(p0)
            val responseData :ResponseData = gson.fromJson(jsonInString,ResponseData::class.java)
            addMarker(LatLng(responseData.lat.toDouble(), responseData.lon.toDouble()),responseData.id,responseData.message,jsonInString)

            handleLocation(responseData.lat.toDouble(), responseData.lon.toDouble())

            mProgressDialog.dismiss()

        }


    }

    override fun getuserLoginEmail(p0: String?) {
    }

    override fun getDeleteState(p0: Boolean, p1: String?, p2: Any?) {
    }

    override fun getFireBaseDBState(p0: Boolean, p1: String?) {
    }

    override fun getuseLoginId(p0: String?) {
    }

    override fun createUserState(p0: Boolean) {
    }

    override fun useLognState(p0: Boolean) {
    }

    override fun getFirebaseStorageState(p0: Boolean) {
    }

    private lateinit var mMap: GoogleMap
    private var locationManager: LocationManager? = null
    val MY_PERMISSIONS_REQUEST_LOCATION = 100
    lateinit var latlon: String
    lateinit var latlonNow: String

    var mFistBoolean : Boolean = true
    lateinit var mFirebselibClass: MfiebaselibsClass
    lateinit var mProgressDialog : ProgressDialog
    var lat : Double = 0.0;
    var lon :Double = 0.0
    var type:Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mFirebselibClass = MfiebaselibsClass(this, this)

        setContentView(R.layout.activity_maps)
        checkPermission()
        MapsInitializer.initialize(applicationContext)

        getData()

        initLayout()

        mProgressDialog = ProgressDialog(this)

        mProgressDialog.setMessage("loading")
        mProgressDialog.setCancelable(false)
        mProgressDialog.show()
        mFirebselibClass.getFirebaseDatabase(ResponseData.KEY_URL+ intent.extras.getInt("type"), "data")
        Log.d("Jack",ResponseData.KEY_URL+type)

    }
    fun getData(){
        if (intent.extras.getDouble("lat")!=null&&intent.extras.getDouble("lon")!=null){
            lat=  intent.extras.getDouble("lat")
            lon =intent.extras.getDouble("lon")
        }
    }


    @SuppressLint("ServiceCast")
    fun initLayout() {
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
//        locationManager = getSystemService(LOCATION_SERVICE) as LocationManager?
//
//        try {
//            // Request location updates
//            locationManager?.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0L, 0f, locationListener)
//        } catch (ex: SecurityException) {
//
//        }

        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera. In this case,
         * we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to install
         * it inside the SupportMapFragment. This method will only be triggered once the user has
         * installed Google Play services and returned to the app.
         */

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
        mMap.setOnMarkerClickListener(gmapListener)
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(LatLng(lat, lon), 12.0f))


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
                latlonNow = latlon
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
    // 在地圖加入指定位置與標題的標記
    private fun addMarker(place: LatLng, title: String, context: String,json:String) {
        val markerOptions = MarkerOptions()
        val marker : Marker
        markerOptions.position(place)
                .title(title)
                .snippet(context)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_park_car))
        marker =  mMap.addMarker(markerOptions)
        marker.tag = json

    }
    private val gmapListener = GoogleMap.OnMarkerClickListener { marker ->
        marker.showInfoWindow()
        // 用吐司顯示註解
        val json :String  = marker.tag.toString()
        val intent = Intent()
        val bundle = Bundle()
        intent.setClass(this, MainActivity::class.java)
        bundle.putString("type",type.toString())
        bundle.putString("json",json)
        intent.putExtras(bundle)
        startActivity(intent)




        true
    }
    private fun handleLocation(latitude: Double,longitude:Double)  {
        val geocoder = Geocoder(this, Locale.getDefault())
        Thread(Runnable {
            try {
                var addresses = geocoder.getFromLocation(
                        latitude, longitude, 1
                )
//                textView.text = addresses.get(0).getAddressLine(0)
//                mProgressDialog.dismiss()
                Log.d("Jack",addresses.get(0).getAddressLine(0))
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }).start()
    }

    override fun onKeyUp(keyCode: Int, event: KeyEvent?): Boolean {

        if (keyCode == KeyEvent.KEYCODE_BACK ){
            this.finish()
        }

        return super.onKeyUp(keyCode, event)
    }


}
