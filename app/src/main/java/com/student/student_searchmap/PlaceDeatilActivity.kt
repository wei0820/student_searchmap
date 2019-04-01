package com.student.student_searchmap

import android.os.Bundle
import android.support.v7.app.AppCompatActivity;
import android.util.Log
import com.student.student_searchmap.Data.GoogleMapPlaceDetailsData
import com.student.student_searchmap.Data.GoogleResponseData


class PlaceDeatilActivity : AppCompatActivity(), GoogleMapAPISerive.GetResponse {
    override fun getData(googleResponseData: GoogleResponseData?) {

    }

    override fun getDetailData(googleMapPlaceDetailsData: GoogleMapPlaceDetailsData?) {
        if (googleMapPlaceDetailsData != null) {
            if (googleMapPlaceDetailsData.result != null) {

                if (googleMapPlaceDetailsData.result.photos != null && googleMapPlaceDetailsData.result.photos.size != 0) {

                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.content_place_deatil)
        getid()
    }

    fun getid() {
        val id: String = intent.extras.getString("id")
        val url :String = intent.extras.getString("tag")
        Log.d("PlaceDeatilActivity",id)
        Log.d("PlaceDeatilActivity",url)


        Log.d("PlaceDeatilActivity",GoogleMapAPISerive.getPhotos(this,url))

//        GoogleMapAPISerive.getPlaceDeatail(this, id, this)

    }

}
