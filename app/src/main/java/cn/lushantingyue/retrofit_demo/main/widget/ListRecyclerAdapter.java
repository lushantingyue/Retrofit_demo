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
import android.util.TimingLogger;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.orhanobut.logger.Logger;

import java.util.ArrayList;

import cn.lushantingyue.retrofit_demo.detail.widget.ArticalDetailActivity;
import cn.lushantingyue.retrofit_demo.R;
import cn.lushantingyue.retrofit_demo.api.ApiService;
import cn.lushantingyue.retrofit_demo.bean.ArticleDetail;
import cn.lushantingyue.retrofit_demo.bean.Articles;
import cn.lushantingyue.retrofit_demo.listener.MyItemClickListener;
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

    private MyItemClickListener listener;
    private ArrayList<Articles> list;
    Activity ctx;

    public ListRecyclerAdapter(Activity ctx, ArrayList<Articles> list, MyItemClickListener listener) {
        this.ctx = ctx;
        this.list = list;
        this.listener = listener;
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
        return new DefineViewHolder(view, listener, parent.getContext());
    }

    static class DefineViewHolder extends ViewHolder
//            implements View.OnClickListener
    {

        private MyItemClickListener mlistener;
        TextView tvTitle;
        String href;

        public DefineViewHolder(View itemView, MyItemClickListener listener, final Context ctx) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
            this.mlistener = listener;
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mlistener != null) {
                        Logger.i("ListRecyclerAdapter >>> onClick: " + href);
                        mlistener.onItemClick(view, getPosition());
                    }
                }
            });

        }

        public void setData(String text, String href) {
            tvTitle.setText(text);
            this.href = href;
        }

//        @Override
//        public void onClick(View view) {
//            if (mlistener != null) {
//                Logger.i("ListRecyclerAdapter >>> onClick: " + href);
//                mlistener.onItemClick(view, getPosition());
//            }
//        }
    }


//    public interface OnItemClickListener {
//        void onItemClick(AdapterView<?> var1, View var2, int var3, long var4);
//    }
}
