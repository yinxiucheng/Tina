package com.tina;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tina.data.DataSource;
import com.tina.listener.OnDeleteAppListener;
import com.tina.listener.OnItemClickListener;

import java.io.File;
import java.text.DecimalFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import tina.com.common.download.DownloadManager;
import tina.com.common.download.entity.DownloadInfo;
import tina.com.common.download.entity.DownloadStatus;
import tina.com.common.download.observer.DataWatcher;
import tina.com.common.download.utils.DownloadUtils;
import tina.com.common.download.utils.Trace;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecyclerViewFragment extends Fragment implements OnItemClickListener<DownloadInfo>, OnDeleteAppListener<DownloadInfo> {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private List<DownloadInfo> mAppInfos;
    private RecyclerViewAdapter mAdapter;
    private Unbinder unbinder;

    DataWatcher dataWatcher = new DataWatcher() {
        @Override
        public void notifyObserver(DownloadInfo data) {
            int index = mAppInfos.indexOf(data);
            if (index != -1){
                mAppInfos.remove(data);
                mAppInfos.add(index, data);
                mAdapter.notifyItemChanged(index, "payload");
            }
            Trace.e(data.toString());
        }
    };

    public RecyclerViewFragment() {
        // Required empty public constructor
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initRecycler();
        for (DownloadInfo info : mAppInfos) {
//            DownloadInfo downloadInfo = DownloadManager.getInstance(getContext()).getDownloadInfo(getActivity(), info.getUrl(), info.getPackageName(), info.getVersionCode());
//            if (downloadInfo != null) {
//                info.setProgress(downloadInfo.getProgress());
//                info.setDownloadPerSize(getDownloadPerSize(downloadInfo.getFinished(), downloadInfo.getLength()));
//                info.setStatus(AppInfo.STATUS_PAUSED);
//            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recycler_view, container, false);
        unbinder = ButterKnife.bind(this, view);
        DownloadManager.getInstance(getContext()).addObservable(getContext(), dataWatcher);
        return view;
    }

    public void initRecycler(){
        mAppInfos = DataSource.getInstance().getData();
        mAdapter = new RecyclerViewAdapter(mAppInfos);
        mAdapter.setOnItemClickListener(this);
        mAdapter.setOnDeleteAppListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(mAdapter);
        mAdapter.setData(mAppInfos);
    }

    @Override
    public void onPause() {
        super.onPause();
//        DownloadManager.getInstance(getContext()).pauseAll();
    }

    private static final DecimalFormat DF = new DecimalFormat("0.00");

    /**
     * Dir: /Download
     */
    private final File mDownloadDir = new File(Environment.getExternalStorageDirectory(), "Download");

    @Override
    public void onItemClick(View v, int position, DownloadInfo appInfo) {
        Trace.e("appInfo's Status:" + appInfo.getStatus());
        if (appInfo.getStatus() == DownloadStatus.DOWNLOADING) {
            pause(appInfo);
        } else if (appInfo.getStatus() == DownloadStatus.IDLE || appInfo.getStatus() == DownloadStatus.PAUSED){
            download(appInfo);
        }else if (appInfo.getStatus() == DownloadStatus.COMPLETED) {
            install(appInfo);
        } else if (appInfo.getStatus() == DownloadStatus.INSTALLED) {
            unInstall(appInfo);
        } else {
            Trace.e("error status");
        }
    }

    private void download(DownloadInfo appInfo) {
        DownloadManager.getInstance(getContext()).download(appInfo);
    }

    private void pause(DownloadInfo appInfo) {
        DownloadManager.getInstance(getContext()).pause(appInfo);
    }

    private void install(DownloadInfo appInfo) {
        DownloadUtils.installApp(getActivity(), new File(mDownloadDir, appInfo.getName() + ".apk"));
    }

    private void deleteAppInfo(Context ctx, final DownloadInfo appInfo) {
        AlertDialog dialog = new AlertDialog.Builder(ctx).setTitle("确认删除" + appInfo.getName() + "?")
                .setCancelable(false)
                .setMessage("确认删除" + appInfo.getName() + "?")
                .setPositiveButton("删除", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        Utils.deleteAppInfo(new File(mDownloadDir, appInfo.getName() + ".apk"));
                    }
                })
                .setNegativeButton("取消", null).create();
        dialog.show();
    }

    private void unInstall(DownloadInfo appInfo) {
//        Utils.unInstallApp(getActivity(), appInfo.getPackageName());
    }

    @Override
    public void onDelete(DownloadInfo appInfo) {
        deleteAppInfo(getActivity(), appInfo);
    }

    private RecyclerViewAdapter.AppViewHolder getViewHolder(int position) {
        return (RecyclerViewAdapter.AppViewHolder) recyclerView.findViewHolderForLayoutPosition(position);
    }

    private boolean isCurrentListViewItemVisible(int position) {
        LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        int first = layoutManager.findFirstVisibleItemPosition();
        int last = layoutManager.findLastVisibleItemPosition();
        return first <= position && position <= last;
    }

    private String getDownloadPerSize(long finished, long total) {
        return DF.format((float) finished / (1024 * 1024)) + "M/" + DF.format((float) total / (1024 * 1024)) + "M";
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        DownloadManager.getInstance(getContext()).removeObservable(getContext(), dataWatcher);
        unbinder.unbind();
    }

    public void pauseAll(){
        DownloadManager.getInstance(getContext()).pauseAll();
    }

    public void recoverAll(){
        DownloadManager.getInstance(getContext()).recoverAll();
    }


}
