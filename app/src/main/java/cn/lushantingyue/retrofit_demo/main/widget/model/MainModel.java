package cn.lushantingyue.retrofit_demo.main.widget.model;

/**
 * Created by Lushantingyue on 2017/12/28.
 * Responsibilities:
 * Description:
 * ProjectName:
 */

public interface MainModel {

    void loadArticles(MainModelImpl.OnLoadArticlesListListener listener);
    void loadArticlesByPage(int page, MainModelImpl.OnLoadArticlesDetailListener listener);
}
