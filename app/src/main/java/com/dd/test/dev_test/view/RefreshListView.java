package com.dd.test.dev_test.view;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;


import com.dd.test.dev_test.R;

import java.util.ArrayList;
import java.util.List;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Created by dd on 16/1/14.
 * 可刷新listview
 */
public class RefreshListView extends RelativeLayout {

    private Context context;
    private PtrClassicFrameLayout ptrClassicFrameLayout;
    private ListView listView;
    private OnRefreshListener onRefreshListener;
    private boolean isLoading = false;
    private List<String> a;
    float downY;

    public RefreshListView(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public RefreshListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    public RefreshListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }

    public void setOnRefreshListener(OnRefreshListener onRefreshListener) {
        this.onRefreshListener = onRefreshListener;
    }

    private void init() {
        initView();
        initData();
        initEvent();
    }

    private void initView() {

//        ptrClassicFrameLayout = new PtrClassicFrameLayout(context);
//        ptrClassicFrameLayout.setResistance(1.7f);
//        ptrClassicFrameLayout.setRatioOfHeaderHeightToRefresh(1.2f);
//        ptrClassicFrameLayout.setDurationToClose(200);
//        ptrClassicFrameLayout.setDurationToCloseHeader(1000);
//        ptrClassicFrameLayout.setPullToRefresh(false);
//        ptrClassicFrameLayout.setKeepHeaderWhenRefresh(true);
//        ptrClassicFrameLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
//        ptrClassicFrameLayout.setBackgroundColor(Color.BLUE);
//        addView(ptrClassicFrameLayout);
//        listView = new ListView(context);
//        listView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//        ptrClassicFrameLayout.addView(listView);

        View view = LayoutInflater.from(context).inflate(R.layout.pull_view, null);
        ptrClassicFrameLayout = (PtrClassicFrameLayout) view.findViewById(R.id.ptr_frame);
        listView = (ListView) view.findViewById(R.id.lv);
        ptrClassicFrameLayout.setLastUpdateTimeRelateObject(context);
        addView(view);
    }

    private void initData() {
        a = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            a.add("hehe" + i);
        }
        BaseAdapter baseAdapter = new BaseAdapter() {
            @Override
            public int getCount() {
                return a.size();
            }

            @Override
            public Object getItem(int i) {
                return a.get(i);
            }

            @Override
            public long getItemId(int i) {
                return i;
            }

            @Override
            public View getView(int i, View view, ViewGroup viewGroup) {
                TextView textView = new TextView(context);
                textView.setLayoutParams(new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.MATCH_PARENT));
                textView.setGravity(Gravity.CENTER);
                textView.setPadding(20, 20, 20, 20);
                textView.setText(a.get(i));

                return textView;
            }
        };
        listView.setAdapter(baseAdapter);
    }

    private void initEvent() {
        ptrClassicFrameLayout.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                if (onRefreshListener != null) {
                    onRefreshListener.onPullDownToRefresh();
                }
            }
        });

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {

                if ((firstVisibleItem + visibleItemCount) == totalItemCount) {
                    View lastVisibleItemView = listView.getChildAt(listView.getChildCount() - 1);
                    if (lastVisibleItemView != null && lastVisibleItemView.getBottom() == listView.getHeight()) {
                        if (onRefreshListener != null && isLoading) {
                            onRefreshListener.onPullUpToRefresh();
                        }
                        isLoading = false;
                    }
                }
            }
        });
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        switch (ev.getAction()) {

            case MotionEvent.ACTION_DOWN:
                downY = ev.getY();
                break;

            case MotionEvent.ACTION_MOVE:
                if (ev.getY() - downY < 0) {
                    isLoading = true;
                }
                break;

            case MotionEvent.ACTION_UP:

                break;
        }
        return super.dispatchTouchEvent(ev);
    }


    public ListView getListView() {
        return listView;
    }

    public void freshComplete() {
        ptrClassicFrameLayout.refreshComplete();
    }

    public void autoRefresh() {
        ptrClassicFrameLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                ptrClassicFrameLayout.autoRefresh();
            }
        }, 500);
    }

    public interface OnRefreshListener {

        void onPullDownToRefresh();

        void onPullUpToRefresh();
    }
}
