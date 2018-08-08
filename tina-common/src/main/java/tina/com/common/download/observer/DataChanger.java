package tina.com.common.download.observer;

import android.content.Context;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Observable;

import tina.com.common.download.entity.DownloadInfo;
import tina.com.common.download.entity.DownloadStatus;

/**
 * @author yxc
 * @date 2018/8/1
 */
public class DataChanger extends Observable {
    private volatile static DataChanger dataWatcher;
    private Context context;
    private LinkedHashMap<String, DownloadInfo> mOperateDownloadInfoList;

    private DataChanger(Context context){
        this.context = context;
        mOperateDownloadInfoList = new LinkedHashMap<>();
    }

    public static synchronized DataChanger getInstance(Context context){
        if (null == dataWatcher){
            synchronized (DataChanger.class){
                if (null == dataWatcher){
                    dataWatcher = new DataChanger(context);
                }
            }
        }
        return dataWatcher;
    }

    public void postStatus(DownloadInfo downloadInfo){
        mOperateDownloadInfoList.put(downloadInfo.tag, downloadInfo);
        //todo 数据库插入或更新
        setChanged();
        notifyObservers(downloadInfo);
    }

    public ArrayList<DownloadInfo> queryAllRecoverableEntries() {
        ArrayList<DownloadInfo> mRecoverableEntries = null;
        for (Map.Entry<String, DownloadInfo> entry : mOperateDownloadInfoList.entrySet()) {
            if (entry.getValue().status == DownloadStatus.PAUSED) {
                if (mRecoverableEntries == null) {
                    mRecoverableEntries = new ArrayList<>();
                }
                mRecoverableEntries.add(entry.getValue());
            }
        }
        return mRecoverableEntries;
    }

    public DownloadInfo queryDownloadEntryById(String tag) {
        return mOperateDownloadInfoList.get(tag);
    }

    public void addToOperatedEntryMap(String tag, DownloadInfo value){
        mOperateDownloadInfoList.put(tag, value);
    }

    public boolean containsDownloadEntry(String tag) {
        return mOperateDownloadInfoList.containsKey(tag);
    }

    public void deleteDownloadEntry(String tag){
        mOperateDownloadInfoList.remove(tag);
        //todo 数据库删除
//        DBController.getInstance(context).deleteById(id);
    }



}
