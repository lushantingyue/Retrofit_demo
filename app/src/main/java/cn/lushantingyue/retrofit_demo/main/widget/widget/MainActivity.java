package cn.lushantingyue.retrofit_demo.main.widget.widget;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.orhanobut.logger.Logger;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zhy.adapter.recyclerview.wrapper.EmptyWrapper;
import com.zhy.adapter.recyclerview.wrapper.HeaderAndFooterWrapper;
import com.zhy.adapter.recyclerview.wrapper.LoadMoreWrapper;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.lushantingyue.retrofit_demo.R;
import cn.lushantingyue.retrofit_demo.bean.Articles;
import cn.lushantingyue.retrofit_demo.detail.widget.ArticalDetailActivity;
import cn.lushantingyue.retrofit_demo.listener.MyItemClickListener;
import cn.lushantingyue.retrofit_demo.main.widget.ListRecyclerAdapter;
import cn.lushantingyue.retrofit_demo.main.widget.presenter.MainPresenterImpl;
import cn.lushantingyue.retrofit_demo.main.widget.view.MainView;

public class MainActivity extends AppCompatActivity implements MainView, SwipeRefreshLayout.OnRefreshListener, MyItemClickListener {

    ArrayList<Articles> listData = new ArrayList<>();

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshWidget;

    private MainActivity ctx;

    //    private ListRecyclerAdapter adapter;
    private MainPresenterImpl mArticlesPresenter;
    private int curPage = 1;

    private CommonAdapter<Articles> mAdapter;
    private HeaderAndFooterWrapper mHeaderAndFooterWrapper;
    private LoadMoreWrapper mLoadMoreWrapper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ctx = MainActivity.this;

        ButterKnife.bind(this);
//        mSwipeRefreshWidget = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        mSwipeRefreshWidget.setColorSchemeColors(getResources().getColor(R.color.primary),getResources().getColor(R.color.primary_dark),
                getResources().getColor(R.color.primary_light),getResources().getColor(R.color.accent));
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
                mArticlesPresenter.loadArticles(curPage);
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
                Logger.i("listData is: "+ bean.getHref());
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
        mArticlesPresenter.loadArticles(curPage);
    }

    @Override
    public void clearArticles() {
        listData.clear();
    }

    @Override
    public void addArticles(ArrayList<Articles> list) {
        listData.addAll(list);
//        adapter.notifyDataSetChanged();
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
        mArticlesPresenter.loadArticles(curPage);
    }

    @Override
    public void toastTips() {
        Toast.makeText(this, "加载完成", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void hideLoadMoreFooter() {
//        mHeaderAndFooterWrapper.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(View child, int position){
        // 跳转文章详情页
        Intent intent = new Intent(ctx, ArticalDetailActivity.class);
        Articles bean = listData.get(position);
        Logger.i("listData is: "+ bean.getHref());
        intent.putExtra("href", bean.getHref());
        startActivity(intent);
    }

}
