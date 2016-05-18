package me.ixfan.wechatkit.material;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 * Created by xfan on 16/4/6.
 */
@XmlAccessorType(XmlAccessType.NONE)
public class MediaObject {

    private String mediaId;

    public MediaObject() {}

    public MediaObject(String mediaId) {
        this.mediaId = mediaId;
    }

    @XmlElement(name = "MediaId", required = true)
    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }
}
