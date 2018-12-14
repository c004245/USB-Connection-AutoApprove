package com.example.hyunwook.usbautoapprove;

/**
 * 18-12-14
 * Usb Device related Descriptor
 */
public class UsbDeviceDescriptor {

    public String packageName;

    //USB device identification
    public int deviceClass;
    public int deviceSubClass;
    public int vendorId;
    public int productId;

    @Override
    public String toString() {
        return "packageName:" + packageName
                + ", deviceClass:" + deviceClass
                + ", deviceSubclass: " + deviceSubClass
                + ", vendorId: " + vendorId
                + ", productId: " + productId;
    }



}
