package tina.com.live_base.utils;

import tina.com.live_base.config.SettingConfig;

/**
 * Created by jsg on 2015/3/30.
 */
public class IdentifyUserAreaUtil {

    public static boolean isHkOrTwUser() {
        return SettingConfig.getUserLocale() == AreaType.HK_TW_MACAO.getValue();
    }

    public static enum AreaType {
        MAINLAND(1), HK_TW_MACAO(2) {
        };
        private int value;

        private AreaType(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }
}
