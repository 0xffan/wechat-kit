/*
 * MIT License
 *
 * Copyright (c) 2016 Warren Fan
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

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
