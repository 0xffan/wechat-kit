package me.ixfan.wechatkit.message.out;

import me.ixfan.wechatkit.material.MediaObject;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by xfan on 16/3/27.
 */
@XmlRootElement(name = "xml")
public class ResponseImageMsg extends  ResponseMsg {

    /**
     * 通过素材管理接口上传多媒体文件, 得到的id.
     */
    private MediaObject mediaImage;

    @XmlElement(name = "Image", required = true)
    public MediaObject getMediaImage() {
        return mediaImage;
    }

    public void setMediaImage(MediaObject mediaImage) {
        this.mediaImage = mediaImage;
    }

    @Override
    public String getMsgType() {
        return ResponseMsgType.Image.value();
    }

    @Override
    public String[] cdataElements() {
        return new String[] { "^ToUserName", "^FromUserName", "^MsgType", "^MediaId" };
    }

    @Override
    public String toString() {
        return "ImageMsg[" + super.toString()
                + ", MsgType='" + this.getMsgType()
                + "', Image[MediaId='" + this.mediaImage.getMediaId()
                + "']]";
    }
}
