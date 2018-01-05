package cn.lushantingyue.retrofit_demo.main.widget.presenter;

import java.util.ArrayList;

import cn.lushantingyue.retrofit_demo.R;
import cn.lushantingyue.retrofit_demo.bean.Articles;
import cn.lushantingyue.retrofit_demo.main.widget.model.MainModelImpl;
import cn.lushantingyue.retrofit_demo.main.widget.view.MainView;
import io.reactivex.disposables.Disposable;

/**
 * Created by Lushantingyue on 2017/12/28.
 * Responsibilities:
 * Description:
 * ProjectName:
 */
public class MainPresenterImpl implements MainPresenter, MainModelImpl.OnLoadArticlesListListener {

    private static final String TAG = "MainPresenterImpl";
    public final int STATUS_LOADING_SUCESS = 1;
    public final int STATUS_LOADING_FAILURE = -1;

    private MainModelImpl mArticleModel;
    private MainView mArticlesView;

    public MainPresenterImpl(MainView view) {
        this.mArticlesView = view;
        this.mArticleModel = new MainModelImpl();
    }

    @Override
    public void loadArticles(int curPage, boolean canloadMore) {
        if(canloadMore) {
            mArticlesView.showProgress();
            mArticleModel.loadArticles(this, curPage);
        }
    }

    @Override
    public void onSuccess(ArrayList<Articles> list, int page) {
        if (page == 1) {
            mArticlesView.clearArticles();
        }
        mArticlesView.addArticles(list);
        mArticlesView.hideProgress();
        mArticlesView.toastTips(STATUS_LOADING_SUCESS);
    }

    @Override
    public void onFailure(String msg, Exception e) {
        mArticlesView.hideProgress();
        mArticlesView.toastTips(STATUS_LOADING_FAILURE);
    }

    @Override
    public void saveDisposable(Disposable d) {
        mArticlesView.saveDisposable(d);
    }
}
