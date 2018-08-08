package tina.com.common.download.observer;

import android.content.Context;

import java.util.Observable;

import tina.com.common.download.entity.DownloadInfo;

/**
 * @author yxc
 * @date 2018/8/1
 */
public class DataChanger extends Observable {
    private static DataChanger dataWatcher;
    private Context context;

    private DataChanger(Context context){
        this.context = context;
    }

    public static synchronized DataChanger getInstance(Context context){
        if (null == dataWatcher){
            dataWatcher = new DataChanger(context);
        }
        return dataWatcher;
    }

    public void postStatus(DownloadInfo downloadInfo){
        setChanged();
        notifyObservers(downloadInfo);
    }

}
