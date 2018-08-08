package tina.com.live_base.utils;

import android.app.Activity;
import android.os.Looper;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;

import com.meitu.library.application.BaseApplication;
import com.meitu.library.util.ui.widgets.MTToast;

/**
 * User: Gyl
 * Date: 14-5-19  下午12:52
 * 基本操作
 */
public class BaseUIOption {

    public static String EXTRA_GOHOME = "EXTRA_GOHOME";
    public static String EXTRA_OPEN_MESSAGE = "EXTRA_OPEN_MESSAGE";

    /**
     * 替换fragment
     *
     * @param fragment       需要替换的fragment
     * @param tag            Fragment的tag
     * @param container      装在fragment的容器id
     * @param isAddBackStack 是否需要保存fragment到后台堆栈
     * @param anim           是否需要切换动画
     */
    public static void replaceFragment(FragmentActivity activity, Fragment fragment, String tag, int container,
                                       boolean isAddBackStack, boolean anim) {
        if (activity != null && !activity.isFinishing()) {
            FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
//             设置动画效果,要在add/replace之前
//             if (anim) {
//             transaction.setCustomAnimations(R.anim.center_scale_in,
//             R.anim.fade_out, R.anim.empty, R.anim.center_scale_out);
//             } else {
//             transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
//             }
            transaction.replace(container, fragment, tag);
            if (isAddBackStack) {
                transaction.addToBackStack(tag);
            }
            // 判断activity是否已经被销毁，用户按下返回键finish掉app时，如果依然commit，会crash
            // 详细的注解：警告：你只能在activity处于可保存状态的状态时，比如running中，onPause()方法和onStop()方法中提交事务，否则会引发异常。
            // 这是因为fragment的状态会丢失。如果要在可能丢失状态的情况下提交事务，请使用commitAllowingStateLoss()。
            transaction.commitAllowingStateLoss();
        }
    }

    public static void showToastLong(String res) {
        if (!TextUtils.isEmpty(res)) {
            MTToast.showLong(res);
        }
    }

    public static void showToastInCenter(String res) {
        if (!TextUtils.isEmpty(res)) {
            MTToast.showInCenter(BaseApplication.getBaseApplication(), res);
        }
    }

    public static void showToast(String res, int duration) {
        if (!TextUtils.isEmpty(res)) {
            MTToast.show(res, duration);
        }
    }

    public static void showToast(int id) {
        showToast(BaseApplication.getBaseApplication().getResources().getString(id), MTToast.LENGTH_SHORT);
    }

    public static void showToast(String res) {
        showToast(res, MTToast.LENGTH_SHORT);
    }

    public static void toastOnUIThread(Activity activity, final String content, final int pDuration) {
        if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
            showToast(content, pDuration);
        } else {
            if (activity != null) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        showToast(content, pDuration);
                    }
                });
            }
        }
    }



    private static long processFinishTime;

    public static synchronized boolean isProcessing() {
        return isProcessing(300);
    }

    /**
     * 是否处于执行任务状态，若不是，则设接下来[minTime]ms 为执行任务状态
     *
     * @param minTime 任务执行时长
     * @return 是否处于执行任务状态
     */
    public static synchronized boolean isProcessing(final long minTime) {
        long newTime = newEffecttiveTime(minTime, processFinishTime);
        if (newTime == processFinishTime) {
            return true;
        } else {
            processFinishTime = newTime;
            return false;
        }
    }

    /**
     * 以上次任务完成时间戳为基准，计算下次任务执行到的时间戳，若上次时间戳未到，则直接返回上次时间戳
     *
     * @param minTime  任务执行时长
     * @param lastTime 上次完成时间戳，以包括休眠时间的系统运行时长计时
     * @return 下次任务执行到的时间戳
     */
    public static synchronized long newEffecttiveTime(final long minTime, long lastTime) {
        if (SystemClock.elapsedRealtime() >= lastTime) {
            return SystemClock.elapsedRealtime() + minTime;
        }
        return lastTime;
    }


}
