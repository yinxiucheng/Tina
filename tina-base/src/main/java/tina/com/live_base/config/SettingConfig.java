package tina.com.live_base.config;

import android.content.Context;
import android.content.SharedPreferences;

import com.meitu.library.application.BaseApplication;

import tina.com.live_base.utils.IdentifyUserAreaUtil;

/**
 * 设置界面功能配置文件
 *
 * @author Z.P.F
 * @time 2014年5月6日 下午1:48:49
 */
public abstract class SettingConfig {

    private static final String CONFIG_FILE_NAME = "setting_config";

    private static final String KEY_IS_PUSH = "canPush";

    // 是否为港台地区用户
    private static final String USER_LOCAL = "USER_LOCAL";

    /*
     * 表情键盘相关变量存储
     */
    private static final String SP_FILE_NAME = "sp_emoji_keyboard";
    /**
     * 播主首次直播
     */
    private static final String SP_KEY_FIRST_LIVE = "SP_KEY_FIRST_LIVE";

    /**
     * 首次提示用户有自己的历史直播可观看
     */
    private static final String SP_KEY_FIRST_HAVE_HISTORY_LIVE = "SP_KEY_FIRST_HAVE_HISTORY_LIVE";

    /**
     * 播放 x 秒后进行视频统计
     */
    private static final String SP_KEY_PLAY_VIDEO_REPORT_TIME_S = "SP_KEY_PLAY_VIDEO_REPORT_TIME_S";

    /**
     * 直播是否异常关闭
     */
    private static final String SP_KEY_IS_LIVE_CLOSE_SUCCESS = "SP_KEY_IS_LIVE_CLOSE_SUCCESS";

    /**
     * 重启app后是否要滑动到“有戏”tab展示
     */
    private static final String SP_KEY_IS_MUSICAL_SHOW_TAB = "SP_KEY_IS_MUSICAL_SHOW_TAB";

    /**
     * 重启app后，判断是否要定位到“有戏”频道
     *
     * @return
     */
    public static boolean isMusicalTabToShow() {
        SharedPreferences sp = BaseApplication.getApplication().getSharedPreferences(SP_FILE_NAME, 0);
        return sp.getBoolean(SP_KEY_IS_MUSICAL_SHOW_TAB, false);
    }


    public static int getUserLocale() {
        SharedPreferences sp =
                BaseApplication.getApplication().getSharedPreferences(CONFIG_FILE_NAME, Context.MODE_PRIVATE);
        return sp.getInt(USER_LOCAL, IdentifyUserAreaUtil.AreaType.MAINLAND.getValue());
    }

    public static void setUserLocale(final int localeValue) {
        SharedPreferences sp =
                BaseApplication.getApplication().getSharedPreferences(CONFIG_FILE_NAME, Context.MODE_PRIVATE);
        sp.edit().putInt(USER_LOCAL, localeValue).apply();
    }
}
