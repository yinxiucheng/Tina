package tina.com.live_base.utils;

import android.graphics.Color;
import android.text.TextUtils;

import com.meitu.library.application.BaseApplication;
import com.meitu.library.util.Debug.Debug;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

import tina.com.live_base.R;

/**
 * User: Gyl
 * Date: 14-5-13  下午7:16
 * 数字格式
 */
public class NumberUtils {

    /**
     * 显示小于100的数，大于等于100显示99+
     *
     * @param count
     * @return
     */
    public static String less100(Integer count) {
        // count = new Random().nextInt(199);
        if (count == null) {
            return "0";
        }
        String value = String.valueOf(count);
        if (count > 99) {
            value = "99+";
        }
        return value;
    }

    /**
     * 最大支持999,超过则显示999+
     */
    public static String less1000(Integer count) {
        if (count == null) {
            return "0";
        }
        if (count > 999) {
            return "999+";
        } else {
            return String.valueOf(count);
        }
    }

    /**
     * 最大支持99999,超过则显示99999+
     *
     * @param count
     * @return
     */
    public static String less10W(Integer count) {
        // count = new Random().nextInt(1999999);
        if (count == null) {
            return "0";
        }
        StringBuilder strBuilder = new StringBuilder();
        if (count > 99999) {
            strBuilder.append("99999+");
        } else {
            strBuilder.append(count.intValue());
        }
        return strBuilder.toString();
    }

    public static String less10WWith1(Integer count) {
        // count = new Random().nextInt(199999);
        if (count == null) {
            return "0";
        }
        int absCount = Math.abs(count);
        String value = String.valueOf(count);
        if (absCount > 99999) {
            String language = Locale.getDefault().getLanguage().toLowerCase();
            if ("zh".equals(language)) {
                int i = count / 1000;
                if (i % 10 != 0) {
                    float v = i / 10F;
                    value = v + BaseApplication.getApplication().getResources().getString(R.string.wan);
                } else { // 一位小数为0时 不保留小数位数
                    int v = i / 10;
                    value = v + BaseApplication.getApplication().getResources().getString(R.string.wan);
                }
            } else {
                value = (count / 1000) + BaseApplication.getApplication().getResources().getString(R.string.k);
            }
        }
        return value;
    }

    public static String less10WWith1(Long count) {
        // count = new Random().nextInt(199999);
        if (count == null) {
            return "0";
        }
        long absCount = Math.abs(count);
        String value = String.valueOf(count);
        if (absCount > 99999) {
            String language = Locale.getDefault().getLanguage().toLowerCase();
            if ("zh".equals(language)) {
                long i = count / 1000;
                if (i % 10 != 0) {
                    float v = i / 10F;
                    value = v + BaseApplication.getApplication().getResources().getString(R.string.wan);
                } else { // 一位小数为0时 不保留小数位数
                    long v = i / 10;
                    value = v + BaseApplication.getApplication().getResources().getString(R.string.wan);
                }
            } else {
                value = (count / 1000) + BaseApplication.getApplication().getResources().getString(R.string.k);
            }
        }
        return value;
    }

    /**
     * 文件大小格式转换
     *
     * @param size
     * @return
     */
    public static final String fileSizeKb2Mb(double size) {
        size /= 1024.0;
        String hrSize = "";
        // 法语等语言标点会显示逗号，强制为china，显示点号
        DecimalFormat dec = null;
        try {
            dec = (DecimalFormat) NumberFormat.getInstance(Locale.CHINA);
            dec.applyPattern("0.0");
        } catch (Exception e) {
            e.printStackTrace();
            dec = new DecimalFormat("0.0");
        }
        double m = size / 1024.0;
        double g = size / 1048576.0;
        double t = size / 1073741824.0;
        if (t > 1) {
            hrSize = dec.format(t).concat("T");
        } else if (g > 1) {
            hrSize = dec.format(g).concat("G");
        } else if (m > 1) {
            hrSize = dec.format(m).concat("M");
        } else {
            hrSize = dec.format(size).concat("K");
        }
        return hrSize;
    }

    /**
     * 输入不带#号的6位十六进制字符串，输出color的int值，输入参数违法则返回白色
     *
     * @param hexString
     * @return
     */
    public static final int hexStringToColorInt(String hexString) {
        // String[] colors = {"2B2931", "4F4E55", "f81535", "ttttss", "0000FF"};
        // hexString = colors[new Random().nextInt(colors.length)];
        if (!TextUtils.isEmpty(hexString) && hexString.length() == 6) {
            try {
                return Color.parseColor("#" + hexString);
            } catch (IllegalArgumentException e) {
                Debug.w(e);
            }
        }
        return Color.parseColor("#ffffff");
    }

    /**
     * 输入不带#号的6位十六进制字符串，输出color的int值，输入参数违法则返回白色
     *
     * @param hexString
     * @return
     */
    public static final int hexStringToColorIntForSquare(String hexString) {
        // String[] colors = {"2B2931", "4F4E55", "f81535", "ttttss", "0000FF"};
        // hexString = colors[new Random().nextInt(colors.length)];
        if (!TextUtils.isEmpty(hexString) && hexString.length() == 6) {
            try {
                return Color.parseColor("#" + hexString);
            } catch (IllegalArgumentException e) {
                Debug.w(e);
                // 做个兼容，服务端可能返回超出颜色的字母。
                return Color.parseColor("#00000000");
            }
        }
        return Color.parseColor("#00000000");
    }

    /**
     * 格式化视频详情页的播放数的显示
     *
     * @param count
     * @return
     */
    public static String formatPlayNumber(Long count) {
        return formatNumber(count, 1, 2, 1, 1, 2);
    }

    /**
     * 格式化直播页点赞数的显示
     *
     * @param count
     * @return
     */
    public static String formatPraiseNumber(Long count) {
        return formatNumber(count, 1, 3, 1, 1, 2);
    }

    /**
     * 格式化整数的显示
     *
     * @param number        格式化的数
     * @param lengthWan     简体中文 [100000,99999500)区间内的数字保留小数位数，后缀为万
     * @param lengthYi      简体中文 大于99999500数的保留小数位数，后缀为亿
     * @param lengthKilo    非简体中文 [1000,999950)区间内数字保留小数位，后缀为K
     * @param lengthMillion 非简体中文 [999950,999500000)区间内数字保留小数位，后缀为M
     * @param lengthBillion 非简体中文 大于999500000数字保留小数位，后缀为B
     * @return
     */
    public static String formatNumber(Long number, int lengthWan, int lengthYi, int lengthKilo, int lengthMillion, int lengthBillion) {
        if (number == null || number < 0) {
            return "0";
        }
        StringBuilder sb = new StringBuilder();
        if (AppUtil.isSimpleChineseSystem()) {
            if (lengthWan < 0) lengthWan = 0;
            if (lengthYi < 0) lengthYi = 0;
            if (number >= 0 && number < 100000) {
                sb.append(number);
            } else if (number < 99999500) {// 考虑四舍五入
                float temp = number / 10000f;
                sb.append(String.format("%." + lengthWan + "f", temp))
                        .append(BaseApplication.getApplication().getResources().getString(R.string.wan));
            } else {
                float temp = number / 100000000f;
                sb.append(String.format("%." + lengthYi + "f", temp))
                        .append(BaseApplication.getApplication().getResources().getString(R.string.yi));
            }
        } else {
            if (lengthKilo < 0) lengthKilo = 0;
            if (lengthMillion < 0) lengthMillion = 0;
            if (lengthBillion < 0) lengthBillion = 0;
            if (number < 1000) {
                sb.append(number);
            } else if (number < 999950) {
                float temp = number / 1000f;
                sb.append(String.format("%." + lengthKilo + "f", temp))
                        .append(BaseApplication.getApplication().getResources().getString(R.string.k));
            } else if (number < 999500000) {
                float temp = number / 1000000f;
                sb.append(String.format("%." + lengthMillion + "f", temp))
                        .append(BaseApplication.getApplication().getResources().getString(R.string.m));
            } else {
                float temp = number / 1000000000f;
                sb.append(String.format("%." + lengthBillion + "f", temp))
                        .append(BaseApplication.getApplication().getResources().getString(R.string.b));
            }
        }
        return sb.toString();
    }

    /**
     * 传入服务器端过来的字符串{图片尺寸 使用宽*高的方式传参如 480*480}
     * 返回高/宽比
     *
     * @return
     */
    public static float getLiveCoverRadio(String des) {
        float defaultRadio = 4 / 3f;
        return getPicRadio(des, defaultRadio);
    }

    /**
     * 传入服务器端过来的字符串{图片尺寸 使用宽*高的方式传参如 480*480}
     * 返回高/宽比
     *
     * @return
     */
    public static float getPicRadio(String des, float defaultRadio) {
        if (TextUtils.isEmpty(des)) {
            return defaultRadio;
        }
        int index = des.indexOf("*");
        if (index <= 0) {
            return defaultRadio;
        }
        try {
            int width = Integer.valueOf(des.substring(0, index).trim());
            int height = Integer.valueOf(des.substring(index + 1).trim());
            return height / (float) width;
        } catch (Exception e) {
            Debug.e(e.getMessage());
            return defaultRadio;
        }
    }

    /**
     * 传入服务器端过来的字符串{图片尺寸 使用宽*高的方式传参如 480*480}
     * 返回宽/高比
     *
     * @return
     */
    public static float getPicWHRadio(String des, float defaultRadio) {
        if (TextUtils.isEmpty(des)) {
            return defaultRadio;
        }
        int index = des.indexOf("*");
        if (index <= 0) {
            return defaultRadio;
        }
        try {
            int width = Integer.valueOf(des.substring(0, index).trim());
            int height = Integer.valueOf(des.substring(index + 1).trim());
            return width / (float) height;
        } catch (Exception e) {
            Debug.e(e.getMessage());
            return defaultRadio;
        }
    }

    public static String formatMusicDuring(long time) {
        String result;
        long m = (time % (1000 * 60 * 60)) / (1000 * 60);
        long s = Math.round(time % (1000 * 60) / 1000f);
        result = (m > 9 ? m : "0" + m) + ":" + (s > 9 ? s : "0" + s);
        if (time < 1000 * 60 * 60) {
        } else {
            long h = (time % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
            result = (h > 9 ? h : "0" + h) + ":" + result;
        }
        return result;
    }

    public static String formatVideoDuring(long time) {
        String result;
        long m = (time % (1000 * 60 * 60)) / (1000 * 60);
        float s = time % (1000 * 60) / 1000f;
        result = String.format("%02d:%02.1f", m, s);
        return result;
    }

    public static boolean isNumber(String number) {
        if (TextUtils.isEmpty(number)) {
            return false;
        }
        return number.matches("[0-9]*$");
    }

}
