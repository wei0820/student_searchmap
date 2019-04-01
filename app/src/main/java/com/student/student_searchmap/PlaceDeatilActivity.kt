package com.student.student_searchmap

import android.os.Bundle
import android.support.v7.app.AppCompatActivity;
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
                picasso.load(GoogleMapAPISerive.getPhotos(this,url)).into(mImageView)
                mRatingBar.numStars = 5
                mRatingBar.rating = googleMapPlaceDetailsData.result.rating

                mNameText.text = "名稱：" + googleMapPlaceDetailsData.result.name
                mAddressText.text = "地址：" + googleMapPlaceDetailsData.result.formatted_address
                mPhoneText.text = "聯絡電話：" + googleMapPlaceDetailsData.result.formatted_phone_number
                if (googleMapPlaceDetailsData.result.opening_hours != null) {
                    if (googleMapPlaceDetailsData.result.opening_hours.open_now) {
                        mOpenNowText.text = "目前營業中!"
                    } else {
                        mOpenNowText.text = "目前沒有營業!"

                    }
                }

                if (googleMapPlaceDetailsData.result.opening_hours != null && googleMapPlaceDetailsData.result.opening_hours.weekday_text != null) {
                    mOPenText.text = "營業時間：" + "\n" + googleMapPlaceDetailsData.result.opening_hours.weekday_text[0] + "\n" + googleMapPlaceDetailsData.result.opening_hours.weekday_text[1] + "\n" + googleMapPlaceDetailsData.result.opening_hours.weekday_text[2] + "\n" +
                            googleMapPlaceDetailsData.result.opening_hours.weekday_text[3] + "\n" + googleMapPlaceDetailsData.result.opening_hours.weekday_text[4] + "\n" + googleMapPlaceDetailsData.result.opening_hours.weekday_text[5] + "\n" + googleMapPlaceDetailsData.result.opening_hours.weekday_text[6]

                }
            }
        }
    }
    lateinit var mReViewListView: LinearLayout
    lateinit var mImageView: ImageView
    lateinit var mNameText: TextView
    lateinit var mAddressText: TextView
    lateinit var mPhoneText: TextView
    lateinit var mOpenNowText: TextView
    lateinit var mOPenText: TextView
    lateinit var mRatingBar: RatingBar
    var url :String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.content_place_deatil)
        getid()
        initlayout()
    }
    fun initlayout(){
        mImageView = findViewById(R.id.viewpage)
        mNameText = findViewById(R.id.nametext)
        mAddressText = findViewById(R.id.addresstext)
        mPhoneText = findViewById(R.id.phonetext)
        mOpenNowText = findViewById(R.id.opennowtext)
        mOPenText = findViewById(R.id.opentext)
        mRatingBar = findViewById(R.id.rating)
    }

    fun getid() {
        val id: String = intent.extras.getString("id")
        url = intent.extras.getString("tag")
        GoogleMapAPISerive.getPlaceDeatail(this, id, this)

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
