package com.example.hyunwook.usbautoapprove

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.hardware.usb.UsbDevice
import android.hardware.usb.UsbManager
import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ArrayAdapter
import com.acs.smartcard.Reader
import java.lang.Exception

/**
 * 18-12-18
 * Convert Java to Kotlin on MainActivity
 * USB auto-approve as simple as possible
 * added by hyunwook Cho
 * written in Guangzhou, China.
 */

class MainActivity2 : AppCompatActivity() {

    //Usb Device Adapter
    private lateinit var mReaderAdapter : ArrayAdapter<String>

    private lateinit var mManager : UsbManager

    private val MY_APP_PACKAGENAME = "com.example.hyunwook.usbautoapprove"
    private val ACTION_USB_PERMISSION_APP = "ACTION_USB_PERMISSION_ISSUER"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mManager = getSystemService(Context.USB_SERVICE) as UsbManager

        mReader = Reader(mManager)
        mReader2 = Reader(mManager)

        mReaderAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item)


        //Check the USB device connected to the Android Device.
        for (device : UsbDevice in mManager.deviceList.values) {
            mReaderAdapter.add(device.deviceName)
        }

       var deviceCount : Int = mReaderAdapter.count

        if (deviceCount == 0) {
            //USB Device not connected on Android Device.
        } else {
            //Init USB Device.
            UsbInit();
        }
    }

    //USB Device Init Method
    private fun UsbInit() {
        UsbInitPermission(1)

        //Main Thread sleep
        try {
            Thread.sleep(100)
        } catch (e : InterruptedException) {
            e.printStackTrace()
        }
    }

    //Usb Permission SendBroadcast
    private fun UsbInitPermission(id : Int) {
        val intent = Intent()
        intent.setAction(ACTION_USB_PERMISSION_APP)
        //current app packageName
        intent.putExtra("packageName", MY_APP_PACKAGENAME)

        //device_filter.xml
        //Test My USB Card Reader Device Vendor Id 1839
        intent.putExtra("vendorId", 1839)

        when (id) {
            1 -> {
                //Test My USB Card Reader Device Product Id 8761
                intent.putExtra("productId", 8761)
                intent.putExtra("deviceClass", 0)
                intent.putExtra("deviceSubclass", 0)
            }
        }

        sendBroadcast(intent)

    }

    class OpenTask : AsyncTask<ArrayAdapter<UsbDevice>, Void, Exception>() {

        override fun doInBackground(vararg params: ArrayAdapter<UsbDevice>?): Exception? {
            var result : Exception? = null

            for (u : ArrayAdapter<UsbDevice>? in params) {
                try {
                    //Current Connected Device Only 1
                    if (u?.count == 1) {
                        mReader.open(u!!.getItem(0))
                    } else {
                        mReader.open(u!!.getItem(0))
                        mReader2.open(u!!.getItem(1))
                    }
                } catch (e: Exception) {
                    result = e
                }
            }

            return result

        }

        override fun onPostExecute(result: Exception?) {

        }
    }

    //CloseTask
    class CloseTask : AsyncTask<Void, Void, Void>() {

        override fun doInBackground(vararg params: Void?): Void? {
            mReader.close()
            mReader2.close()
            return null
        }

        override fun onPostExecute(result: Void?) {
            
        }
    }
    companion object {
        lateinit var mReader : Reader
        lateinit var mReader2 : Reader
    }
}
