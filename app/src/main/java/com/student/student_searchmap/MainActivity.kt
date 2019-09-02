package com.student.student_searchmap

import android.app.ProgressDialog
import android.content.Intent
import android.graphics.BitmapFactory
import android.location.Geocoder
import android.media.Image
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.gson.Gson
import com.hendraanggrian.pikasso.picasso
import com.student.student_searchmap.Data.ResponseData
import java.util.*

class MainActivity : AppCompatActivity() {
    lateinit var img :ImageView
    lateinit var add :TextView
    lateinit var select:TextView
    lateinit var phone :TextView
    lateinit var price :TextView
    lateinit var time :TextView
    lateinit var message :TextView
    lateinit var button: Button
    lateinit var mProgressDialog : ProgressDialog

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
            val phone :String = responseData.phone.replaceFirst("0","+886")
            sendSMS(phone)
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

    protected fun sendSMS(phone:String) {
        Log.i("Send SMS", "")

        val smsIntent = Intent(Intent.ACTION_VIEW)
        smsIntent.data = Uri.parse("smsto:")
        smsIntent.type = "vnd.android-dir/mms-sms"

        smsIntent.putExtra("address", phone)
        smsIntent.putExtra("sms_body", "Test SMS to Angilla")
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
