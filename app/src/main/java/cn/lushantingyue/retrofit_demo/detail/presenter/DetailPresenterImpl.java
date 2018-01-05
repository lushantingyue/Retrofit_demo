package cn.lushantingyue.retrofit_demo.detail.presenter;

import android.view.View;

import java.util.ArrayList;

import cn.lushantingyue.retrofit_demo.bean.ArticleDetail;
import cn.lushantingyue.retrofit_demo.bean.Articles;
import cn.lushantingyue.retrofit_demo.detail.model.DetailModel;
import cn.lushantingyue.retrofit_demo.detail.model.DetailModelImpl;
import cn.lushantingyue.retrofit_demo.detail.view.DetailView;
import cn.lushantingyue.retrofit_demo.detail.widget.ArticalDetailActivity;
import io.reactivex.disposables.Disposable;

/**
 * Created by Administrator on 2017/12/29 11.
 * Responsibilities:
 * Description:
 * ProjectName:
 */

public class DetailPresenterImpl implements DetailPresenter, DetailModelImpl.OnLoadArticlesDetailListener {

    private DetailModelImpl mModel;
    DetailView mView;

    public DetailPresenterImpl(DetailView view) {
        this.mView = view;
        this.mModel = new DetailModelImpl();
    }

    @Override
    public void loadDetail(String href) {
        mView.showProgress();
        mModel.loadArticlesDetail(href, this);
    }

    @Override
    public void onSuccess(ArticleDetail data) {
        mView.hideProgress();
        mView.clearData();
        mView.loadData(data);
        mView.showTips();
    }

    @Override
    public void onFailure(String msg, Exception e) {

    }

    @Override
    public void saveDisposable(Disposable d) {
        mView.saveDisposable(d);
    }
}
