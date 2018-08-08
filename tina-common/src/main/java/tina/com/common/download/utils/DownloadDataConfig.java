package tina.com.common.download.utils;

import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.text.TextUtils;

import java.io.File;

/**
 * Created by xiuchengyin on 2018/4/19.
 */

public class DownloadDataConfig {

    public static String downloadPath = "";

    private static final String DOWNLOAD_DIR = "download";

    public static final int DEFAULT_MAX_THREAD_NUMBER = 10;

    public static final int DEFAULT_THREAD_NUMBER = 1;

    private boolean useExternalStorageDir = true;

    /**
     * thread number in the pool
     */
    private int maxThreadNum = DEFAULT_MAX_THREAD_NUMBER;

    /**
     * thread number for each download
     */
    private int threadNum = DEFAULT_THREAD_NUMBER;

    private volatile static DownloadDataConfig instance;

    private DownloadDataConfig() {}

    public static DownloadDataConfig getInstance(){
        if (null == instance){
            synchronized (DownloadDataConfig.class){
                if (null == instance){
                    instance = new DownloadDataConfig();
                }
            }
        }
        return instance;
    }

    public boolean isUseExternalStorageDir() {
        return useExternalStorageDir;
    }

    public void setUseExternalStorageDir(boolean useExternalStorageDir) {
        this.useExternalStorageDir = useExternalStorageDir;
    }

    public void setDownLoadPath(String path, boolean useExternalStorageDir) {
        this.useExternalStorageDir = useExternalStorageDir;
        downloadPath = path;
    }

    private File getDownloadDir() {
        File file = new File(downloadPath);
        return file;
    }

    public  File getDir() {
        if (TextUtils.isEmpty(downloadPath)) {
            return getDefaultDownloadDir();
        } else {
            return new File(DownloadDataConfig.downloadPath);
        }
    }

    public File getDefaultDownloadDir() {
        return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
    }




    //获取可用SD卡存储
    public static long getEnableSize() {
        File path = Environment.getExternalStorageDirectory();
        if (null == path) {
            return 0;
        }
        StatFs stat = new StatFs(path.getPath());
        long blockSize;  //每一个存储块的大小
        long tatalBlocks;  //存储块的总数
        long avaliableBlocks;  //可以使用的存储块的个数

        //获取当前的版本的等级,版本大于18也就是4.4.2，使用没过时的api
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN_MR2) {
            blockSize = stat.getBlockSizeLong();  //每一个存储块的大小
            tatalBlocks = stat.getBlockCountLong();  //存储块的总数
            avaliableBlocks = stat.getAvailableBlocksLong();  //可以使用的存储块的个数

        } else {
            blockSize = stat.getBlockSizeLong();  //每一个存储块的大小
            tatalBlocks = stat.getBlockCountLong();  //存储块的总数
            avaliableBlocks = stat.getAvailableBlocksLong();  //可以使用的存储块的个数
        }
        return blockSize * avaliableBlocks;
    }

    public int getMaxThreadNum() {
        return maxThreadNum;
    }

    public void setMaxThreadNum(int maxThreadNum) {
        this.maxThreadNum = maxThreadNum;
    }

    public int getThreadNum() {
        return threadNum;
    }

    public void setThreadNum(int threadNum) {
        this.threadNum = threadNum;
    }

}
