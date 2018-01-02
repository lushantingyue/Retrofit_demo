package cn.lushantingyue.retrofit_demo.main.widget.model;

import java.util.ArrayList;
import java.util.HashMap;

import cn.lushantingyue.retrofit_demo.api.ApiService;
import cn.lushantingyue.retrofit_demo.bean.Articles;
import cn.lushantingyue.retrofit_demo.utils.Constant;
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
    public void loadArticles(final OnLoadArticlesListListener listener, final int curPage) {

        new Thread() {
            @Override
            public void run() {

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(Constant.baseUrl)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                ApiService service = retrofit.create(ApiService.class);
//                final Call<ArrayList<Articles>> call = service.listData();
                HashMap<String, Integer> params = new HashMap<String, Integer>();
                params.put("page", curPage);
                Call<ArrayList<Articles>> call = service.listDataByPage(params);
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
                        listener.onFailure("加载数据失败", new Exception("error network."));
                    }
                });

            }
        }.start();
    }

    public interface OnLoadArticlesListListener {
        void onSuccess(ArrayList<Articles> list);
        void onFailure(String msg, Exception e);
    }

}
