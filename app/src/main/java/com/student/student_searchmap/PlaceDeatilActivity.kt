package com.student.student_searchmap

import android.content.Context
import android.os.Bundle
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
//                picasso.load(GoogleMapAPISerive.getPhotos(this,url)).into(mImageView)
                mRatingBar.numStars = 5
                mRatingBar.rating = googleMapPlaceDetailsData.result.rating

                mNameText.text = "名稱：" + googleMapPlaceDetailsData.result.name
                mAddressText.text = "地址：" + googleMapPlaceDetailsData.result.formatted_address
                if (googleMapPlaceDetailsData.result.formatted_phone_number!=null&&!googleMapPlaceDetailsData.result.formatted_phone_number.equals("null")){
                    mPhoneText.text = "聯絡電話：" + googleMapPlaceDetailsData.result.formatted_phone_number

                }else{
                    mPhoneText.text = "尚未提供電話"

                }
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

                if (googleMapPlaceDetailsData.result.photos != null) {
                    for (photos in googleMapPlaceDetailsData.result.photos) {
                        var photoString: String = GoogleMapAPISerive.getPhotos(this, photos.photo_reference)
                        mPhotoData.add(photoString)


                    }
                }else{
                    mPhotoData.add(GoogleMapAPISerive.getPhotos(this,url))
                }
                mImagePagerAdapter.notifyDataSetChanged()

                if (googleMapPlaceDetailsData.result.reviews != null) {
                    for (review in googleMapPlaceDetailsData.result.reviews) {
                        addnewLayout(review)


                    }


                }

            }
        }
    }
    lateinit var mReViewListView: LinearLayout
    lateinit var mViewPage: ViewPager
    lateinit var mNameText: TextView
    lateinit var mAddressText: TextView
    lateinit var mPhoneText: TextView
    lateinit var mOpenNowText: TextView
    lateinit var mOPenText: TextView
    lateinit var mRatingBar: RatingBar
    var url :String = ""
    var mPhotoData: ArrayList<String> = ArrayList()
    lateinit var mImagePagerAdapter: ImagePagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.content_place_deatil)
        getid()
        initlayout()
    }
    fun initlayout(){
        mViewPage = findViewById(R.id.viewpage)
        mImagePagerAdapter = ImagePagerAdapter(this, mPhotoData)
        mViewPage.adapter = mImagePagerAdapter
        mNameText = findViewById(R.id.nametext)
        mAddressText = findViewById(R.id.addresstext)
        mPhoneText = findViewById(R.id.phonetext)
        mOpenNowText = findViewById(R.id.opennowtext)
        mOPenText = findViewById(R.id.opentext)
        mRatingBar = findViewById(R.id.rating)
        mReViewListView = findViewById(R.id.reviewlistview)

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
    inner class ImagePagerAdapter(internal var context: Context, internal var arrayList: ArrayList<String>?) : PagerAdapter() {
        internal var layoutInflater: LayoutInflater

        init {
            layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        }

        override fun getCount(): Int {
            return if (arrayList != null) {
                arrayList!!.size
            } else 0
        }

        override fun isViewFromObject(view: View, `object`: Any): Boolean {
            return view === `object` as LinearLayout
        }

        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            val itemView = layoutInflater.inflate(R.layout.image_viewpager_layout, container, false)
            val imageView = itemView.findViewById<ImageView>(R.id.viewPagerItem_image1)
            picasso.load(arrayList!![position]).error(R.mipmap.ic_launcher_round).into(imageView)
            container.addView(itemView)

            return itemView
        }

        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            container.removeView(`object` as LinearLayout)
        }

    }
}
