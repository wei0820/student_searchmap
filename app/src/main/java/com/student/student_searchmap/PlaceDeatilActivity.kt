package com.student.student_searchmap

import android.os.Bundle
import android.support.v7.app.AppCompatActivity;
import android.util.Log
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RatingBar
import android.widget.TextView
import com.hendraanggrian.pikasso.picasso
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
    lateinit var mReViewListView: LinearLayout

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
    fun addnewLayout(data: GoogleMapPlaceDetailsData.Result.Reviews) {
        val view = getLayoutInflater().inflate(R.layout.review_layout, null)
        var mReviewImg: ImageView = view!!.findViewById(R.id.reviewimg)
        var mReviewName: TextView = view!!.findViewById(R.id.reviewname)
        var mReviewText: TextView = view!!.findViewById(R.id.reviewtext)
        var mReviewTime: TextView = view!!.findViewById(R.id.reviewtimetext)
        var mReviewRating: RatingBar = view!!.findViewById(R.id.rating)

        if (data.profile_photo_url != null) {
            picasso.load(data.profile_photo_url).error(R.mipmap.ic_launcher).into(mReviewImg)
        }
        mReviewName.text = data.author_name
        mReviewText.text = data.text
        mReviewRating.rating = data.rating
        mReViewListView.addView(view)


    }

}
