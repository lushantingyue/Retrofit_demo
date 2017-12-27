package cn.lushantingyue.retrofit_demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import cn.lushantingyue.retrofit_demo.api.ApiService;
import cn.lushantingyue.retrofit_demo.bean.Articles;
import cn.lushantingyue.retrofit_demo.view.adapter.ListRecyclerAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ctx = MainActivity.this;

//        ButterKnife.bind(this);
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setSmoothScrollbarEnabled(true);

        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new ListRecyclerAdapter(ctx, listData);
        recyclerView.setAdapter(adapter);

        loadData();
    }

    private void loadData() {
        final String api = "http://192.168.2.30:3000/"; // 连内网使用
        final String wifi = "http://192.168.155.1:3000/"; // 连本机wifi使用

        new Thread(){
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
                            String list = response.body().get(0).get_abstract();
                            ArrayList<Articles> resp = response.body();
                            listData.addAll(resp);
                            adapter.notifyDataSetChanged();
                        }
//                        if (listData != null && listData.size() > 0) {
//                            Toast.makeText(ctx, listData.get(0).get_abstract().toString(), Toast.LENGTH_LONG).show();
//                        }
                    }
                    @Override
                    public void onFailure(Call<ArrayList<Articles>> call, Throwable t) {
                        t.printStackTrace();
                    }
                });

            }
        }.start();
    }
}
