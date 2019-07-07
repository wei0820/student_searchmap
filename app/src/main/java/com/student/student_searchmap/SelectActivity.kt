package com.student.student_searchmap

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity;
import android.widget.Button
import com.student.student_searchmap.Data.PhoneAuthActivity

import kotlinx.android.synthetic.main.activity_select.*

class SelectActivity : AppCompatActivity() {
    lateinit var mButton:Button
    lateinit var mButton2: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.content_select)
        mButton = findViewById(R.id.button_1)
        mButton2 = findViewById(R.id.button_2)
        mButton.setOnClickListener {
            startActivity(Intent(this,MyMapActivity::class.java))

        }
        mButton2.setOnClickListener {
            startActivity(Intent(this,PhoneAuthActivity::class.java))

        }
    }

}
