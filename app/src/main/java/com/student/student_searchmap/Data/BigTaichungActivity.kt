package com.student.student_searchmap.Data

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.GridView
import android.widget.ArrayAdapter
import com.student.student_searchmap.R


class BigTaichungActivity : AppCompatActivity() {
    lateinit var mGridView: GridView
    private val mAppNames = arrayOf("中區","東區","西區","南區","北區","西屯區","南屯區","北屯區","豐原區","大里區","太平區","清水區","沙鹿區","大甲區","東勢區","梧棲區","烏日區","神岡區","大肚區","大雅區","后里區","霧峰區","潭子區","龍井區","外埔區","和平區","石岡區","大安區","新社區")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_big_taichung)
        mGridView = findViewById(R.id.gridview)
        // 陣列接收器
        val place = ArrayAdapter(this, android.R.layout.simple_list_item_1, mAppNames
        )
        mGridView.adapter = place

    }
}
