package tina.com.common.download.entity;

public class ThreadInfo {

    private int id;
    private String tag;
    private String url;
    private long start;
    private long end;
    private long finished;
    private int status;

    public ThreadInfo() {

    }

    public ThreadInfo(int id, String tag, String uri, long finished) {
        this.id = id;
        this.tag = tag;
        this.url = uri;
        this.finished = finished;
    }

    public ThreadInfo(int id, String tag, String uri, long start, long end, long finished, int status) {
        this.id = id;
        this.tag = tag;
        this.url = uri;
        this.start = start;
        this.end = end;
        this.finished = finished;
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public long getStart() {
        return start;
    }

    public void setStart(long start) {
        this.start = start;
    }

    public long getEnd() {
        return end;
    }

    public void setEnd(long end) {
        this.end = end;
    }

    public long getFinished() {
        return finished;
    }

    public void setFinished(long finished) {
        this.finished = finished;
    }

    @Override
    public String toString() {
        return "ThreadInfo{" +
                "id=" + id +
                ", tag='" + tag + '\'' +
                ", url='" + url + '\'' +
                ", start=" + start +
                ", end=" + end +
                ", finished=" + finished +
                '}';
    }
}
