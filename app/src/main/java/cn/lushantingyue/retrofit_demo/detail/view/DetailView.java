package cn.lushantingyue.retrofit_demo.detail.view;

/**
 * Created by Administrator on 2017/12/29 11.
 * Responsibilities:
 * Description:
 * ProjectName:
 */

public interface DetailView {

    void showProgress();
    void hideProgress();
    void showTips();

    void loadData();
    void clearData();
}
