package cn.lushantingyue.retrofit_demo.detail.view;

import cn.lushantingyue.retrofit_demo.bean.ArticleDetail;
import io.reactivex.disposables.Disposable;

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

    void loadData(ArticleDetail data);
    void clearData();

    void saveDisposable(Disposable d);
}
