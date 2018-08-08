package tina.com.common.download.data;

import java.util.ArrayList;

import tina.com.common.download.entity.DownloadInfo;

/**
 * @author yxc
 * @date 2018/8/8
 */
public class DBController {

    private volatile static DBController instance;

    private DBController(){}

    public static DBController getInstance(){
        if (null == instance){
            synchronized (DBController.class){
                if (null == instance){
                    instance = new DBController();
                }
            }
        }
        return instance;
    }

    public ArrayList<DownloadInfo> queryAll(){
        //todo
        return new ArrayList<>();
    }


    public DownloadInfo newOrUpdate(DownloadInfo downloadInfo){
        //todo
        return new DownloadInfo();
    }





}
