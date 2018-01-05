package cn.lushantingyue.retrofit_demo.main.widget.widget;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.orhanobut.logger.Logger;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zhy.adapter.recyclerview.wrapper.HeaderAndFooterWrapper;
import com.zhy.adapter.recyclerview.wrapper.LoadMoreWrapper;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.lushantingyue.retrofit_demo.R;
import cn.lushantingyue.retrofit_demo.bean.Articles;
import cn.lushantingyue.retrofit_demo.detail.widget.ArticalDetailActivity;
import cn.lushantingyue.retrofit_demo.listener.MyItemClickListener;
import cn.lushantingyue.retrofit_demo.main.widget.presenter.MainPresenterImpl;
import cn.lushantingyue.retrofit_demo.main.widget.view.MainView;
import io.reactivex.disposables.Disposable;

public class MainActivity extends AppCompatActivity implements MainView, SwipeRefreshLayout.OnRefreshListener, MyItemClickListener {

    ArrayList<Articles> listData = new ArrayList<>();

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.fab) FloatingActionButton fab;

    @BindView(R.id.recycler_view) RecyclerView recyclerView;
    @BindView(R.id.swipe_refresh_layout) SwipeRefreshLayout mSwipeRefreshWidget;

    @OnClick(R.id.fab)
     public void sayHi(FloatingActionButton fab) {
        Snackbar.make(fab, "置顶", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    private MainActivity ctx;

    //    private ListRecyclerAdapter adapter;
    private MainPresenterImpl mArticlesPresenter;
    private int curPage = 1;

    private CommonAdapter<Articles> mAdapter;
    private HeaderAndFooterWrapper mHeaderAndFooterWrapper;
    private LoadMoreWrapper mLoadMoreWrapper;
    private ArrayList<Disposable> dispose = new ArrayList<>();
    private boolean canloadMore = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ctx = MainActivity.this;

        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
//        mSwipeRefreshWidget = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        mSwipeRefreshWidget.setColorSchemeColors(getResources().getColor(R.color.primary), getResources().getColor(R.color.primary_dark),
                getResources().getColor(R.color.primary_light), getResources().getColor(R.color.accent));
        mSwipeRefreshWidget.setOnRefreshListener(this);
//        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setSmoothScrollbarEnabled(true);
        linearLayoutManager.setItemPrefetchEnabled(true);

        recyclerView.setLayoutManager(linearLayoutManager);
        mAdapter = new CommonAdapter<Articles>(this, R.layout.item, listData) {
            @Override
            protected void convert(ViewHolder holder, Articles article, int position) {
                holder.setText(R.id.tv_title, article.get_abstract());
            }
        };
        initHeaderAndFooter();

        mLoadMoreWrapper = new LoadMoreWrapper(mHeaderAndFooterWrapper);
        mLoadMoreWrapper.setLoadMoreView(R.layout.default_loading);
        mLoadMoreWrapper.setOnLoadMoreListener(new LoadMoreWrapper.OnLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                curPage++;
                mArticlesPresenter.loadArticles(curPage, canloadMore);
            }
        });
//        adapter = new ListRecyclerAdapter(ctx, listData, this);
//        recyclerView.setAdapter(adapter);
        recyclerView.setAdapter(mLoadMoreWrapper);
        mAdapter.setOnItemClickListener(new CommonAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                // 跳转文章详情页
                Intent intent = new Intent(ctx, ArticalDetailActivity.class);
                Articles bean = listData.get(position);
                Logger.i("listData is: " + bean.getHref());
                intent.putExtra("href", bean.getHref());
                startActivity(intent);
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });

        mArticlesPresenter = new MainPresenterImpl(this);
    }

    private void initHeaderAndFooter() {
        mHeaderAndFooterWrapper = new HeaderAndFooterWrapper(mAdapter);
        TextView t1 = new TextView(this);
        t1.setText(" -- 顶部视图 -- ");
        TextView b1 = new TextView(this);
        b1.setText(" -- 底部视图 -- ");
        mHeaderAndFooterWrapper.addHeaderView(t1);
        mHeaderAndFooterWrapper.addFootView(b1);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mArticlesPresenter.loadArticles(curPage, canloadMore);
    }

    @Override
    public void clearArticles() {
        listData.clear();
    }

    @Override
    public void addArticles(ArrayList<Articles> list) {
        if (list.size() > 0) {
            listData.addAll(list);
        } else {
            this.canloadMore = false;
            mLoadMoreWrapper.setLoadMoreView(R.layout.bottom);
        }
        mLoadMoreWrapper.notifyDataSetChanged();
    }

    @Override
    public void showProgress() {
        mSwipeRefreshWidget.setRefreshing(true);
    }

    @Override
    public void hideProgress() {
        mSwipeRefreshWidget.setRefreshing(false);
    }

    @Override
    public void onRefresh() {
        curPage = 1;
        canloadMore = true;
        mArticlesPresenter.loadArticles(curPage, canloadMore);
    }

    //    加载状态判定
    private final int STATUS_LOADING_FAILURE = -1;
    private final int STATUS_LOADING_SUCCESS = 1;

    @Override
    public void toastTips(int status) {
        switch (status) {
            case STATUS_LOADING_SUCCESS:
                Toast.makeText(this, "加载完成", Toast.LENGTH_SHORT).show();
                break;
            case STATUS_LOADING_FAILURE:
                Toast.makeText(this, "加载失败", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }

    @Override
    public void onItemClick(View child, int position) {
        // 跳转文章详情页
        Intent intent = new Intent(ctx, ArticalDetailActivity.class);
        Articles bean = listData.get(position);
        Logger.i("listData is: " + bean.getHref());
        intent.putExtra("href", bean.getHref());
        startActivity(intent);
    }

    @Override
    public void saveDisposable(Disposable d) {
        dispose.add(d);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        for (int i = 0; i < dispose.size(); i++) {
            dispose.remove(i).dispose();
        }
    }
}
