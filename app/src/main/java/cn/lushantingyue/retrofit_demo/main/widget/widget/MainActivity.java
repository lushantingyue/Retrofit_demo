package cn.lushantingyue.retrofit_demo.main.widget.widget;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.orhanobut.logger.Logger;

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

//    private RecyclerView recyclerView;
//    private SwipeRefreshLayout mSwipeRefreshWidget;
    private LinearLayoutManager linearLayoutManager;
    private ListRecyclerAdapter adapter;
    private MainPresenterImpl mArticlesPresenter;
    private int curPage = 6;

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

        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setSmoothScrollbarEnabled(true);
        linearLayoutManager.setItemPrefetchEnabled(true);

        recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new ListRecyclerAdapter(ctx, listData, this);
        recyclerView.setAdapter(adapter);

        mArticlesPresenter = new MainPresenterImpl(this);
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
        adapter.notifyDataSetChanged();
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
        mArticlesPresenter.loadArticles(curPage);
    }

    @Override
    public void toastTips() {
        Toast.makeText(this, "加载完成", Toast.LENGTH_SHORT).show();
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

//    /**
//     * Perform an item click operation on the specified list adapter position.
//     *
//     * @param position Adapter position for performing the click
//     * @return true if the click action could be performed, false if not.
//     *         (e.g. if the popup was not showing, this method would return false.)
//     */
//    public boolean performItemClick(int position) {
//        ...
//    }

}
