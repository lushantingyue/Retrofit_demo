package cn.lushantingyue.retrofit_demo.detail.widget;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.orhanobut.logger.Logger;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.lushantingyue.retrofit_demo.R;
import cn.lushantingyue.retrofit_demo.bean.ArticleDetail;
import cn.lushantingyue.retrofit_demo.detail.presenter.DetailPresenterImpl;
import cn.lushantingyue.retrofit_demo.detail.view.DetailView;

public class ArticalDetailActivity extends AppCompatActivity implements DetailView, SwipeRefreshLayout.OnRefreshListener {

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

    @BindView(R.id.swipe_refresh_layout_detail) SwipeRefreshLayout refreshWidget;

    private DetailPresenterImpl mPresenter;
    private ArticleDetail data = new ArticleDetail();
    private Activity act;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artical_detail);

        act = ArticalDetailActivity.this;
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        refreshWidget.setOnRefreshListener(this);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

//        setView();
        href = this.getIntent().getExtras().getString("href");

        title.setText(href!=null?href:"");

        mPresenter = new DetailPresenterImpl(this);
    }

//    private void setView() {
//        title = findViewById(R.id.title);
//        author = findViewById(R.id.author);
//        uid = findViewById(R.id.uid);
//        content = findViewById(R.id.content);
//        date = findViewById(R.id.date);
//        wordage = findViewById(R.id.wordage);
//        avatar = findViewById(R.id.avatar);
//    }

    @Override
    protected void onResume() {
        super.onResume();

        Logger.i("onResume ArticalDetail >>>");
        mPresenter.loadDetail(href);
    }

    @Override
    public void showProgress() {
        refreshWidget.setRefreshing(true);
    }

    @Override
    public void hideProgress() {
        refreshWidget.setRefreshing(false);
    }

    @Override
    public void showTips() {
        Toast.makeText(act, "文章详情--加载完成", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void loadData(ArticleDetail data) {

        this.data = data;
        Logger.i("loadData >>> ");
        title.setText(data.getTitle());
        author.setText(data.getAuthor());
        String path = "http:" + data.getAvatar();

        Glide.with(act)
                .load(path)
                .placeholder(R.mipmap.ic_launcher) //设置占位图
//                .error(R.mipmap.erro) //设置错误图片
                .crossFade() //设置淡入淡出效果，默认300ms，可以传参
                .into(avatar);
        date.setText(data.getDate());
        wordage.setText(data.getWordage());

//        content.setText(data.getText());
        String text = data.getText();
//        while (text.contains("\n")) {
//            text = text.replace("\n", "");
//        }
        content.setText(text);
    }

    @Override
    public void clearData() {
        this.data = null;
    }

    @Override
    public void onRefresh() {
        mPresenter.loadDetail(href);
    }
}
