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
import cn.lushantingyue.retrofit_demo.main.widget.widget.multitype.ArticleViewBinder;
import cn.lushantingyue.retrofit_demo.utils.LoadMoreDelegate;
import io.reactivex.disposables.Disposable;
import me.drakeet.multitype.MultiTypeAdapter;

public class MainActivity extends AppCompatActivity implements MainView,
        SwipeRefreshLayout.OnRefreshListener, MyItemClickListener, LoadMoreDelegate.LoadMoreSubject {

    ArrayList<Articles> listData = new ArrayList<>();
    private LoadMoreDelegate loadMoreDelegate;

    @BindView(R.id.toolbar) Toolbar toolbar;

    @BindView(R.id.fab) FloatingActionButton fab;
    @BindView(R.id.recycler_view) RecyclerView recyclerView;

    @BindView(R.id.swipe_refresh_layout) SwipeRefreshLayout mSwipeRefreshWidget;

    @OnClick(R.id.fab)
     public void sayHi(FloatingActionButton fab) {
        Snackbar.make(fab, "置顶", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
        recyclerView.smoothScrollToPosition(0);
    }

    private MainActivity ctx;

    //    private ListRecyclerAdapter adapter;
    private MainPresenterImpl mArticlesPresenter;
    private int curPage = 1;

    private MultiTypeAdapter mAdapter;
//    private HeaderAndFooterWrapper mHeaderAndFooterWrapper;
//    private LoadMoreWrapper mLoadMoreWrapper;
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
        // loadmore
        loadMoreDelegate = new LoadMoreDelegate(this);

        mAdapter = new MultiTypeAdapter();
        // 传入 OnItemClickListener
        mAdapter.register(Articles.class, new ArticleViewBinder(this));
        recyclerView.setAdapter(mAdapter);
        // loadmore
        loadMoreDelegate.attach(recyclerView);

        mAdapter.setItems(listData);
        mAdapter.notifyDataSetChanged();
        // 实现加载更多FooterV

//        mLoadMoreWrapper.setLoadMoreView(R.layout.default_loading);
        mArticlesPresenter = new MainPresenterImpl(this);
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
        }
        mAdapter.notifyDataSetChanged();
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
//                Logger.i("item count= "+ mLoadMoreWrapper.getItemCount());
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
        Logger.i("add dispose");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        for (int i = 0; i < dispose.size(); i++) {
            dispose.remove(i).dispose();
            Logger.i("remove dispose");
        }
    }

    @Override
    public boolean isLoading() {
        return false;
    }

    @Override
    public void onLoadMore() {
        curPage++;
        mArticlesPresenter.loadArticles(curPage, canloadMore);
    }
}
