package me.ixfan.wechatkit.message.out;

import me.ixfan.wechatkit.message.out.xml.XmlSerializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Created by xfan on 16/3/31.
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(propOrder = { "title", "description", "musicUrl", "hqMusicUrl", "thumbMediaId"})
public class Music implements XmlSerializable {

    /**
     * 音乐标题
     */
    private String title;

    /**
     * 音乐描述
     */
    private String description;

    /**
     * 音乐链接
     */
    private String musicUrl;

    /**
     * 高质量音乐链接，WIFI环境优先使用该链接播放音乐
     */
    private String hqMusicUrl;

    /**
     * 缩略图的媒体id，通过素材管理接口上传多媒体文件，得到的id
     */
    private String thumbMediaId;

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

    @XmlElement(name = "MusicUrl")
    public String getMusicUrl() {
        return musicUrl;
    }

    public void setMusicUrl(String musicUrl) {
        this.musicUrl = musicUrl;
    }

    @XmlElement(name = "HQMusicUrl")
    public String getHqMusicUrl() {
        return hqMusicUrl;
    }

    public void setHqMusicUrl(String hqMusicUrl) {
        this.hqMusicUrl = hqMusicUrl;
    }

    @XmlElement(name = "ThumbMediaId")
    public String getThumbMediaId() {
        return thumbMediaId;
    }

    public void setThumbMediaId(String thumbMediaId) {
        this.thumbMediaId = thumbMediaId;
    }

    @Override
    public String toString() {
        return "Music[title='" + this.title +
                "', description='" + this.description
                + "', musicUrl='" + this.musicUrl
                + "', HQMusicUrl='" + this.hqMusicUrl
                + "', ThumbMediaId=" + this.thumbMediaId
                + "']";
    }

    @Override
    public String[] cdataElements() {
        return new String[] { "^Title", "^Description", "^MusicUrl", "^HQMusicUrl", "^ThumbMediaId"};
    }
}
