package cn.lushantingyue.retrofit_demo.api;

import java.util.ArrayList;
import java.util.HashMap;

import cn.lushantingyue.retrofit_demo.bean.ArticleDetail;
import cn.lushantingyue.retrofit_demo.bean.Articles;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by Lushantingyue on 2017/12/25 16.
 * Responsibilities:
 * Description:
 * ProjectName:
 */

public interface ApiService {

    //    请求全部数据
    @GET("data/jianshu")
    Call<ArrayList<Articles>> listData();

    //    分页请求数据
    @POST("data/jianshuList")
    Call<ArrayList<Articles>> listDataByPage(@Body HashMap<String, Integer> page);

    //    http://localhost:3000/data/jianshuDetail/:articalId
    //  根据文章关联href 请求文章详情
    @GET("data/jianshuDetail/{articalId}")
    Call<ArticleDetail> articleDetail(@Path("articalId") String articalId);
}
