package com.student.student_searchmap

import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView

import kotlinx.android.synthetic.main.activity_landing_page.*
import java.util.*

class LandingPageActivity : AppCompatActivity() {
    lateinit var  mTextView :TextView
    var mChange :Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landing_page)
        initLayout()
        changeColor()
    }
    fun  initLayout(){
        mTextView  = findViewById(R.id.text)

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


}
