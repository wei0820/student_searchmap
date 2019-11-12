package com.student.student_searchmap.Data

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.GridView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.student.student_searchmap.R


class BigTaichungActivity : AppCompatActivity() {
    lateinit var mGridView: GridView
    private val mAppNames = arrayOf("中區","東區","西區","南區","北區","西屯區","南屯區","北屯區","豐原區","大里區","太平區","清水區","沙鹿區","大甲區","東勢區","梧棲區","烏日區","神岡區","大肚區","大雅區","后里區","霧峰區","潭子區","龍井區","外埔區","和平區","石岡區","大安區","新社區")
    private val mLanlon = arrayOf("24.141751, 120.681039",
            "24.138312, 120.698239",
            "24.143073, 120.663236",
            "24.120987, 120.662462",
            "24.157413, 120.683193",
            "24.185498, 120.612168",
            "24.147399, 120.608366",
            "24.182189, 120.686490",
            "24.251930, 120.721875",
            "24.105328, 120.680877",
            "24.124423, 120.716996",
            "24.302150, 120.585618",
            "24.237824, 120.585797",
            "24.378588, 120.649316",
            "24.261299, 120.831482",
            "24.248510, 120.539882",
            "24.103534, 120.630520",
            "24.247481, 120.683455",
            "24.135888, 120.563453",
            "24.226033, 120.651073",
            "24.308179, 120.722461",
            "24.044633, 120.711068",
            "24.216410, 120.706231",
            "24.210200, 120.517682",
            "24.331863, 120.653702",
            "24.313821, 121.186361",
            "24.274274, 120.776053",
            "25.026617, 121.543245",
            "24.185987, 120.816106")
    val type = arrayOf(0,1,2,3,4,5,6,7,8,9,10,
            11,12,13,14,15,16,17,18,19,20,
            21,22,23,24,25,26,27,28)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_big_taichung)
        mGridView = findViewById(R.id.gridview)
        // 陣列接收器
        val place = ArrayAdapter(this, android.R.layout.simple_list_item_1, mAppNames
        )
        mGridView.adapter = place
        mGridView.setOnItemClickListener { adapterView, view, i, l ->
            Log.d("Jack",mLanlon.get(i))
            var lat : Double = mLanlon.get(i).split(",")[0].toDouble()
            var lon : Double = mLanlon.get(i).split(",")[1].toDouble()
            var intent = Intent()
            var bundle = Bundle()
            bundle.putDouble("lat",lat)
            bundle.putDouble("lon",lon)
            bundle.putInt("type",type[i])

            intent.putExtras(bundle)
            intent.setClass(this,Map2Activity::class.java)
            startActivity(intent)

        }
    }
}
