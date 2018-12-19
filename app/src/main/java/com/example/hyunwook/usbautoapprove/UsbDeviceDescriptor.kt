package com.example.hyunwook.usbautoapprove

/**
 * 18-12-19
 * Convert Java to Kotlin on MainActivity
 * USB Device related Descriptor
 * added by hyunwook Cho
 * written in Seoul, Korea
 */

class UsbDeviceDescriptor2 {

    lateinit var packageName : String

    //Usb Device identification
    var deviceClass: Int = 0
    var deviceSubClass: Int = 0
    var vendorId: Int = 0
    var productId: Int = 0;

    override fun toString(): String {
        return ("packageName:" + packageName
                + ", deviceClass:" + deviceClass
                + ", deviceSubclass: " + deviceSubClass
                + ", vendorId: " + vendorId
                + ", productId: " + productId)
    }


}
