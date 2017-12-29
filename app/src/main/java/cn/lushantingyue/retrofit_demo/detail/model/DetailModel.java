package cn.lushantingyue.retrofit_demo.detail.model;

/**
 * Created by Administrator on 2017/12/29 11.
 * Responsibilities:
 * Description:
 * ProjectName:
 */

public interface DetailModel {

    void loadArticlesDetail(String href, DetailModelImpl.OnLoadArticlesDetailListener listener);
}
