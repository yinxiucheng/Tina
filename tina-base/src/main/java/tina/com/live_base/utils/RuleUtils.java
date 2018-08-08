package tina.com.live_base.utils;

import android.support.annotation.NonNull;
import android.text.TextUtils;

/**
 * 图片地址规则处理工具
 *
 * @author ccs 2017.06.14
 */
public class RuleUtils {

    /**
     * 正常图片格式
     */
    private static final String[] SUPPORT_EXTENSION = {
            ".jpg", ".gif", ".png"
    };

    /**
     * 判断是否是带格式的地址
     * 如果是以.(jpg|gif|png)结尾的则认为是不带格式的, 如果增加了!配置的则认为是带格式的
     * webp格式地址样例 http://mvimg10.meitudata.com/58f78bc660d708495.jpg!webp
     */
    public static boolean isRuleUrl(@NonNull String url) {
        boolean isRule = true;
        for (String reg : SUPPORT_EXTENSION) {
            if (url.endsWith(reg)) {
                isRule = false;
                break;
            }
        }
        return isRule;
    }

    /**
     * 将带格式的地址转换成普通的地址
     * 样例 http://test.abc.com/a.jpg!webp 处理后变成http://test.abc.com/a.jpg
     * 样例 http://test.abc.com/a.png!webp 处理后变成http://test.abc.com/a.png
     */
    public static String change2NormalUrl(@NonNull String url) {
        for (String reg : SUPPORT_EXTENSION) {
            if (url.contains(reg) && !url.endsWith(reg)) {
                url = url.replaceAll(reg + ".*", reg);
            }
            if (!isRuleUrl(url)) {
                return url;
            }
        }
        return url;
    }


    public static boolean isValidImageUrl(@NonNull String url){
        boolean isValid = true;
        if (TextUtils.isEmpty(url)){
            return isValid;
        }
        for (String reg : SUPPORT_EXTENSION) {
            if (url.endsWith(reg)) {
                isValid = false;
                break;
            }
        }
        return isValid;
    }

}
