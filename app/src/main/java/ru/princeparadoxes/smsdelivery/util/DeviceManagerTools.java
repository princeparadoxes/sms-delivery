package ru.princeparadoxes.smsdelivery.util;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;

import java.util.UUID;

public class DeviceManagerTools {
    private static final String STRING_COLON = ":";
    private static final String STRING_EMPTY = "";
    private static final int DECIDE_SHIFT = 32;

    private DeviceManagerTools() {
        // nothing;
    }

    public static String getUniqueDeviceId(Context context) {
        final TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

        final String tmDevice = STRING_EMPTY + tm.getDeviceId();
        final String tmSerial = STRING_EMPTY + tm.getSimSerialNumber();
        String andrId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        final String androidId = STRING_EMPTY + andrId;

        UUID deviceUuid = new UUID(androidId.hashCode(),
                ((long) tmDevice.hashCode() << DECIDE_SHIFT) | tmSerial.hashCode());
        return deviceUuid.toString();
    }

    public static String getMacAddress(Context context) {
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo wInfo = wifiManager.getConnectionInfo();
        return wInfo.getMacAddress();
    }

    public static String getMacAddressWithoutColon(Context context) {
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo wInfo = wifiManager.getConnectionInfo();
        String macAddress = wInfo.getMacAddress();
        return macAddress.replace(STRING_COLON, STRING_EMPTY);
    }

    public static String getDeviceInfo() {
        StringBuilder builder = new StringBuilder();
        builder.append("OS VERSION: " + System.getProperty("os.version") + ",\n");    // OS version
        builder.append("SDK_INT: " + Build.VERSION.SDK_INT + ",\n");    // API Level
        builder.append("DEVICE: " + Build.DEVICE + ",\n");              // Device
        builder.append("MODEL: " + Build.MODEL + ",\n");                // Model
        builder.append("PRODUCT: " + Build.PRODUCT + ",\n");            // Product
        builder.append("BRAND: " + Build.BRAND + ". ");                 // Brand
        return builder.toString();
    }

    public static String getDetailDeviceInfo() {
        StringBuilder builder = new StringBuilder();
        builder.append("OS_VERSION: " + System.getProperty("os.version") + ",\n");
        builder.append("SDK_INT: " + Build.VERSION.SDK_INT + ",\n");
        builder.append("RELEASE: " + Build.VERSION.RELEASE + ",\n");
        builder.append("BUILD_ID: " + Build.ID + ",\n");
        builder.append("DEVICE: " + Build.DEVICE + ",\n");
        builder.append("MODEL: " + Build.MODEL + ",\n");
        builder.append("PRODUCT: " + Build.PRODUCT + ",\n");
        builder.append("BRAND: " + Build.BRAND + ",\n");
        builder.append("CPU_ABI: " + Build.CPU_ABI + ",\n");
        builder.append("CPU_ABI2: " + Build.CPU_ABI2 + ",\n");
        builder.append("DISPLAY: " + Build.DISPLAY + ",\n");
        builder.append("HARDWARE: " + Build.HARDWARE + ",\n");
        builder.append("MANUFACTURER: " + Build.MANUFACTURER + ",\n");
        builder.append("SERIAL: " + Build.SERIAL + ",\n");
        builder.append("USER: " + Build.USER + ",\n");
        builder.append("HOST: " + Build.HOST + ". ");
        return builder.toString();
    }
}
