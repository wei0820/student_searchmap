package com.student.student_searchmap

import android.os.Bundle
import android.support.v7.app.AppCompatActivity;
import android.util.Log


class PlaceDeatilActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.content_place_deatil)
        getid()
    }
    fun getid(){
      val id :String =   intent.extras.getString("id")
        Log.d("Jack",id)

    }

}
