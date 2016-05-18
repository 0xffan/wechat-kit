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
@XmlType(propOrder = { "mediaId", "title", "description"})
public class Video implements XmlSerializable {

    /**
     * 通过素材管理接口上传多媒体文件，得到的id
     */
    private String mediaId;

    /**
     * 视频消息的标题
     */
    private String title;

    /**
     * 视频消息的描述
     */
    private String description;

    @XmlElement(name = "MediaId", required = true)
    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }

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

    @Override
    public String[] cdataElements() {
        return new String[] { "^MediaId", "^Title", "^Description" };
    }

    @Override
    public String toString() {
        return "Video=[title='" + this.title
                + "', description='" + this.description
                + "', mediaId='" + this.mediaId
                + "']";
    }
}
