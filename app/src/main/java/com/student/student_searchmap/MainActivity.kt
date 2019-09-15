package com.student.student_searchmap

import android.app.ProgressDialog
import android.content.Intent
import android.graphics.BitmapFactory
import android.location.Geocoder
import android.media.Image
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.format.DateFormat
import android.util.Base64
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.android.gms.maps.model.LatLng
import com.google.gson.Gson
import com.hendraanggrian.pikasso.picasso
import com.jackpan.libs.mfirebaselib.MfiebaselibsClass
import com.jackpan.libs.mfirebaselib.MfirebaeCallback
import com.student.student_searchmap.Data.ResponseData
import java.util.*
import com.firebase.client.Firebase



class MainActivity : AppCompatActivity(), MfirebaeCallback {
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


    }

    override fun getuserLoginEmail(p0: String?) {
    }

    override fun getDeleteState(p0: Boolean, p1: String?, p2: Any?) {
        if (p0) {
            Toast.makeText(this, "刪除成功！", Toast.LENGTH_SHORT).show();
            mFirebselibClass.getFirebaseDatabase(ResponseData.KEY_URL, "data")
        } else {
            Toast.makeText(this, "刪除失敗！", Toast.LENGTH_SHORT).show();
        }    }

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
    lateinit var img :ImageView
    lateinit var add :TextView
    lateinit var select:TextView
    lateinit var phone :TextView
    lateinit var price :TextView
    lateinit var time :TextView
    lateinit var message :TextView
    lateinit var button: Button
    lateinit var mProgressDialog : ProgressDialog
    lateinit var mFirebselibClass: MfiebaselibsClass

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContentView(R.layout.activity_main)
        img = findViewById(R.id.img)
        add  =findViewById(R.id.add)
        select = findViewById(R.id.select)
        phone = findViewById(R.id.phone)
        price = findViewById(R.id.price)
        time =findViewById(R.id.time)
        message = findViewById(R.id.message)
        button = findViewById(R.id.button)

//        mProgressDialog = ProgressDialog(this)
//
//        mProgressDialog.setMessage("loading")
//        mProgressDialog.setCancelable(false)
//        mProgressDialog.show()
        getData()

    }
    fun  getData(){
        val jsonString: String = intent.extras.getString("json")

        val gson =Gson()
        val responseData = gson.fromJson(jsonString,ResponseData::class.java)
        if (responseData.url!=null){
            decode(responseData.url)

        }
        select.text = responseData.select
        phone.text = responseData.phone
        price.text =responseData.price
        time.text = "開始:"+ responseData.starttime+"~"+"結束:"+ responseData.endtime
        message.text = responseData.message
        handleLocation(responseData.lat.toDouble(),responseData.lon.toDouble(),add)

        button.setOnClickListener {
            Toast.makeText(this,"預約成功",Toast.LENGTH_SHORT).show()

            val phone :String = responseData.phone.replaceFirst("0","+886")
            val firebase = Firebase(ResponseData.KEY_URL)
            firebase.child(responseData.date).removeValue();
            val mCal = Calendar.getInstance()
            val s = DateFormat.format("yyyy-MM-dd kk:mm:ss", mCal.getTime());
            sendSMS(phone,s.toString(),responseData.id)
        }


    }
    fun decode(imageString: String) {

        //decode base64 string to image
        val imageBytes = Base64.decode(imageString, Base64.DEFAULT)
        val decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)

        img.setImageBitmap(decodedImage)
    }
    private fun handleLocation(latitude: Double,longitude:Double,textView: TextView)  {
        val geocoder = Geocoder(this, Locale.getDefault())
        Thread(Runnable {
            try {
                var addresses = geocoder.getFromLocation(
                        latitude, longitude, 1
                )
                textView.text = addresses.get(0).getAddressLine(0)
//                mProgressDialog.dismiss()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }).start()
    }

    protected fun sendSMS(phone:String,time:String,id:String) {

        val smsIntent = Intent(Intent.ACTION_VIEW)
        smsIntent.data = Uri.parse("smsto:")
        smsIntent.type = "vnd.android-dir/mms-sms"

        smsIntent.putExtra("address", phone)
        smsIntent.putExtra("sms_body", "親愛的客戶您好:謝謝您使用停車App於"+time+"預租停車位,如有疑義,請洽詢客服信箱 ya_1827@yahoo.com.tw")
        try {
            startActivity(smsIntent)
            finish()
            Log.i("Finished sending SMS...", "")
        } catch (ex: android.content.ActivityNotFoundException) {
            Toast.makeText(this,
                    "SMS faild, please try again later.", Toast.LENGTH_SHORT).show()
        }

    }
}
