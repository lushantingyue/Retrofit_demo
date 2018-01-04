package cn.lushantingyue.retrofit_demo.bean;

/**
 * Created by Lushantingyue on 2017/12/26 16.
 * Responsibilities:
 * Description:
 * ProjectName:
 */

public class ArticleDetail {

// "_id": "593a6466667af304b49d4f5b",
// "author": "草头三心",
// "title": "能享受孤独的人，一般都是狠角色",
// "text": "<div class=\"show-content\">\n
// "date": "2017.06.07 12:00*",
// "avatar": "//upload.jianshu.io/users/upload_avatars/3291451/d31fdf2a2868.jpeg?imageMogr2/auto-orient/strip|imageView2/1/w/144/h/144",
// "wordage": "字数 1689",
// "href": "/p/079b1be86561",

    String author;
    String title;
    String text;
    String date;
    String avatar;
    String wordage;
    String href;

    public String getAuthor() {
        return author;
    }

    public ArticleDetail setAuthor(String author) {
        this.author = author;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public ArticleDetail setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getText() {
        return text;
    }

    public ArticleDetail setText(String text) {
        this.text = text;
        return this;
    }

    public String getDate() {
        return date;
    }

    public ArticleDetail setDate(String date) {
        this.date = date;
        return this;
    }

    public String getAvatar() {
        return avatar;
    }

    public ArticleDetail setAvatar(String avatar) {
        this.avatar = avatar;
        return this;
    }

    public String getWordage() {
        return wordage;
    }

    public ArticleDetail setWordage(String wordage) {
        this.wordage = wordage;
        return this;
    }

    public String getHref() {
        return href;
    }

    public ArticleDetail setHref(String href) {
        this.href = href;
        return this;
    }


}
