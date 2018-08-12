package tina.com.common.download.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.ToMany;
import org.greenrobot.greendao.annotation.Transient;

import java.io.File;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;

import tina.com.common.download.utils.DownloadConfig;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;
import com.live_common.download.gen.DaoSession;
import com.live_common.download.gen.ThreadInfoDao;
import com.live_common.download.gen.DownloadInfoDao;

/**
 * @author yxc
 * @date 2018/8/1
 */
@Entity
public class DownloadInfo implements Serializable{

    static final long serialVersionUID = 42L;
    @Id
    public String tag;

    public String fileName;

    public String url;

    public long finish;

    public long length;

    public int status;

    @Transient
    public File dir;

    public int progress;

    public String name;

    public boolean acceptRanges;

    public String image;

    public String packageName;

    public String versionCode;

//    @ToMany(referencedJoinProperty="tag")
//    public List<ThreadInfo> threadInfoList;

    @Transient
    public List<ThreadInfo> threadInfoList;

    public DownloadInfo(){}

    public DownloadInfo(String name, String fileName, String iamge, String url,File file) {
        this.name = name;
        this.fileName = fileName;
        this.image = iamge;
        this.url = url;
        this.dir = file;
    }

    @Generated(hash = 1756508593)
    public DownloadInfo(String tag, String fileName, String url, long finish, long length, int status,
            int progress, String name, boolean acceptRanges, String image, String packageName,
            String versionCode) {
        this.tag = tag;
        this.fileName = fileName;
        this.url = url;
        this.finish = finish;
        this.length = length;
        this.status = status;
        this.progress = progress;
        this.name = name;
        this.acceptRanges = acceptRanges;
        this.image = image;
        this.packageName = packageName;
        this.versionCode = versionCode;
    }


    @Override
    public boolean equals(Object o) {
        return this.hashCode() == o.hashCode();
    }

    @Override
    public int hashCode() {
        return url.hashCode();
    }


    public File getDir(){
        return dir;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    @Override
    public String toString() {
        return fileName + ":" + getDownloadPerSize() + "::" + DownloadStatus.getStausText(status) + ":" + progress ;
    }

    private static final DecimalFormat DF = new DecimalFormat("0.00");

    public String getDownloadPerSize() {
        return DF.format((float) finish / (1024 * 1024)) + "M/" + DF.format((float) length / (1024 * 1024)) + "M";
    }

    public void reset() {
        finish = 0;
        progress = 0;
        File file = DownloadConfig.getInstance().getDownloadFile(url);
        if (file.exists()){
            file.delete();
        }
    }

    public String getFileName() {
        return this.fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public long getFinish() {
        return this.finish;
    }

    public void setFinish(long finish) {
        this.finish = finish;
    }

    public long getLength() {
        return this.length;
    }

    public void setLength(long length) {
        this.length = length;
    }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getProgress() {
        return this.progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean getAcceptRanges() {
        return this.acceptRanges;
    }

    public void setAcceptRanges(boolean acceptRanges) {
        this.acceptRanges = acceptRanges;
    }

    public String getImage() {
        return this.image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPackageName() {
        return this.packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getVersionCode() {
        return this.versionCode;
    }

    public void setVersionCode(String versionCode) {
        this.versionCode = versionCode;
    }

}
