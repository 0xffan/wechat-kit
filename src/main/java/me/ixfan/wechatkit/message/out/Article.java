package me.ixfan.wechatkit.message.out;

import me.ixfan.wechatkit.message.out.xml.XmlSerializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Created by xfan on 16/3/27.
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(propOrder = { "title", "description", "picUrl", "url" })
public class Article implements XmlSerializable {

    /**
     * 图文消息标题
     */
    private String title;

    /**
     * 图文消息描述
     */
    private String description;

    /**
     * 图片链接，支持JPG、PNG格式，较好的效果为大图360*200，小图200*200
     */
    private String picUrl;

    /**
     * 点击图文消息跳转链接
     */
    private String url;

    @XmlElement(name = "Title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @XmlElement(name = "Description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @XmlElement(name = "PicUrl")
    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    @XmlElement(name = "Url")
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "Article[title='" + this.title
                + "', description='" + this.description
                + "', picUrl='" + this.picUrl
                + "', url='" + this.url
                + "']";
    }

    @Override
    public String[] cdataElements() {
        return new String[] {"^Title", "^Description", "^PicUrl", "^Url"};
    }
}
