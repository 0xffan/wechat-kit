package me.ixfan.wechatkit.message.out;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by xfan on 16/3/27.
 */
@XmlRootElement(name = "xml")
public class ResponseNewsMsg extends ResponseMsg {

    /**
     * 图文消息个数，限制为10条以内
     */
    private Integer articleCount;

    /**
     * 多条图文消息信息，默认第一个item为大图,注意，如果图文数超过10，则将会无响应
     */
    private List<Article> articles;

    @XmlElement(name = "ArticleCount", required = true)
    public Integer getArticleCount() {
        return articleCount;
    }

    public void setArticleCount(Integer articleCount) {
        this.articleCount = articleCount;
    }

    @XmlElement(name = "item")
    @XmlElementWrapper(name = "Articles", required = true)
    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }

    public void addArticle(Article article) {
        this.articles.add(article);
    }

    @Override
    public String getMsgType() {
        return ResponseMsgType.News.value();
    }

    @Override
    public String[] cdataElements() {
        return new String[] {"^ToUserName", "^FromUserName", "^MsgType",
                "^Title", "^Description", "^PicUrl", "^Url" };
    }

    @Override
    public String toString() {
        StringBuilder sBuilder = new StringBuilder();
        sBuilder.append("NewsMsg[" + super.toString());
        sBuilder.append(", msgType='" + this.getMsgType() + "', ");
        for(Article article: this.articles) {
            sBuilder.append(article.toString());
            sBuilder.append(", ");
        }
        sBuilder.delete(sBuilder.length() - 2, sBuilder.length());
        sBuilder.append("]");

        return sBuilder.toString();
    }

}
