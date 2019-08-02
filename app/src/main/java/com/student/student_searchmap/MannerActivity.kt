package com.student.student_searchmap

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.app.Activity
import android.app.TimePickerDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.location.Geocoder
import android.net.Uri
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.support.annotation.RequiresApi
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AlertDialog
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import android.widget.*
import java.io.ByteArrayOutputStream
import java.io.FileNotFoundException
import java.util.*
import android.util.Base64
import com.jackpan.libs.mfirebaselib.MfiebaselibsClass
import com.jackpan.libs.mfirebaselib.MfirebaeCallback


class MannerActivity : AppCompatActivity(), View.OnClickListener, MfirebaeCallback {
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
    }

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

    private val CAMERA = 66
    private val PHOTO = 99
    private val REQUEST_EXTERNAL_STORAGE = 200
    private val PICKER = 100
    lateinit var bitmap: Bitmap
    lateinit var phone: DisplayMetrics
    var img: String = ""
    lateinit var mAddressEdt: EditText
    lateinit var mCheckBtn: Button
    lateinit var mSpinner: Spinner
    lateinit var mStartbtn: Button
    lateinit var mEndbtn: Button
    lateinit var mPriceEdt: EditText
    lateinit var mPhotoButtton: Button
    lateinit var mPhoneEdt: EditText
    lateinit var mMessageEdt: EditText
    lateinit var mSendBtn: Button
     var latitude  :Double = 0.0
     var longitude :Double =0.0
    var mSelectType : String = ""
    var mStartString :String = ""
    var  mEndString :String = ""
    var mPhoneString :String = ""
    var mMessagerString : String = ""
    lateinit var mFirebselibClass: MfiebaselibsClass

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mFirebselibClass = MfiebaselibsClass(this, this@MannerActivity)

        setContentView(R.layout.activity_manner)
        phone = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(phone)

        initLayout()
    }

    fun initLayout() {
        mAddressEdt = findViewById(R.id.editText)
        mCheckBtn = findViewById(R.id.checkbtn)
        mStartbtn = findViewById(R.id.startbtn)
        mEndbtn = findViewById(R.id.endbtn)
        mSpinner = findViewById(R.id.spinner)
        mPriceEdt = findViewById(R.id.editText3)
        mPhotoButtton = findViewById(R.id.button4)
        mPhoneEdt = findViewById(R.id.editText4)
        mMessageEdt = findViewById(R.id.editText5)
        mSendBtn = findViewById(R.id.send)

        val searchSortSpinnerData = arrayOf("室內", "室外")
        val adapter = ArrayAdapter(
                this, // Context
                android.R.layout.simple_spinner_item, // Layout
                searchSortSpinnerData // Array
        )
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)

        // Finally, data bind the spinner object with dapter
        mSpinner.adapter = adapter;

        // Set an on item selected listener for spinner object
        mSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                Toast.makeText(this@MannerActivity, "選擇" + searchSortSpinnerData[position], Toast.LENGTH_SHORT).show()
                mSelectType  = searchSortSpinnerData[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Another interface callback
            }
        }
        mCheckBtn.setOnClickListener(this)
        mStartbtn.setOnClickListener(this)
        mEndbtn.setOnClickListener(this)
        mPhotoButtton.setOnClickListener(this)
        mSendBtn.setOnClickListener(this)

    }

    @SuppressLint("NewApi")
    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.checkbtn -> {
                if (!mAddressEdt.text.isEmpty()) {
                    checkAddress(mAddressEdt.text.toString())
                } else {
                    Toast.makeText(this, "請勿輸入空白", Toast.LENGTH_SHORT).show()
                }

            }
            R.id.startbtn -> {
                mStartString = clickTimePicker(mStartbtn)

            }
            R.id.endbtn -> {
               mEndString  = clickTimePicker(mEndbtn)

            }
            R.id.button4 -> {
                selectPic()
            }
            R.id.send -> {
                sendData()
            }
        }
    }

    fun checkAddress(addrss: String) {
        var geoCoder = Geocoder(this, Locale.getDefault())
        var addressLocation = geoCoder.getFromLocationName(addrss, 1)
        if (addressLocation.size != 0) {
             latitude = addressLocation[0].latitude
            longitude = addressLocation[0].longitude
            Log.d("latitude", latitude.toString())
            Log.d("longitude", longitude.toString())
            val builder = AlertDialog.Builder(this)
            builder.setTitle("取得成功")
            builder.setMessage("latitude:" + latitude.toString() + "\n\n"
                    + "longitude:" + longitude.toString()
            )
            builder.setPositiveButton("知道了", { dialog, whichButton ->
                dialog.dismiss()
            })
            // create dialog and show it
            val dialog = builder.create()
            dialog.show()
        } else {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("取得失敗")
            builder.setMessage("請重新檢查地址並重新輸入!!")
            builder.setPositiveButton("知道了", { dialog, whichButton ->
                dialog.dismiss()
            })
            // create dialog and show it
            val dialog = builder.create()
            dialog.show()
        }


    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun clickTimePicker(button: Button) :String {
        var time :String = ""
        val c = Calendar.getInstance()
        val hour = c.get(Calendar.HOUR)
        val minute = c.get(Calendar.MINUTE)

        val tpd = TimePickerDialog(this, TimePickerDialog.OnTimeSetListener(function = { view, h, m ->
            button.setText(h.toString() + " : " + m)
            time = h.toString() + " : " + m;
        }), hour, minute, false)

        tpd.show()
        return  time
    }


    private fun selectPic() {
        val permission = ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.READ_EXTERNAL_STORAGE
        )
        if (permission != PackageManager.PERMISSION_GRANTED) {
            //未取得權限，向使用者要求允許權限

            if (ActivityCompat.shouldShowRequestPermissionRationale(
                            this,
                            android.Manifest.permission.CAMERA
                    )
            ) {
                android.support.v7.app.AlertDialog.Builder(this)
                        .setMessage("我真的沒有要做壞事, 給我權限吧?")
                        .setPositiveButton("OK") { dialog, which ->
                            ActivityCompat.requestPermissions(
                                    this,
                                    arrayOf(android.Manifest.permission.CAMERA),
                                    REQUEST_EXTERNAL_STORAGE
                            )
                        }
                        .setNegativeButton("No") { dialog, which -> finish() }
                        .show()
            } else {
                ActivityCompat.requestPermissions(
                        this,
                        arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                        REQUEST_EXTERNAL_STORAGE)
            }

        } else {
            //開啟相簿相片集，須由startActivityForResult且帶入requestCode進行呼叫，原因
            //為點選相片後返回程式呼叫onActivityResult
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(intent, PHOTO)


        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            REQUEST_EXTERNAL_STORAGE -> {
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

    //拍照完畢或選取圖片後呼叫此函式
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == PICKER) {
            if (resultCode == Activity.RESULT_OK) {


            }
        }

        //藉由requestCode判斷是否為開啟相機或開啟相簿而呼叫的，且data不為null
        if ((requestCode == CAMERA || requestCode == PHOTO) && data != null) {
            //取得照片路徑uri
            val datauri = data.data
            img = encode(datauri)
            Log.d("img",img)
            val cr = contentResolver
            try {


                //讀取照片，型態為Bitmap
                bitmap = BitmapFactory.decodeStream(cr.openInputStream(datauri));
                //判斷照片為橫向或者為直向，並進入ScalePic判斷圖片是否要進行縮放
                if (bitmap.width > bitmap.height) ScalePic(
                        bitmap,
                        phone.heightPixels
                );
                else ScalePic(bitmap, phone.widthPixels);
            } catch (ex: FileNotFoundException) {
                ex.printStackTrace()

            }

            uploadFromPic(datauri)


            super.onActivityResult(requestCode, resultCode, data)
        }

    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private fun uploadFromPic(datauri: Uri?) {
        val after44 = Build.VERSION.SDK_INT >= 19
        var filePath = ""

        if (after44) {
            val wholeID = DocumentsContract.getDocumentId(datauri)

            // Split at colon, use second item in the array
            val id = wholeID.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[1]

            val column = arrayOf(MediaStore.Images.Media.DATA)

            // where id is equal to
            val sel = MediaStore.Images.Media._ID + "=?"

            val cursor = contentResolver.query(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    column, sel, arrayOf(id), null
            )


            val columnIndex = cursor!!.getColumnIndex(column[0])

            if (cursor.moveToFirst()) {
                filePath = cursor.getString(columnIndex)

            }

            cursor.close()
        } else {

            try {
                val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)
                val cursor = contentResolver.query(
                        datauri!!,
                        filePathColumn, null, null, null)
                cursor!!.moveToFirst()

                val columnIndex = cursor.getColumnIndex(filePathColumn[0])
                filePath = cursor.getString(columnIndex)
                cursor.close()

            } catch (e: Exception) {
                // TODO: handle exception
                e.printStackTrace()
            }

        }

    }

    private fun ScalePic(bitmap: Bitmap, phone: Int) {
        Log.d(javaClass.simpleName, "//ScalePic")

        //縮放比例預設為1
        var mScale = 1f

        //如果圖片寬度大於手機寬度則進行縮放，否則直接將圖片放入ImageView內
        if (bitmap.width > phone) {
            //判斷縮放比例
            mScale = phone.toFloat() / bitmap.width.toFloat()

            val mMat = Matrix()
            mMat.setScale(mScale, mScale)

            val mScaleBitmap = Bitmap.createBitmap(
                    bitmap,
                    0,
                    0,
                    bitmap.width,
                    bitmap.height,
                    mMat,
                    false
            )
            Log.d(javaClass.simpleName, mScaleBitmap.toString())

        } else {

        }
    }

    fun encode(imageUri: Uri): String {
        val input = getContentResolver().openInputStream(imageUri)
        val image = BitmapFactory.decodeStream(input, null, null)
        //encode image to base64 string
        val baos = ByteArrayOutputStream()
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        var imageBytes = baos.toByteArray()
        val imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT)
        return imageString
    }

    fun decode(imageString: String) {

        //decode base64 string to image
        val imageBytes = Base64.decode(imageString, Base64.DEFAULT)
        val decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)

//        imageview.setImageBitmap(decodedImage)
    }
    fun sendData(){
        mPhoneString = mPhoneEdt.text.toString()
        mMessagerString = mMessageEdt.text.toString()
        if(latitude!=0.0
                &&longitude!=0.0
                && !mStartString.isEmpty()
                &&!mEndString.isEmpty()
                &&!img.isEmpty()
                &&!mPhoneString.isEmpty()
                &&!mMessagerString.isEmpty()
                &&!mSelectType.isEmpty()){
            val builder = AlertDialog.Builder(this)
            builder.setTitle("提示")
            builder.setMessage("以輸入全部資訊")
            builder.setPositiveButton("知道了", { dialog, whichButton ->
                dialog.dismiss()
            })
            // create dialog and show it
            val dialog = builder.create()
            dialog.show()


        }else{
            val builder = AlertDialog.Builder(this)
            builder.setTitle("提示")
            builder.setMessage("請檢查是否有漏內容")
            builder.setPositiveButton("知道了", { dialog, whichButton ->
                dialog.dismiss()
            })
            // create dialog and show it
            val dialog = builder.create()
            dialog.show()
        }



    }

}
