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

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.lushantingyue.retrofit_demo.R;
import cn.lushantingyue.retrofit_demo.api.ApiService;
import cn.lushantingyue.retrofit_demo.bean.Articles;
import cn.lushantingyue.retrofit_demo.detail.view.DetailView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ArticalDetailActivity extends AppCompatActivity implements DetailView {

    private LayoutInflater inflater;
//    private TextView title, author, uid, content, date, wordage;
//    private ImageView avatar;
    private String href;

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.fab) FloatingActionButton fab;

    @BindView(R.id.title) TextView title;
    @BindView(R.id.author) TextView author;
    @BindView(R.id.uid) TextView uid;
    @BindView(R.id.content) TextView content;
    @BindView(R.id.date) TextView date;
    @BindView(R.id.wordage) TextView wordage;
    @BindView(R.id.avatar) ImageView avatar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artical_detail);

        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

//        setView();
        href = this.getIntent().getExtras().getString("href");
        title.setText(href);
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

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showTips() {

    }

    @Override
    public void loadData() {

    }

    @Override
    public void clearData() {

    }
}
