package com.example.hyunwook.usbautoapprove;

import android.content.Context;
import android.content.Intent;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.acs.smartcard.Reader;

/**
 * 18-12-14 Fri.
 * USB auto-approve as simple as possible
 * added by hyunwook Cho
 * written in Guangzhou, China.
 */
public class MainActivity extends AppCompatActivity {

    private ArrayAdapter<String> mReaderAdapter; //Usb Device Adapter.

    private UsbManager mManager;

    static final String MY_APP_PACKAGENAME = "com.example.hyunwook.usbautoapprove";
    static final String ACTION_USB_PERMISSION_APP =  "ACTION_USB_PERMISSION_ISSUER";

    static final String TAG = MainActivity.class.getSimpleName();

    static private Reader mReader; //출근 장치
    static private Reader mReader2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mManager = (UsbManager) getSystemService(Context.USB_SERVICE);

        mReader = new Reader(mManager);
        mReader2 = new Reader(mManager);
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

    //Usb Device Init Method
    private void UsbInit() {
        UsbInitPermission(1);

        //Main Thread sleep.
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //Usb Permission SendBroadcast
    private void UsbInitPermission(int id) {
        Intent intent = new Intent();
        intent.setAction(ACTION_USB_PERMISSION_APP);
        intent.putExtra("packageName", MY_APP_PACKAGENAME); //current app packageName.

        //device_filter.xml
        intent.putExtra("vendorId", 1839); //My USB Card Reader Device Vendor Id 1839
        switch (id) {
            case 1:
                intent.putExtra("productId", 8761); //My USB Card Reader Device Product Id 8761
                intent.putExtra("deviceClass", 0);
                intent.putExtra("deviceSubclass", 0);
                break;
        }

        sendBroadcast(intent);


    }

    //Reader Device OpenTask
    public static class OpenTask extends AsyncTask<ArrayAdapter<UsbDevice>, Void, Exception> {

        @Override
        protected Exception doInBackground(ArrayAdapter<UsbDevice>... params) {
            Exception result = null;
            for (ArrayAdapter<UsbDevice> u : params) {
                try {
                    //Current Connected Device Only 1
                    if (u.getCount() == 1) {
                        mReader.open(u.getItem(0));
                    } else {
                        mReader.open(u.getItem(0));
                        mReader2.open(u.getItem(1));
                    }
                } catch (Exception e) {
                    result = e;
                }
            }


            return result;
        }
        @Override
        protected void onPostExecute(Exception result) {

        }
    }

    public static class CloseTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            mReader.close();
            mReader2.close();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
        }
    }




}
