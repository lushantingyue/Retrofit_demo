package cn.lushantingyue.retrofit_demo.detail.model;

import cn.lushantingyue.retrofit_demo.api.ApiService;
import cn.lushantingyue.retrofit_demo.bean.ArticleDetail;
import cn.lushantingyue.retrofit_demo.bean.Articles;
import cn.lushantingyue.retrofit_demo.utils.Constant;
import cn.lushantingyue.retrofit_demo.utils.RetrofitWrapper;
import io.reactivex.disposables.Disposable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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

//                Call<ArticleDetail> call = service.articleDetail(subHref);
//            call.enqueue(new Callback<ArticleDetail>() {
//
//                @Override
//                public void onResponse(Call<ArticleDetail> call, Response<ArticleDetail> response) {
//                    if (response.body() != null) {
//                        ArticleDetail resp = response.body();
//                        listener.onSuccess(resp);
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<ArticleDetail> call, Throwable t) {
//                    t.printStackTrace();
//                    listener.onFailure("加载数据失败", new Exception("error network."));
//                }
//            });

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
