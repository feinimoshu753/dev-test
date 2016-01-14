package com.dd.test.dev_test;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.dd.test.dev_test.view.RefreshListView;

import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import in.srain.cube.views.ptr.header.MaterialHeader;

/**
 * Created by dd on 16/1/14.
 */
public class PullToRefreshActivity extends Activity {

    private long mStartLoadingTime = -1;
    private boolean mImageHasLoaded = true;
    private PtrFrameLayout mPtrFrameLayout;
    private RefreshListView refreshListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pull_to_refresh);

        refreshListView = (RefreshListView) findViewById(R.id.refresh_lv);

        refreshListView.setOnRefreshListener(new RefreshListView.OnRefreshListener() {
            @Override
            public void onPullDownToRefresh() {
                Toast.makeText(PullToRefreshActivity.this, "下拉", Toast.LENGTH_SHORT).show();
                refreshListView.freshComplete();
            }

            @Override
            public void onPullUpToRefresh() {
                Toast.makeText(PullToRefreshActivity.this, "上拉", Toast.LENGTH_SHORT).show();
            }
        });
//        refreshListView.autoRefresh();

//        mPtrFrameLayout = (PtrFrameLayout) findViewById(R.id.material_style_ptr_frame);
//
//        // header
//        final MaterialHeader header = new MaterialHeader(this);
//        int[] colors = getResources().getIntArray(R.array.google_colors);
//        header.setColorSchemeColors(colors);
//        header.setLayoutParams(new PtrFrameLayout.LayoutParams(-1, -2));
//        header.setPadding(0, 15, 0, 15);
//        header.setPtrFrameLayout(mPtrFrameLayout);
//
//        mPtrFrameLayout.setLoadingMinTime(1000);
//        mPtrFrameLayout.setDurationToCloseHeader(1500);
//        mPtrFrameLayout.setHeaderView(header);
//        mPtrFrameLayout.addPtrUIHandler(header);
//        mPtrFrameLayout.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                mPtrFrameLayout.autoRefresh(false);
//            }
//        }, 100);
//
//        mPtrFrameLayout.setPtrHandler(new PtrHandler() {
//            @Override
//            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
//                return true;
//            }
//
//            @Override
//            public void onRefreshBegin(final PtrFrameLayout frame) {
//                if (mImageHasLoaded) {
//                    long delay = (long) (1000 + Math.random() * 2000);
//                    delay = Math.max(0, delay);
//                    delay = 0;
//                    frame.postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            frame.refreshComplete();
//                        }
//                    }, delay);
//                } else {
//                    mStartLoadingTime = System.currentTimeMillis();
//                }
//            }
//        });

    }
}
