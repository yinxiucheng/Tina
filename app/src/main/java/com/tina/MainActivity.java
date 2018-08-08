package com.tina;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.tbruyelle.rxpermissions2.RxPermissions;

import butterknife.BindView;
import butterknife.ButterKnife;
import tina.com.common.download.DownloadManager;
import tina.com.common.download.entity.DownloadInfo;
import tina.com.common.download.entity.DownloadStatus;
import tina.com.common.download.observer.DataWatcher;
import tina.com.common.download.utils.Trace;
import tina.com.common.http.utils.ToastUtil;
import tina.com.common.http.utils.Utils;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    DownloadManager downloadManager;
    DataWatcher dataWatcher;
    DownloadInfo downloadInfo;

    @BindView(R.id.muti_download)
    Button mutiDownload;
    @BindView(R.id.download)
    Button download;
    @BindView(R.id.pause)
    Button pause;
    @BindView(R.id.cancel)
    Button cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        init();
        permission();
    }

    private void permission() {
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.requestEach(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(permission -> {
                    if (permission.granted) {
                        // 用户已经同意该权限
                        ToastUtil.show("用户已经同意该权限");
                    } else if (permission.shouldShowRequestPermissionRationale) {
                        // 用户拒绝了该权限，没有选中『不再询问』（Never ask again）,那么下次再次启动时，还会提示请求权限的对话框
                        ToastUtil.show("用户拒绝了该权限");
                    } else {
                        // 用户拒绝了该权限，并且选中『不再询问』，提醒用户手动打开权限
                        ToastUtil.show("权限被拒绝，请在设置里面开启相应权限，若无相应权限会影响使用");
                    }
                });
    }

    private void init() {

        downloadManager = DownloadManager.getInstance(Utils.getContext());
        dataWatcher = new DataWatcher() {
            @Override
            public void notifyObserver(DownloadInfo data) {
                downloadInfo = data;
                Trace.e(data.getName() + ":" + data.getFinish() + "/"
                        + data.getLength() + ":" + data.getStatus());
                if (data.status == DownloadStatus.CANCELED) {
                    downloadInfo = null;
                }
            }
        };

        downloadInfo = new DownloadInfo();
        downloadInfo.setName("test");
        downloadInfo.setUrl("http://s1.music.126.net/download/android/CloudMusic_2.8.1_official_4.apk");

        download.setOnClickListener(this);
        pause.setOnClickListener(this);
        cancel.setOnClickListener(this);
        mutiDownload.setOnClickListener(this);
    }


    @Override
    protected void onStart() {
        super.onStart();
        downloadManager.addObservable(this, dataWatcher);
    }

    @Override
    protected void onStop() {
        super.onStop();
        downloadManager.removeObservable(this, dataWatcher);
    }

    @Override
    public void onClick(View v) {
        if (null == downloadInfo) {
            downloadInfo = new DownloadInfo();
            downloadInfo.setName("test");
            downloadInfo.setUrl("http://s1.music.126.net/download/android/CloudMusic_2.8.1_official_4.apk");
        }
        switch (v.getId()) {
            case R.id.download: {
                downloadManager.download(downloadInfo);
                break;
            }
            case R.id.pause: {
                if (downloadInfo.status == DownloadStatus.PAUSED) {
                    downloadManager.resume(downloadInfo);
                    pause.setText("Puse");
                } else if (downloadInfo.status == DownloadStatus.DOWNLOADING) {
                    downloadManager.pause(downloadInfo);
                    pause.setText("Resume");
                }
                break;
            }
            case R.id.cancel: {
                downloadManager.cancel(downloadInfo);
                break;
            }
            case R.id.muti_download: {
                Intent intent = new Intent(this, AppListActivity.class);
                startActivity(intent);
                break;
            }
            default:
        }
    }
}
