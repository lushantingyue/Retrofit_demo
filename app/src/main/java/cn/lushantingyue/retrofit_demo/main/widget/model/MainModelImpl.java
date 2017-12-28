package cn.lushantingyue.retrofit_demo.main.widget.model;

import java.util.ArrayList;

import cn.lushantingyue.retrofit_demo.api.ApiService;
import cn.lushantingyue.retrofit_demo.bean.Articles;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Lushantingyue on 2017/12/28.
 * Responsibilities:
 * Description:
 * ProjectName:
 */

public class MainModelImpl implements MainModel {

    @Override
    public void loadArticles(final MainModelImpl.OnLoadArticlesListListener listener) {

        final String api = "http://192.168.2.30:3000/"; // 连内网使用
        final String wifi = "http://192.168.155.1:3000/"; // 连本机wifi使用

        new Thread() {
            @Override
            public void run() {

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(wifi)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                ApiService service = retrofit.create(ApiService.class);
                final Call<ArrayList<Articles>> call = service.listData();
                call.enqueue(new Callback<ArrayList<Articles>>() {

                    @Override
                    public void onResponse(Call<ArrayList<Articles>> call, Response<ArrayList<Articles>> response) {
                        if (response.body() != null) {
                            ArrayList<Articles> resp = response.body();
                            listener.onSuccess(resp);
                        }
                    }

                    @Override
                    public void onFailure(Call<ArrayList<Articles>> call, Throwable t) {
                        t.printStackTrace();
                    }
                });

            }
        }.start();
    }

    @Override
    public void loadArticlesByPage(int page, MainModelImpl.OnLoadArticlesDetailListener listener) {

    }

    public interface OnLoadArticlesListListener {
        void onSuccess(ArrayList<Articles> list);
        void onFailure(String msg, Exception e);
    }

    public interface OnLoadArticlesDetailListener { }
}
