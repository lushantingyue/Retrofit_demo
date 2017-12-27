package cn.lushantingyue.retrofit_demo.api;

import java.util.ArrayList;

import cn.lushantingyue.retrofit_demo.bean.ArticleDetail;
import cn.lushantingyue.retrofit_demo.bean.Articles;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Administrator on 2017/12/25.
 */

public interface ApiService {

    @GET("data/jianshu")
    Call<ArrayList<Articles>> listData();

    //    http://localhost:3000/data/jianshuDetail/:articalId
    @GET("data/jianshuDetail/{articalId}")
    Call<ArticleDetail> articleDetail(@Path("articalId") String articalId);
}