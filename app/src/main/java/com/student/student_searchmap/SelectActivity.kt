package com.student.student_searchmap

import android.Manifest
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import com.student.student_searchmap.Data.BigTaichungActivity
import com.student.student_searchmap.Data.PhoneAuthActivity


class SelectActivity : AppCompatActivity() {
    lateinit var mButton:Button
    lateinit var mButton2: Button
    lateinit var mbutton3 :Button
    private val MY_PERMISSIONS_REQUEST_READ_CONTACTS = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.content_select)
        mButton = findViewById(R.id.button_1)
        mButton2 = findViewById(R.id.button_2)
        mbutton3 = findViewById(R.id.button_3)
        set()

        mButton.setOnClickListener {
            startActivity(Intent(this,MyMapActivity::class.java))

        }
        mButton2.setOnClickListener {
            startActivity(Intent(this,PhoneAuthActivity::class.java))
//            startActivity(Intent(this,MannerActivity::class.java))



        }
        mbutton3.setOnClickListener {
            startActivity(Intent(this,SearchCar2Activity::class.java))

        }

    }
    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            MY_PERMISSIONS_REQUEST_READ_CONTACTS -> {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {
                    finish()
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return
            }
        }// other 'case' lines to check for other
        // permissions this app might request
    }

    fun set(){
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.ACCESS_FINE_LOCATION)) {
            AlertDialog.Builder(this)
                    .setMessage("我真的沒有要做壞事, 給我權限吧?")
                    .setPositiveButton("OK") { dialog, which ->
                        ActivityCompat.requestPermissions(this,
                                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                                MY_PERMISSIONS_REQUEST_READ_CONTACTS)
                    }
                    .setNegativeButton("No") { dialog, which -> finish() }
                    .show()
        } else {
            ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION),
                    MY_PERMISSIONS_REQUEST_READ_CONTACTS)
        }
    }


}
