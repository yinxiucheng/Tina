package tina.com.common.download.entity;

import java.io.File;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.HashMap;

import tina.com.common.download.utils.DownloadConfig;

/**
 * @author yxc
 * @date 2018/8/1
 */
public class DownloadInfo implements Serializable {

    public static final int DEFAULT_PROCESS_BEGIN = 0;

    public String id;

    public String tag;

    public String fileName;

    public String url;

    public long finish;

    public long length;

    public int status;

    public File dir;

    public int progress;

    public String name;

    public boolean acceptRanges;

    public HashMap<Integer, Integer> ranges;

    public String image;

    public String packageName;

    public String versionCode;

    public DownloadInfo(){}

    public DownloadInfo(String name, String fileName, String iamge, String url,File file) {
        this.name = name;
        this.fileName = fileName;
        this.image = iamge;
        this.url = url;
        this.id = url;
        this.dir = file;
    }

    public long getFinish() {
        return finish;
    }

    public void setFinish(long finish) {
        this.finish = finish;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }


    public long getLength() {
        return length;
    }

    public void setLength(long length) {
        this.length = length;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(String versionCode) {
        this.versionCode = versionCode;
    }

    //获取当前状态对应 下一个status的 文案
    public String getNextStausText(){
        if (status == DownloadStatus.PAUSED){
            return "resume";
        }else if (status == DownloadStatus.DOWNLOADING){
            return "pause";
        }else if (status == DownloadStatus.IDLE){
            return "download";
        }else if (status == DownloadStatus.WAITING){
            return "waiting";
        }else if (status == DownloadStatus.COMPLETED){
            return "install";
        }else if (status == DownloadStatus.INSTALLED){
            return "open";
        }else if (status == DownloadStatus.FAILED){
            return "error";
        }else {
            return "download";
        }
    }


    //获取
    public String getStausText(){
        if (status == DownloadStatus.PAUSED){
            return "pause";
        }else if (status == DownloadStatus.DOWNLOADING){
            return "downloading";
        }else if (status == DownloadStatus.IDLE){
            return "idle";
        }else if (status == DownloadStatus.WAITING){
            return "waiting";
        }else if (status == DownloadStatus.COMPLETED){
            return "completed";
        }else if (status == DownloadStatus.INSTALLED){
            return "installed";
        }else if (status == DownloadStatus.FAILED){
            return "error";
        }else {
            return "download";
        }
    }

    @Override
    public boolean equals(Object o) {
        return this.hashCode() == o.hashCode();
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }


    public File getDir(){
        return dir;
    }

    //todo 获取文件需要调整
    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public boolean isAcceptRanges() {
        return acceptRanges;
    }

    public void setAcceptRanges(boolean acceptRanges) {
        this.acceptRanges = acceptRanges;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return fileName + ":" + finish + "/" + length + "::" + status ;
    }

    private static final DecimalFormat DF = new DecimalFormat("0.00");

    public String getDownloadPerSize() {
        return DF.format((float) finish / (1024 * 1024)) + "M/" + DF.format((float) length / (1024 * 1024)) + "M";
    }

    public void reset() {
        finish = 0;
        ranges = null;
        progress = 0;
        File file = DownloadConfig.getInstance().getDownloadFile(url);
        if (file.exists()){
            file.delete();
        }
    }

}
