package cn.lushantingyue.retrofit_demo.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import cn.lushantingyue.retrofit_demo.BuildConfig;
import cn.lushantingyue.retrofit_demo.bean.ArticleDetail;
import cn.lushantingyue.retrofit_demo.bean.Articles;
import cn.lushantingyue.retrofit_demo.detail.model.DetailModelImpl;
import cn.lushantingyue.retrofit_demo.main.widget.model.MainModelImpl;
import cn.lushantingyue.retrofit_demo.utils.Constant;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Lushantingyue on 2017/12/28 17.
 * Responsibilities: Request network api data
 * Description: Wrapper of Retrofit
 * ProjectName:
 */

public class RemoteData {

    private final ApiService service;

    public RemoteData() {
        service = RetrofitWrapper.getInstance();
    }

    public void listDataByPage(final MainModelImpl.OnLoadArticlesListListener listener, final int curPage) {

        HashMap<String, Integer> params = new HashMap<>();
        params.put("page", curPage);

        Observable<ArrayList<Articles>> observable = service.listDataByPage(params);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ArrayList<Articles>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        listener.saveDisposable(d);
                    }

                    @Override
                    public void onNext(ArrayList<Articles> articles) {
                        listener.onSuccess(articles, curPage);
                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.onFailure("数据加载错误", new Exception("retrofit request erro."));
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    public void articleDetail(final DetailModelImpl.OnLoadArticlesDetailListener listener, String href) {

        Observable<ArticleDetail> observable = service.articleDetail(href);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ArticleDetail>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        listener.saveDisposable(d);
                    }

                    @Override
                    public void onNext(ArticleDetail articleDetail) {
                        listener.onSuccess(articleDetail);
                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.onFailure("数据加载错误", new Exception("retrofit request erro."));
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

}
