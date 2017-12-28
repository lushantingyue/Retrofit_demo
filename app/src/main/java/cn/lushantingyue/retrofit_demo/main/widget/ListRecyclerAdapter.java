/*
 * Copyright 2017 Lushantingyue
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.lushantingyue.retrofit_demo.main.widget;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import cn.lushantingyue.retrofit_demo.detail.widget.ArticalDetailActivity;
import cn.lushantingyue.retrofit_demo.R;
import cn.lushantingyue.retrofit_demo.api.ApiService;
import cn.lushantingyue.retrofit_demo.bean.ArticleDetail;
import cn.lushantingyue.retrofit_demo.bean.Articles;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created on 2016/7/14.
 *
 * @author Yan Zhenjie.
 */
public class ListRecyclerAdapter extends Adapter<ListRecyclerAdapter.DefineViewHolder> {

//    private List<String> list;
    private ArrayList<Articles> list;
    Activity ctx;

    public ListRecyclerAdapter(Activity ctx, ArrayList<Articles> list) {
        this.ctx = ctx;
        this.list = list;
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public ArrayList<Articles> getList() {
        return this.list;
    }

    @Override
    public void onBindViewHolder(DefineViewHolder viewHolder, int position) {
        viewHolder.setData(list.get(position).get_abstract(),list.get(position).getHref().split("/")[2]);
    }

    @Override
    public DefineViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item, parent, false);
        return new DefineViewHolder(view, parent.getContext());
    }

    static class DefineViewHolder extends ViewHolder {

        TextView tvTitle;
        String href;

        public DefineViewHolder(View itemView, final Context ctx) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_title);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
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

                                Call<ArticleDetail> call = service.articleDetail(href);
                                call.enqueue(new Callback<ArticleDetail>() {


                                    @Override
                                    public void onResponse(Call<ArticleDetail> call, Response<ArticleDetail> response) {
                                        if (response.body() != null) {
                                            ArticleDetail resp = response.body();
                                            String title = resp.getTitle() + resp.getAuthor();
                                            Intent intent = new Intent(ctx, ArticalDetailActivity.class);
                                            intent.putExtra("href", resp.getHref());
                                            ctx.startActivity(intent);
                                            com.orhanobut.logger.Logger.i(title + ">>>>>>>>>>>>>>>>>>>>>");
                                        }

                                    }
                                    @Override
                                    public void onFailure(Call<ArticleDetail> call, Throwable t) {
                                        t.printStackTrace();
                                    }
                                });
                            }
                        }.start();
                }
            });
        }

        public void setData(String text, String href) {
            tvTitle.setText(text);
            this.href = href;
        }

    }

}
