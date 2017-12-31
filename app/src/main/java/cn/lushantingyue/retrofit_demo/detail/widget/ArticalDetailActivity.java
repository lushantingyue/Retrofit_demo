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
    @BindView(R.id.webView) WebView webView;
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
        // 设置长按监听, 屏蔽复制内容功能
        webView.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {
                return true;
            }
        });

//        setView();
        href = this.getIntent().getExtras().getString("href");
        title.setText(href);

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
        webView.onResume();

        Logger.i("onResume ArticalDetail >>>");
        mPresenter.loadDetail(href);
    }

    @Override
    protected void onPause() {
        super.onPause();
        webView.onPause();
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
        setWebViewProperties(data.getText());
    }

    private void setWebViewProperties(String text) {

        WebSettings settings = webView.getSettings();
        settings.setCacheMode(WebSettings.LOAD_DEFAULT); // 不缓存
        settings.setJavaScriptEnabled(true); // 允许JavaScript执行
        settings.setDefaultTextEncodingName("UTF-8"); // 设置默认编码
        settings.setLoadWithOverviewMode(true); // 页面适应手机屏幕的分辨率
        settings.setUseWideViewPort(true); // 尺度的网页在HTML定义
        settings.setBuiltInZoomControls(true); // 显示放大缩小 controler
        settings.setSupportZoom(true); // 可以缩放
        settings.setJavaScriptCanOpenWindowsAutomatically(true); // 直接JS打开新的窗口，相当于外链接
        settings.setAllowFileAccess(false); //提高安全性, 禁止访问本地文件
        // ws.setPluginsEnabled(true);
        // ws.setPluginState(PluginState.ON);
        webView.setWebChromeClient(new WebViewChromeClient());
        webView.setWebViewClient(new MyWebViewClient());
        // 移除远程代码执行漏洞
        webView.removeJavascriptInterface("searchBoxJavaBridge_");
        webView.removeJavascriptInterface("accessibility");
        webView.removeJavascriptInterface("accessibilityTraversal");

//        webView.loadUrl(text);
        webView.loadData(text, "text/html; charset=UTF-8", null);
    }

    private class WebViewChromeClient extends WebChromeClient {
        /**
         * 加载进度条
         */
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
//            pb.setProgress(newProgress);
//            if (newProgress == 100) {
//                pb.setVisibility(View.GONE);
//            }
            super.onProgressChanged(view, newProgress);
        }
    }

    /**
     * 控制网页都在webview里显示
     */
    public class MyWebViewClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
//            pb.setVisibility(View.VISIBLE);
            view.loadUrl(url);
            view.setWebChromeClient(new WebViewChromeClient());
            return true;
        }

        @Override
        public void onReceivedError(WebView view, int errorCode,
                                    String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
//            refresh_btn.setVisibility(View.VISIBLE);
            Logger.i( "loading url Error, no Data >>>>>> ");
            // mainView.invalidate();
        }
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
