package tina.com.common.http.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;

/**
 * Network utility functions.
 */
@SuppressWarnings("unused")
public final class NetworkUtil {
    private static final int NETWORK_TYPE_UNAVAILABLE = -1;
    private static final int NETWORK_TYPE_MOBILE = -100;
    private static final int NETWORK_TYPE_WIFI = -101;

    private static final int NETWORK_CLASS_WIFI = -101;
    private static final int NETWORK_CLASS_UNAVAILABLE = -1;
    /**
     * Unknown network class.
     */
    private static final int NETWORK_CLASS_UNKNOWN = 0;
    /**
     * Class of broadly defined "2G" networks.
     */
    private static final int NETWORK_CLASS_2_G = 1;
    /**
     * Class of broadly defined "3G" networks.
     */
    private static final int NETWORK_CLASS_3_G = 2;
    /**
     * Class of broadly defined "4G" networks.
     */
    private static final int NETWORK_CLASS_4_G = 3;

    // 适配低版本手机
    /**
     * Network type is unknown
     */
    private static final int NETWORK_TYPE_UNKNOWN = 0;
    /**
     * Current network is GPRS
     */
    private static final int NETWORK_TYPE_GPRS = 1;
    /**
     * Current network is EDGE
     */
    private static final int NETWORK_TYPE_EDGE = 2;
    /**
     * Current network is UMTS
     */
    private static final int NETWORK_TYPE_UMTS = 3;
    /**
     * Current network is CDMA: Either IS95A or IS95B
     */
    private static final int NETWORK_TYPE_CDMA = 4;
    /**
     * Current network is EVDO revision 0
     */
    private static final int NETWORK_TYPE_EVDO_0 = 5;
    /**
     * Current network is EVDO revision A
     */
    private static final int NETWORK_TYPE_EVDO_A = 6;
    /**
     * Current network is 1xRTT
     */
    private static final int NETWORK_TYPE_1xRTT = 7;
    /**
     * Current network is HSDPA
     */
    private static final int NETWORK_TYPE_HSDPA = 8;
    /**
     * Current network is HSUPA
     */
    private static final int NETWORK_TYPE_HSUPA = 9;
    /**
     * Current network is HSPA
     */
    private static final int NETWORK_TYPE_HSPA = 10;
    /**
     * Current network is iDen
     */
    private static final int NETWORK_TYPE_IDEN = 11;
    /**
     * Current network is EVDO revision B
     */
    private static final int NETWORK_TYPE_EVDO_B = 12;
    /**
     * Current network is LTE
     */
    private static final int NETWORK_TYPE_LTE = 13;
    /**
     * Current network is eHRPD
     */
    private static final int NETWORK_TYPE_EHRPD = 14;
    /**
     * Current network is HSPA+
     */
    private static final int NETWORK_TYPE_HSPAP = 15;
    /**
     * Current network is GSM
     */
    private static final int NETWORK_TYPE_GSM = 16;
    /**
     * Current network is TD_SCDMA
     */
    private static final int NETWORK_TYPE_TD_SCDMA = 17;

    private NetworkUtil() {
    }

    private static boolean isNetworkConnected(Context context) {
        ConnectivityManager connManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = null;
        try {
            if (connManager != null) {
                activeNetworkInfo = connManager.getActiveNetworkInfo();
            }
        } catch (Exception e) {
            // in some roms, here maybe throw a exception(like nullpoint).
            e.printStackTrace();
        }
        return (activeNetworkInfo != null && activeNetworkInfo.isConnected());
    }

    public static boolean isNetworkConnected() {
        return isNetworkConnected(Utils.getContext());
    }

    public static NetworkInfo getActiveNetworkInfo(Context context) {
        ConnectivityManager connManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = null;
        try {
            if (connManager != null) {
                activeNetworkInfo = connManager.getActiveNetworkInfo();
            }
        } catch (Exception e) {
            // in some roms, here maybe throw a exception(like nullpoint).
            e.printStackTrace();
        }
        return activeNetworkInfo;
    }

    public static boolean isMobileNetworkConnected(Context context) {
        ConnectivityManager connManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = null;
        try {
            // maybe throw exception in android framework
            if (connManager != null) {
                networkInfo = connManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return networkInfo != null && networkInfo.isConnected();
    }

    public static boolean isWifiConnected(Context context) {
        ConnectivityManager connManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = null;
        try {
            // maybe throw exception in android framework
            if (connManager != null) {
                networkInfo = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (networkInfo != null && networkInfo.isConnected());
    }

    public static boolean isWifiClosed(Context context) {
        WifiManager wm = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        return wm != null && wm.getWifiState() == WifiManager.WIFI_STATE_DISABLED;
    }

    /**
     * 获取网络类型
     */
    public static String getCurrentNetworkType(Context context) {
        int networkClass = getNetworkClass(context);
        String type = "unknown";
        switch (networkClass) {
            case NETWORK_CLASS_UNAVAILABLE:
                type = "unavailable";
                break;
            case NETWORK_CLASS_WIFI:
                type = "Wi-Fi";
                break;
            case NETWORK_CLASS_2_G:
                type = "2G";
                break;
            case NETWORK_CLASS_3_G:
                type = "3G";
                break;
            case NETWORK_CLASS_4_G:
                type = "4G";
                break;
            case NETWORK_CLASS_UNKNOWN:
                type = "unknown";
                break;
        }
        return type;
    }

    private static int getNetworkClassByType(int networkType) {
        switch (networkType) {
            case NETWORK_TYPE_UNAVAILABLE:
                return NETWORK_CLASS_UNAVAILABLE;
            case NETWORK_TYPE_WIFI:
                return NETWORK_CLASS_WIFI;
            case NETWORK_TYPE_GPRS:
            case NETWORK_TYPE_EDGE:
            case NETWORK_TYPE_CDMA:
            case NETWORK_TYPE_1xRTT:
            case NETWORK_TYPE_IDEN:
                return NETWORK_CLASS_2_G;
            case NETWORK_TYPE_UMTS:
            case NETWORK_TYPE_EVDO_0:
            case NETWORK_TYPE_EVDO_A:
            case NETWORK_TYPE_HSDPA:
            case NETWORK_TYPE_HSUPA:
            case NETWORK_TYPE_HSPA:
            case NETWORK_TYPE_EVDO_B:
            case NETWORK_TYPE_EHRPD:
            case NETWORK_TYPE_HSPAP:
            case NETWORK_TYPE_GSM:
            case NETWORK_TYPE_TD_SCDMA:
                return NETWORK_CLASS_3_G;
            case NETWORK_TYPE_LTE:
                return NETWORK_CLASS_4_G;
            default:
                return NETWORK_CLASS_UNKNOWN;
        }
    }

    private static int getNetworkClass(Context context) {
        int networkType = NETWORK_TYPE_UNKNOWN;
        try {
            ConnectivityManager systemService = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo network = null;
            if (systemService != null) {
                network = systemService.getActiveNetworkInfo();
            }
            if (network != null && network.isAvailable()
                    && network.isConnected()) {
                int type = network.getType();
                if (type == ConnectivityManager.TYPE_WIFI) {
                    networkType = NETWORK_TYPE_WIFI;
                } else if (type == ConnectivityManager.TYPE_MOBILE) {
                    TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(
                            Context.TELEPHONY_SERVICE);
                    if (telephonyManager != null) {
                        networkType = telephonyManager.getNetworkType();
                    }
                }
            } else {
                networkType = NETWORK_TYPE_UNAVAILABLE;
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return getNetworkClassByType(networkType);

    }
}
