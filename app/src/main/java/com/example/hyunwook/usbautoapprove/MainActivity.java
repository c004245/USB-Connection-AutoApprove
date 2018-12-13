package com.example.hyunwook.usbautoapprove;

import android.content.Context;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;

/**
 * 18-12-14 Fri.
 * USB auto-approve as simple as possible
 * added by hyunwook Cho
 * written in Guangzhou, China.
 */
public class MainActivity extends AppCompatActivity {

    private ArrayAdapter<String> mReaderAdapter; //Usb Device Adapter.

    private UsbManager mManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mManager = (UsbManager) getSystemService(Context.USB_SERVICE);

        mReaderAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item);

        //Check the USB device connected to the Android device
        for (UsbDevice device : mManager.getDeviceList().values()) {
            mReaderAdapter.add(device.getDeviceName());
        }

        int deviceCount = mReaderAdapter.getCount();

        if (deviceCount == 0) {
            //Usb device not connected on Android Device.
        } else {
            //Init Usb Device.
            UsbInit();
        }
    }
}
