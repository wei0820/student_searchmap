package com.student.student_searchmap

import android.annotation.SuppressLint
import android.app.TimePickerDialog
import android.location.Geocoder
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v7.app.AlertDialog
import android.util.Log
import android.view.View
import android.widget.*
import java.util.*

class MannerActivity : AppCompatActivity(),View.OnClickListener {


    lateinit var mAddressEdt :EditText
    lateinit var mCheckBtn :Button
    lateinit var mSpinner :Spinner
    lateinit var mStartbtn :Button
    lateinit var mEndbtn :Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manner)
        initLayout()
    }
    fun initLayout(){
        mAddressEdt = findViewById(R.id.editText)
        mCheckBtn = findViewById(R.id.checkbtn)
        mStartbtn = findViewById(R.id.startbtn)
        mEndbtn = findViewById(R.id.endbtn)
        mSpinner = findViewById(R.id.spinner)
        val searchSortSpinnerData = arrayOf("室內","室外")
        val adapter = ArrayAdapter(
                this, // Context
                android.R.layout.simple_spinner_item, // Layout
                searchSortSpinnerData // Array
        )
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)

        // Finally, data bind the spinner object with dapter
        mSpinner.adapter = adapter;

        // Set an on item selected listener for spinner object
        mSpinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent:AdapterView<*>, view: View, position: Int, id: Long){
                Toast.makeText(this@MannerActivity,"選擇"+searchSortSpinnerData[position],Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>){
                // Another interface callback
            }
        }
        mCheckBtn.setOnClickListener(this)
        mStartbtn.setOnClickListener(this)
        mEndbtn.setOnClickListener(this)

    }
    @SuppressLint("NewApi")
    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.checkbtn ->{
                if(!mAddressEdt.text.isEmpty()){
                    checkAddress(mAddressEdt.text.toString())
                }else{
                    Toast.makeText(this,"請勿輸入空白",Toast.LENGTH_SHORT).show()
                }

            }
            R.id.startbtn ->{
                clickTimePicker(mStartbtn)

            }
            R.id.endbtn ->{
                clickTimePicker(mEndbtn)

            }
        }
    }
    fun checkAddress(addrss:String){
        var geoCoder = Geocoder(this, Locale.getDefault())
        var addressLocation = geoCoder.getFromLocationName(addrss, 1)
        if (addressLocation.size!=0){
            var latitude = addressLocation[0].latitude
            var longitude = addressLocation[0].longitude
            Log.d("latitude",latitude.toString())
            Log.d("longitude",longitude.toString())
            val builder = AlertDialog.Builder(this)
            builder.setTitle("取得成功")
            builder.setMessage("latitude:"+latitude.toString()+"\n\n"
                    + "longitude:"+ longitude.toString()
            )
            builder.setPositiveButton("知道了",{ dialog, whichButton ->
                dialog.dismiss()
            })
            // create dialog and show it
            val dialog = builder.create()
            dialog.show()
        }else{
            val builder = AlertDialog.Builder(this)
            builder.setTitle("取得失敗")
            builder.setMessage("請重新檢查地址並重新輸入!!")
            builder.setPositiveButton("知道了",{ dialog, whichButton ->
                dialog.dismiss()
            })
            // create dialog and show it
            val dialog = builder.create()
            dialog.show()
        }


    }
    @RequiresApi(Build.VERSION_CODES.N)
    fun clickTimePicker(button: Button) {
        val c = Calendar.getInstance()
        val hour = c.get(Calendar.HOUR)
        val minute = c.get(Calendar.MINUTE)

        val tpd = TimePickerDialog(this,TimePickerDialog.OnTimeSetListener(function = { view, h, m ->
            button.setText(h.toString() + " : " + m)

        }),hour,minute,false)

        tpd.show()
    }
}
