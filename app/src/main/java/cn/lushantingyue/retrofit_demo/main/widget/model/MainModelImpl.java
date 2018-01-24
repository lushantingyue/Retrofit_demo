package cn.lushantingyue.retrofit_demo.main.widget.model;

import java.util.ArrayList;

import cn.lushantingyue.retrofit_demo.bean.Articles;
import cn.lushantingyue.retrofit_demo.api.RetrofitWrapper;
import io.reactivex.disposables.Disposable;

/**
 * Created by Lushantingyue on 2017/12/28.
 * Responsibilities:
 * Description:
 * ProjectName:
 */

public class MainModelImpl implements MainModel {

    @Override
    public void loadArticles(final OnLoadArticlesListListener listener, final int curPage) {

        RetrofitWrapper.getInstance().listDataByPage(listener, curPage);
    }

    public interface OnLoadArticlesListListener {
        void onSuccess(ArrayList<Articles> list, int page);
        void onFailure(String msg, Exception e);

        void saveDisposable(Disposable d);  // 保存Disposable 对象
    }

}
