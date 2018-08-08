package tina.com.live_base.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Parcel;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.style.URLSpan;
import android.view.View;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SuppressLint("ParcelCreator")
public class MTURLSpan extends URLSpan {// implements ParcelableSpan

    public static final int REQUEST_CODE_TOPIC = 400;

    public static final String EXTRA_KEY_TOPIC = "topic";

    private static final Pattern TOPIC_URL = Pattern.compile("(#[^#]+#)");
    private static final Pattern MENTION_URL = Pattern.compile("@[^\\s　：:@#]+");

    private static final String TOPIC_SCHEME = "com.meitu.meipai.topic";
    private static final String TOPIC_HOST = TOPIC_SCHEME + "://";

    private static final String MENTION_SCHEME = "com.meitu.meipai.mention";
    private static final String MENTION_HOST = MENTION_SCHEME + "://";

    private static final String TOPIC_LINK_COLOR = "#5c75a7";

    public static final int TYPE_TOPIC = 1;
    private static final int TYPE_MENTION = 2;
    public static final int TYPE_ALL = 3;

    /**
     * Url地址用#分割参数, 因为用户名可能拥有&符号
     */
    private static final String URL_PARAM_SPLIT = "#";
    /**
     * 数字正则表达式
     */
    private static final Pattern PATTERN_INT = Pattern.compile("[0-9]*");

    private final String mURL;
    private String mColorNormal = null;

    public MTURLSpan(String url, String colorNormal, String colorPress) {
        super(url);
        mURL = url;
        mColorNormal = colorNormal;
    }

    public int getSpanTypeId() {
        return super.getSpanTypeId();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mURL);
    }

    public String getURL() {
        return mURL;
    }

    public void onClick(View widget) {
        Uri uri = Uri.parse(getURL());
        if (TOPIC_SCHEME.equals(uri.getScheme())) {
            onTopicAction(widget);
        } else if (MENTION_SCHEME.equals(uri.getScheme())) {
            onMentionAction(widget);
        }
    }

    // 点击主题链接的响应：跳转到主题页面
    private void onTopicAction(View widget) {
        final Context context = widget.getContext();
        int index = getURL().indexOf(TOPIC_HOST);
        // 以主题链接开头，则截取话题部分内容
        if (index == 0) {
        }
    }

    // 点击@链接的响应：跳转到个人主页
    // Url地址用#分割参数,输出为: com.meitu.meipai.mention://xxx=123#@ccs
    private void onMentionAction(View widget) {
        int index = getURL().indexOf(MENTION_HOST);

        if (index == 0) {
        }
    }

    @Override
    public void updateDrawState(TextPaint tp) {
        if (TextUtils.isEmpty(mColorNormal)) {
            tp.setColor(Color.parseColor(TOPIC_LINK_COLOR));
        } else {
            tp.setColor(Color.parseColor(mColorNormal));
        }
    }


    public static String convertText(String text) {
        if (TextUtils.isEmpty(text)) {
            return text;
        }
        // 港澳台地区，双#话题替换为单#空格
        if (!IdentifyUserAreaUtil.isHkOrTwUser()) {
            return text;
        } else if (!text.endsWith("#")) {
            return text;
        }
        StringBuilder ssb = new StringBuilder(text);
        Matcher m = TOPIC_URL.matcher(text);
        while (m.find()) {
            int end = m.end();
            ssb.replace(end - 1, end, " ");
        }
        return ssb.toString();
    }


}
