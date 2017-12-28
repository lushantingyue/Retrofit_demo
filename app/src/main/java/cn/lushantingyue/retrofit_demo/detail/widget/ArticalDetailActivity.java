package cn.lushantingyue.retrofit_demo.detail.widget;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import cn.lushantingyue.retrofit_demo.R;
import cn.lushantingyue.retrofit_demo.api.ApiService;
import cn.lushantingyue.retrofit_demo.bean.Articles;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ArticalDetailActivity extends AppCompatActivity {

    private LayoutInflater inflater;
    private TextView title, author, uid, content, date, wordage;
    private ImageView avatar;
    private String href;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artical_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        setView();
        href = this.getIntent().getExtras().getString("href");
        title.setText(href);
//        loadData();
    }

    private void loadData() {
//        final String api = "http://192.168.2.30:3000/"; // 连内网使用
//        final String wifi = "http://192.168.155.1:3000/"; // 连本机wifi使用
//
//        new Thread(){
//            @Override
//            public void run() {
//
//                Retrofit retrofit = new Retrofit.Builder()
//                        .baseUrl(wifi)
//                        .addConverterFactory(GsonConverterFactory.create())
//                        .build();
//                ApiService service = retrofit.create(ApiService.class);
//                final Call<ArrayList<Articles>> call = service.listData();
//                call.enqueue(new Callback<ArrayList<Articles>>() {
//
//                    @Override
//                    public void onResponse(Call<ArrayList<Articles>> call, Response<ArrayList<Articles>> response) {
//                        if (response.body() != null) {
//                            ArrayList<Articles> resp = response.body();
////                                listData.addAll(resp);
//                        }
//                    }
//                    @Override
//                    public void onFailure(Call<ArrayList<Articles>> call, Throwable t) {
//                        t.printStackTrace();
//                    }
//                });
//
//            }
//        }.start();
    }

    private void setView() {
        title = findViewById(R.id.title);
        author = findViewById(R.id.author);
        uid = findViewById(R.id.uid);
        content = findViewById(R.id.content);
        date = findViewById(R.id.date);
        wordage = findViewById(R.id.wordage);
        avatar = findViewById(R.id.avatar);
    }

}
