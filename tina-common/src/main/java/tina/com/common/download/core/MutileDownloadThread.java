package tina.com.common.download.core;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;

import tina.com.common.download.data.DBHelper;
import tina.com.common.download.entity.DownloadInfo;
import tina.com.common.download.entity.ThreadInfo;

/**
 * @author yxc
 * @date 2018/8/6
 */
public class MutileDownloadThread extends DownloadThread {


    public MutileDownloadThread(DownloadInfo downloadInfo, ThreadInfo threadInfo, OnDownloadListener downloadListener) {
        super(downloadInfo, threadInfo, downloadListener);
    }

    protected Map<String, String> getHttpHeaders(ThreadInfo info) {
        Map<String, String> headers = new HashMap<String, String>();
        long start = info.getStart() + info.getFinished();
        long end = info.getEnd();
        headers.put("Range", "bytes=" + start + "-" + end);
        return headers;
    }


    @Override
    protected int getResponseCode() {
        return HttpURLConnection.HTTP_PARTIAL;
    }

    @Override
    protected void insertIntoDB(ThreadInfo threadInfo) {
//        DBHelper.getInstance().newOrUpdate(threadInfo);
    }

    @Override
    protected void updateDB(ThreadInfo threadInfo) {
//        DBHelper.getInstance().newOrUpdate(threadInfo);
    }

    protected RandomAccessFile getFile(File dir, String name, long offset) throws IOException {
        File file = new File(dir, name);
        RandomAccessFile raf = new RandomAccessFile(file, "rwd");
        raf.seek(offset);
        return raf;
    }


}
