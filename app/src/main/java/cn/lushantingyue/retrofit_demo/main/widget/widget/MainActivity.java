package cn.lushantingyue.retrofit_demo.main.widget.widget;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.ArrayList;

import cn.lushantingyue.retrofit_demo.R;
import cn.lushantingyue.retrofit_demo.api.ApiService;
import cn.lushantingyue.retrofit_demo.bean.Articles;
import cn.lushantingyue.retrofit_demo.main.widget.ListRecyclerAdapter;
import cn.lushantingyue.retrofit_demo.main.widget.presenter.MainPresenterImpl;
import cn.lushantingyue.retrofit_demo.main.widget.view.MainView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements MainView, SwipeRefreshLayout.OnRefreshListener {

    ArrayList<Articles> listData = new ArrayList<>();

//    @BindView(R.id.tv)
//    private TextView tv;

//    @BindView(R.id.btn)
//    private Button btn;

//    @OnClick(R.id.btn)
//    public void setText() {
//    }

    private MainActivity ctx;

    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private ListRecyclerAdapter adapter;
    private SwipeRefreshLayout mSwipeRefreshWidget;
    private MainPresenterImpl mArticlesPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ctx = MainActivity.this;

//        ButterKnife.bind(this);
        mSwipeRefreshWidget = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        mSwipeRefreshWidget.setColorSchemeColors(getResources().getColor(R.color.primary),getResources().getColor(R.color.primary_dark),
                getResources().getColor(R.color.primary_light),getResources().getColor(R.color.accent));
        mSwipeRefreshWidget.setOnRefreshListener(this);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setSmoothScrollbarEnabled(true);

        recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new ListRecyclerAdapter(ctx, listData);
        recyclerView.setAdapter(adapter);

//        loadData();
        mArticlesPresenter = new MainPresenterImpl(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mArticlesPresenter.loadArticles();
    }

    @Override
    public void addArticles(ArrayList<Articles> list) {
        listData.addAll(list);
        adapter.notifyDataSetChanged();
    }

    private void loadData() {
        final String api = "http://192.168.2.30:3000/"; // 连内网使用
        final String wifi = "http://192.168.155.1:3000/"; // 连本机wifi使用

        new Thread() {
            @Override
            public void run() {

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(wifi)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                ApiService service = retrofit.create(ApiService.class);
                final Call<ArrayList<Articles>> call = service.listData();
                call.enqueue(new Callback<ArrayList<Articles>>() {

                    @Override
                    public void onResponse(Call<ArrayList<Articles>> call, Response<ArrayList<Articles>> response) {
                        if (response.body() != null) {
                            ArrayList<Articles> resp = response.body();
                            if (resp != null) {
                                listData.addAll(resp);
                                adapter.notifyDataSetChanged();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ArrayList<Articles>> call, Throwable t) {
                        t.printStackTrace();
                    }
                });

            }
        }.start();
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
        mArticlesPresenter.loadArticles();
    }

    @Override
    public void toastTips() {
        Toast.makeText(this, "加载完成", Toast.LENGTH_SHORT).show();
    }
}
