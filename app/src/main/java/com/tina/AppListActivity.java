package com.tina;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

public class AppListActivity extends AppCompatActivity {

    public static final class TYPE {
        public static final int TYPE_LISTVIEW = 0;
        public static final int TYPE_RECYCLERVIEW = 1;
    }

    RecyclerViewFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_list);
        Intent intent = getIntent();
        int type = intent.getIntExtra("EXTRA_TYPE", TYPE.TYPE_LISTVIEW);

        if (savedInstanceState == null) {
            fragment = new RecyclerViewFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, fragment)
                    .commit();
        }

        getSupportActionBar().setTitle(type == TYPE.TYPE_LISTVIEW ? "ListView Demo" : "批量下载");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            if (item.getTitle().equals("pause all")) {
                item.setTitle(R.string.action_recover_all);
                if (null != fragment){
                    fragment.pauseAll();
                }
            } else {
                item.setTitle(R.string.action_pause_all);
//                mDownloadManager.recoverAll();
                fragment.recoverAll();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
