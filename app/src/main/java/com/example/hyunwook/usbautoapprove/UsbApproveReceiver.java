package com.example.hyunwook.usbautoapprove;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.os.IBinder;
import android.text.TextUtils;
import android.widget.ArrayAdapter;

import java.lang.reflect.Method;

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
                //Set UsbDevice info
                UsbDeviceDescriptor deviceFilter = new UsbDeviceDescriptor();
                deviceFilter.packageName = intent.getStringExtra("packageName");
                deviceFilter.vendorId = intent.getIntExtra("vendorId", -1);
                deviceFilter.productId = intent.getIntExtra("productId", -1);
                deviceFilter.deviceClass = intent.getIntExtra("deviceClass", -1);
                deviceFilter.deviceSubClass = intent.getIntExtra("deviceSubclass", -1);

                if (TextUtils.isEmpty(deviceFilter.packageName)) {
                    return;
                }

                PackageManager pm = context.getPackageManager();
                ApplicationInfo ai = pm.getApplicationInfo(deviceFilter.packageName, 0);

                //Usb Service
                UsbManager manager = (UsbManager) context.getSystemService(Context.USB_SERVICE);

                Method method = null;

                //Register ServiceManager
                method = Class.forName("android.os.ServiceManager").getMethod("getService", String.class);

                //Binder USB
                IBinder binder = (IBinder) method.invoke(null, "usb");
                IUsbManager service = IUsbManager.Stub.asInterface(binder);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
