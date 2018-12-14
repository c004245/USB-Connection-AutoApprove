package com.example.hyunwook.usbautoapprove;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.hardware.usb.UsbDevice;
import android.widget.ArrayAdapter;

/**
 * 18-12-14
 * Usb Auto Approve Receiver.
 * Usb IBinder, IUsbManager
 */
public class UsbApproveReceiver extends BroadcastReceiver {

    private final String TAG = UsbApproveReceiver.class.getSimpleName();
    private final String ACTION_USB_PERMISSION_APP =  "ACTION_USB_PERMISSION_ISSUER";

    private ArrayAdapter<UsbDevice> mReaderAdapter;

    @SuppressLint("PrivateApi")
    public void onReceive(Context context, Intent intent) {

        mReaderAdapter = new ArrayAdapter<UsbDevice>(context, android.R.layout.simple_spinner_item);

        String action = intent.getAction();
        if (action != null && action.equals(ACTION_USB_PERMISSION_APP)) {
            //Called USB Auto Approve Receiver.
            try {

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
