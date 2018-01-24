package cn.lushantingyue.retrofit_demo.detail.model;

import cn.lushantingyue.retrofit_demo.bean.ArticleDetail;
import cn.lushantingyue.retrofit_demo.api.RetrofitWrapper;
import io.reactivex.disposables.Disposable;

/**
 * Created by Administrator on 2017/12/29 11.
 * Responsibilities:
 * Description:
 * ProjectName:
 */

public class DetailModelImpl implements DetailModel {

    @Override
    public void loadArticlesDetail(String href, DetailModelImpl.OnLoadArticlesDetailListener listener) {

        String subHref = href.split("/p/")[1];
        RetrofitWrapper.getInstance().articleDetail(listener, subHref);
    }

    /**
     * 通过接口给Presenter反馈
     */
    public interface OnLoadArticlesDetailListener {
        void onSuccess(ArticleDetail list);
        void onFailure(String msg, Exception e);

        void saveDisposable(Disposable d);
    }

}
