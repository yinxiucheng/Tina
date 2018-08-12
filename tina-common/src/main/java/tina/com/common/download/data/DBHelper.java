package tina.com.common.download.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.live_common.download.gen.DaoMaster;
import com.live_common.download.gen.DaoSession;
import com.live_common.download.gen.DownloadInfoDao;
import com.live_common.download.gen.ThreadInfoDao;

import java.util.List;

import tina.com.common.download.entity.DownloadInfo;
import tina.com.common.download.entity.ThreadInfo;
import tina.com.live_base.utils.BaseUtils;

/**
 * @author yxc
 * @date 2018/8/10
 */
public class DBHelper {

    private static volatile DBHelper instance;

    private static final String DB_NAME = "tina_download";

    private DaoMaster daoMaster;

    private DaoSession daoSession;

    private SQLiteDatabase mWritableDatabase;

    private DownloadInfoDao mDownloadInfoDao;

    private ThreadInfoDao mThreadInfoDao;

    private Object _dlock = new Object();

    private Object _tlock = new Object();


    public static DBHelper getInstance(){
        if (null == instance){
            synchronized (DBHelper.class){
                if (null == instance){
                    instance = new DBHelper(BaseUtils.getContext());
                }
            }
        }
        return instance;
    }

    public DBHelper(Context context){
        mWritableDatabase = new TinaDBOpenHelper(context, getDbName(), null).getWritableDatabase();
        daoSession = new DaoMaster(mWritableDatabase).newSession();
    }


    public class TinaDBOpenHelper extends DaoMaster.OpenHelper{

        public TinaDBOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
            super(context, name, factory);
        }
    }


    private String getDbName(){
        return DB_NAME;
    }


    private DownloadInfoDao getDownloadInfoDao(){
        mDownloadInfoDao = daoSession.getDownloadInfoDao();
       return mDownloadInfoDao;
    }

    private ThreadInfoDao getThreadInfoDao(){
        mThreadInfoDao = daoSession.getThreadInfoDao();
        return mThreadInfoDao;
    }

    public void insertDownloadInfoTX(List<DownloadInfo> downloadInfos){
        if (null == mDownloadInfoDao){
            getDownloadInfoDao();
        }
        mDownloadInfoDao.insertOrReplaceInTx(downloadInfos, true);
    }

    /**
     * 获取 DownloadInfo对应的 threadInfo
     * @param tag
     * @return
     */
//    public List<ThreadInfo> getThreadInfoListByTag(String tag){
//        if (null == mThreadInfoDao){
//            getThreadInfoDao();
//        }
//        return mThreadInfoDao._queryDownloadInfo_ThreadInfoList(tag);
//    }

    /**
     * 获取所有的 DownloadInfo
     * @return
     */
    public List<DownloadInfo> queryDownloadInfoAll(){
        if (null == mDownloadInfoDao){
            getDownloadInfoDao();
        }
        return mDownloadInfoDao.loadAll();
    }

    /**
     * 查询单个的 DownloadInfo
     *
//     * @param tag
     * @return
     */
    public DownloadInfo queryDownloadInfo(String tag) {
        if (null == mDownloadInfoDao){
            getDownloadInfoDao();
        }
        return mDownloadInfoDao.load(tag);
    }

    public void newOrUpdate(DownloadInfo downloadInfo){
        if (null == mDownloadInfoDao){
            getDownloadInfoDao();
        }
        synchronized (_dlock){
            mDownloadInfoDao.insertOrReplace(downloadInfo);
        }
    }

    public void newOrUpdateThreadInfo(ThreadInfo threadInfo){
        if (null == mThreadInfoDao){
            getThreadInfoDao();
        }
        synchronized (_tlock){
            mThreadInfoDao.insertOrReplace(threadInfo);
        }
    }

    public void deleteDownloadInfoByTag(String tag) {
        if (null == mDownloadInfoDao){
            getDownloadInfoDao();
        }
        synchronized (_dlock){
            mDownloadInfoDao.deleteByKey(tag);
        }
    }

//    public void deleteThreadInfoByTag(String tag) {
//        if (null == mThreadInfoDao){
//            getThreadInfoDao();
//        }
//        List<ThreadInfo> threadInfos = queryThreadInfos(tag);
//        synchronized (_tlock){
//            mThreadInfoDao.deleteInTx(threadInfos);
//        }
//    }

    /**
     * 查询tag的所有ThreadInfo
     *
     * @param tag
     * @return
     */
//    public List<ThreadInfo> queryThreadInfos(String tag) {
//        if (null == mThreadInfoDao){
//            getThreadInfoDao();
//        }
//        List<ThreadInfo> list = mThreadInfoDao.queryBuilder().where(ThreadInfoDao.Properties.Tag.eq(tag)).list();
//        return list;
//    }

    /**
     * 查询单个ThreadInfo
     *
     * @param tag
     * @param index
     * @return
     */
//    public ThreadInfo queryThreadInfo(String tag, String index){
//        if (null == mThreadInfoDao){
//            getThreadInfoDao();
//        }
//        ThreadInfo threadInfo = mThreadInfoDao.queryBuilder().
//                where(ThreadInfoDao.Properties.Tag.eq(tag), ThreadInfoDao.Properties.Index.eq(index)).unique();
//        return threadInfo;
//    }




}
