package cn.lushantingyue.retrofit_demo.main.widget.view;

import java.util.ArrayList;

import cn.lushantingyue.retrofit_demo.bean.Articles;
import io.reactivex.disposables.Disposable;

/**
 * Created by Lushantingyue on 2017/12/28.
 * Responsibilities:
 * Description:
 * ProjectName:
 */

public interface MainView {

    void showProgress();
    void hideProgress();

    void addArticles(ArrayList<Articles> list);

    void toastTips(int status);

    void clearArticles();

    void saveDisposable(Disposable d);
}
