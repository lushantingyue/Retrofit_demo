package cn.lushantingyue.retrofit_demo.main.widget.widget.multitype;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import cn.lushantingyue.retrofit_demo.R;
import cn.lushantingyue.retrofit_demo.bean.Articles;
import me.drakeet.multitype.ItemViewBinder;

/**
 * Created by Administrator on 2018/1/16 17.
 * Responsibilities:
 * Description:
 * ProjectName:
 */

public class ArticleViewBinder extends ItemViewBinder<Articles, ArticleViewBinder.ViewHolder> {

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View root = inflater.inflate(R.layout.item, parent, false);
        return new ViewHolder(root);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull Articles article) {
        holder.tv_title.setText(article.get_abstract());
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @NonNull
        private final TextView tv_title;

        public ViewHolder(View itemView) {
            super(itemView);
            this.tv_title = itemView.findViewById(R.id.tv_title);
        }
    }
}
