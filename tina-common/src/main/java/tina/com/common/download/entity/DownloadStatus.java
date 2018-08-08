package tina.com.common.download.entity;

/**
 * @author yxc
 * @date 2018/8/1
 */
public class DownloadStatus {
    public static final int IDLE = 0;
    public static final int WAITING = 1;

    public static final int STARTED = 2;
    public static final int CONNECTING = 3;
    public static final int CONNECTED = 4;
    public static final int DOWNLOADING = 5;
    public static final int PAUSED = 6;
    public static final int CANCELED = 7;
    public static final int COMPLETED = 8;

    public static final int INSTALLED = 9;
    public static final int FAILED = 10;

    public static final int STATUS_STORAGE_NOT_ENOUGH = 11;
}
