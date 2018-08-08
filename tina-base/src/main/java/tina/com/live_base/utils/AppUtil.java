package tina.com.live_base.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.meitu.library.application.BaseApplication;
import com.meitu.library.util.Debug.Debug;
import com.meitu.library.util.io.SharedPreferencesUtils;

import java.util.List;
import java.util.Locale;

/**
 * @author mtb0074.
 * @since 13-12-23.
 * 与app相关工具函数，如快捷方式创建等.
 */
public class AppUtil {
    public static final String TAG = AppUtil.class.getSimpleName();

    public static final String PREFS_NAME = "software_information";
    private static final String PREFS_NAME_FOR_NOT_STARTUP = "software_information_not_startup";
    /**
     * 安装后第一次运行
     */
    public static final int INSTALL = 1;
    /**
     * 更新后第一次运行
     */
    public static final int UPDATE = 2;
    /**
     * 同一个版本非第一次运行
     */
    public static final int NOT_FIRST_RUN = 0;
    public static final String BUILD_TIME = "BUILD_TIME";
    static String ISFIRSTRUN = "isFirstRun";
    public static String VERSIONCODE = "versioncode";
    private static String OLDVERSIONCODE = "oldversioncode";
    private static String INSTALL_TIMESTAMP = "INSTALL_TIMESTAMP";
    private static String UPDATE_TIMESTAMP = "UPDATE_TIMESTAMP";

    public static String LOCALE_CHINESE_SIMPLE = "simplechinese";
    public static String LOCALE_CHINESE_TRADITIONAL = "traditionalchinese";
    public static String LOCALE_ENGLISH = "english";
    /**
     * 首次安装记录origin_channel渠道的key (V5.7新增)
     */
    private static final String ORIGIN_CHANNEL_ID_KEY = "origin_channel";


    /**
     * 判断此软件是否首次运行 =1安装，=2更新
     */
    private static int judgeFirstRunAOnTable(Context context, String table) {
        try {
            SharedPreferences settings = context.getSharedPreferences(table, Activity.MODE_PRIVATE);
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            int versionCode = info.versionCode;// 版本号
            SharedPreferences.Editor edit = settings.edit();
            if (settings.getBoolean(ISFIRSTRUN, true)) {// 第一次运行
                edit.putBoolean(ISFIRSTRUN, false);
                edit.putInt(VERSIONCODE, versionCode);
                long timestamp = System.currentTimeMillis();
                edit.putLong(INSTALL_TIMESTAMP, timestamp);
                edit.putLong(UPDATE_TIMESTAMP, timestamp);
                edit.apply();
                return INSTALL;
            } else if (settings.getInt(VERSIONCODE, 0) != versionCode) {// 升级或降级更新安装,3.1升级到3.2不显示欢迎页
                edit.putInt(OLDVERSIONCODE, settings.getInt(VERSIONCODE, 120));// 120是最后一个没有记录这个数据的版本，所以默认值120
                edit.putInt(VERSIONCODE, versionCode);
                long timestamp = System.currentTimeMillis();
                edit.putLong(UPDATE_TIMESTAMP, timestamp);
                edit.apply();
                return UPDATE;
            } else {
                return NOT_FIRST_RUN;
            }
        } catch (Exception e) {
            Debug.w(e);
        }
        return NOT_FIRST_RUN;
    }







    /**
     * 获得启动activity的名字
     *
     * @return
     */
    public static String getLauncherActivityName(String packageName) {
        if (TextUtils.isEmpty(packageName)) {
            throw new IllegalArgumentException("packageName is empty!");
        }
        try {
            PackageManager packageManager = BaseApplication.getBaseApplication().getPackageManager();
            Intent intent = new Intent(Intent.ACTION_MAIN, null);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);
            intent.setPackage(packageName);
            List<ResolveInfo> packageInfos = packageManager.queryIntentActivities(intent, 0);
            int size = packageInfos.size();
            for (int i = 0; i < size; i++) {
                String pName = packageInfos.get(i).activityInfo.packageName;
                if (packageName.equals(pName)) {
                    return packageInfos.get(i).activityInfo.name;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "com.meitu.meipaimv.StartupActivity";
    }

    /**
     * 获取对应包名的包信息
     *
     * @param packageName
     * @return 获取不到返回null
     */
    public static PackageInfo getPackageInfo(final String packageName) {
        PackageInfo packageInfo;
        try {
            packageInfo = BaseApplication.getBaseApplication().getPackageManager().getPackageInfo(packageName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            packageInfo = null;
        }
        return packageInfo;
    }

    /**
     * 获取包信息
     *
     * @return 获取不到返回null
     */
    public static PackageInfo getPackageInfo() {
        return getPackageInfo(BaseApplication.getBaseApplication().getPackageName());
    }

    /**
     * 获取version code
     *
     * @return 获取不到返回0
     */
    public static int getAppVersionCode() {
        PackageInfo info = getPackageInfo();

        if (info == null) {
            return 0;
        } else {
            return info.versionCode;
        }
    }

    /**
     * 获取 version name
     *
     * @return 获取不到返回""
     */
    public static String getAppVersionName() {
        PackageInfo info = getPackageInfo();

        if (info == null) {
            return "";
        } else {
            return info.versionName;
        }
    }

    /**
     * 获取包名
     *
     * @return 获取不到返回""
     */
    public static String getAppPackageName() {
        PackageInfo info = getPackageInfo();

        if (info == null) {
            return "";
        } else {
            return info.packageName;
        }
    }

    /**
     * 判断App是否在前台运行
     *
     * @param context
     * @return
     */
    public static boolean isRunningForeground(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (am.getRunningTasks(1) != null && am.getRunningTasks(1).size() > 0) {
            ComponentName cn = am.getRunningTasks(1).get(0).topActivity;
            String currentPackageName = cn.getPackageName();
            return !TextUtils.isEmpty(currentPackageName) && currentPackageName.equals(getAppPackageName());
        } else {
            return false;
        }
    }

    /**
     * 判断activity是否在当前App顶层, 如果home出去/进入其他App，也返回true
     *
     * @param context
     * @return
     */
    public static boolean isActivityRunningOnTop(Context context, String activityName) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (am.getRunningTasks(1) != null) {
            if (am.getRunningTasks(1).size() > 0) {
                ComponentName cn = am.getRunningTasks(1).get(0).topActivity;
                String currentPackageName = cn.getPackageName();

                if (!TextUtils.isEmpty(currentPackageName) && currentPackageName.equals(getAppPackageName())) {
                    String className = cn.getClassName();
                    // topActivity的名字和需要验证的activity一致
                    return !TextUtils.isEmpty(className) && className.equals(activityName);
                }
            } else {
                /*
                  这里是home出去或者打开其他app，返回true
                 */
                return true;
            }
        }
        // 包名不一样，被其他应用覆盖，可能是home键退出了
        return true;
    }


    /**
     * 启动一个app
     * com -- ComponentName 对象，包含apk的包名和主Activity名
     * param -- 需要传给apk的参数
     */
    public static void startApp(Context context, String packageName) {
        Debug.d("MPPush", "startApp context=" + context + " packageName= " + packageName);
        if (!TextUtils.isEmpty(packageName) && context != null) {
            try {
                Intent LaunchIntent = context.getPackageManager().getLaunchIntentForPackage(packageName);
                context.startActivity(LaunchIntent);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 判断当前的系统是否为简体中文环境
     *
     * @return
     */
    public static final boolean isSimpleChineseSystem() {
        Locale locale = Locale.getDefault();
        String language = locale.getLanguage();
        String country = locale.getCountry();
        if (language != null && "zh".equalsIgnoreCase(language)) {
            if ("cn".equalsIgnoreCase(country)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断系统的语言环境，非中文（简体、繁体）视为英文环境
     *
     * @return
     */
    public static String getLocaleLanguage() {
        String result = LOCALE_ENGLISH;
        Locale locale = Locale.getDefault();
        if (locale != null) {
            String country = locale.getCountry();
            String language = locale.getLanguage();
            if ("CN".equalsIgnoreCase(country) && "ZH".equalsIgnoreCase(language)) {
                result = LOCALE_CHINESE_SIMPLE;
            } else if (("TW".equalsIgnoreCase(country) || "HK".equalsIgnoreCase(country))
                    && "ZH".equalsIgnoreCase(language)) {
                result = LOCALE_CHINESE_TRADITIONAL;
            }
        }
        return result;
    }

    public static final String DEFAULE_TABLE_NAME = "meitu_data";

    /**
     * 获取原始渠道id
     */
    public static String getOriginChannelID() {
        return SharedPreferencesUtils.getSharedPreferencesValue(DEFAULE_TABLE_NAME,
                ORIGIN_CHANNEL_ID_KEY, "");
    }



    /**
     * 判断app当前是否已开启，通过判断给定Activity是否在属于当前包名的task的栈底，不在则有其他activity存在，说明已开启，在则因为未开启。
     */
    public static boolean isAppOpened(Activity activity) {
        ActivityManager mActivityManager =
                ((ActivityManager) BaseApplication.getApplication().getSystemService(Context.ACTIVITY_SERVICE));
        String activityName = activity.getClass().getName();
        List<ActivityManager.RunningTaskInfo> tasksInfo = mActivityManager.getRunningTasks(3);
        if (tasksInfo != null && tasksInfo.size() > 0) {
            for (int i = 0; i < tasksInfo.size(); i++) {
                ComponentName cn = tasksInfo.get(i).baseActivity;
                String currentPackageName = cn.getPackageName();
                if (!TextUtils.isEmpty(currentPackageName) && currentPackageName.equals(getAppPackageName())) {
                    String className = cn.getClassName();
                    Debug.d(TAG, "activityName=" + activityName + " baseActivity = " + className);
                    if (!activityName.equals(className)) {// 当前activity不在堆栈底，视为app已经开启
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * 获得当前进程名称
     */
    public static String getCurrentProcessName(@NonNull Context context) {
        try {
            int pid = android.os.Process.myPid();
            ActivityManager mActivityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            if (mActivityManager == null) {
                return null;
            }
            for (ActivityManager.RunningAppProcessInfo appProcess : mActivityManager.getRunningAppProcesses()) {
                if (appProcess.pid == pid) {
                    return appProcess.processName;
                }
            }
        } catch (Exception e) {
            // no-op
        }
        return null;
    }

    /**
     * 判断当前Application是否是主进程
     */
    public static boolean isMainProcess() {
        return isMainProcess(getCurrentProcessName(BaseApplication.getApplication()));
    }

    /**
     * 判断当前Application是否是主进程
     */
    public static boolean isMainProcess(String processName) {
        return null != processName && !processName.contains(":");
    }
}
