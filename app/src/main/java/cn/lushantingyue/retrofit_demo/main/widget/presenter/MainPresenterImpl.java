package cn.lushantingyue.retrofit_demo.main.widget.presenter;

import android.content.Context;
import android.widget.Toast;

import java.util.ArrayList;

import cn.lushantingyue.retrofit_demo.bean.Articles;
import cn.lushantingyue.retrofit_demo.main.widget.model.MainModelImpl;
import cn.lushantingyue.retrofit_demo.main.widget.view.MainView;

/**
 * Created by Lushantingyue on 2017/12/28.
 * Responsibilities:
 * Description:
 * ProjectName:
 */
public class MainPresenterImpl implements MainPresenter, MainModelImpl.OnLoadArticlesListListener {

    private static final String TAG = "MainPresenterImpl";

    private MainModelImpl mArticleModel;
    private Context context;
    private MainView mArticlesView;

    public MainPresenterImpl(Context context, MainView view) {
        this.context = context;
        this.mArticlesView = view;
        this.mArticleModel = new MainModelImpl();
    }

    @Override
    public void loadArticles() {
        mArticlesView.showProgress();
        mArticleModel.loadArticles(this);
    }

    @Override
    public void onSuccess(ArrayList<Articles> list) {
        mArticlesView.addArticles(list);
        mArticlesView.hideProgress();
        Toast.makeText(context, "加载完成", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFailure(String msg, Exception e) {

    }
}
