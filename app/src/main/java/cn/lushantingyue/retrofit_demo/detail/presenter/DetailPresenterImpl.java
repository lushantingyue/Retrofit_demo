package cn.lushantingyue.retrofit_demo.detail.presenter;

import java.util.ArrayList;

import cn.lushantingyue.retrofit_demo.bean.Articles;
import cn.lushantingyue.retrofit_demo.detail.model.DetailModel;
import cn.lushantingyue.retrofit_demo.detail.model.DetailModelImpl;

/**
 * Created by Administrator on 2017/12/29 11.
 * Responsibilities:
 * Description:
 * ProjectName:
 */

public class DetailPresenterImpl implements DetailPresenter, DetailModelImpl.OnLoadArticlesDetailListener {

    @Override
    public void loadDetail() {
//        loadArticlesDetail
    }

    @Override
    public void onSuccess(ArrayList<Articles> list) {

    }

    @Override
    public void onFailure(String msg, Exception e) {

    }
}
