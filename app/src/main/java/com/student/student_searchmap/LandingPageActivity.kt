package com.student.student_searchmap

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import java.util.*


class LandingPageActivity : Activity() {
    lateinit var  mTextView :TextView
    lateinit var    mRelativeLayout:RelativeLayout
    var mChange :Boolean = false
    private lateinit var managePermissions: ManagePermissions

    private val MY_PERMISSIONS_REQUEST_LOCATION = 1
    val list = listOf<String>(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.INTERNET,
            Manifest.permission.ACCESS_WIFI_STATE,
            Manifest.permission.ACCESS_NETWORK_STATE)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landing_page)

        checkPermission()

        initLayout()
        changeColor()

    }
    fun  initLayout(){
        mTextView  = findViewById(R.id.text)
        mRelativeLayout = findViewById(R.id.layout)
        mRelativeLayout.setOnClickListener {
            startActivity(Intent(this,MyMapActivity::class.java))
        }
    }
    fun changeColor(){
        val timer = Timer()
        timer.schedule(task, 1, 300)
    }

    var task: TimerTask = object : TimerTask() {
        override fun run() {
            runOnUiThread {
                if (mChange) {
                    mChange = false
                    mTextView.setTextColor(Color.TRANSPARENT) //这个是透明，=看不到文字
                } else {
                    mChange = true
                    mTextView.setTextColor(Color.RED)
                }
            }
        }
    }
    fun checkPermission() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), MY_PERMISSIONS_REQUEST_LOCATION)
        }

    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == MY_PERMISSIONS_REQUEST_LOCATION) {

            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            } else {
                Toast.makeText(this, "需要定位功能,才能使用喔", Toast.LENGTH_SHORT).show()
                return
            }
        }
    }

    fun Context.toast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
