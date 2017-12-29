package cn.lushantingyue.retrofit_demo.detail.model;

import java.util.ArrayList;

import cn.lushantingyue.retrofit_demo.bean.Articles;

/**
 * Created by Administrator on 2017/12/29 11.
 * Responsibilities:
 * Description:
 * ProjectName:
 */

public class DetailModelImpl implements DetailModel {


    @Override
    public void loadArticlesDetail(String href, DetailModelImpl.OnLoadArticlesDetailListener listener) {

    }

    /**
     * 通过接口给Presenter反馈
     */
    public interface OnLoadArticlesDetailListener {
        void onSuccess(ArrayList<Articles> list);
        void onFailure(String msg, Exception e);
    }
}
