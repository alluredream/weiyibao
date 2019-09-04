package com.android.test.makestart2;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;


/**
 * Created by AllureDream on 2018-11-26.
 */

public class refreshActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{
    private static final int REFRESH_COMPLETE = 0x01;
    private SwipeRefreshLayout refreshLayout;
    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case REFRESH_COMPLETE:
                    refreshLayout.setRefreshing(false);
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notices_page);
        //强制横屏
        setRequestedOrientation(ActivityInfo
                .SCREEN_ORIENTATION_LANDSCAPE);
    }
    /**
     * 当刷新的时候进行回调
     */
    @Override
    public void onRefresh() {
        //在这里执行操作的更新等操作

        //刷新成功
        mHandler.sendEmptyMessageDelayed(REFRESH_COMPLETE, 2000);
        //refreshLayout.setRefreshing(false);

    }


}
