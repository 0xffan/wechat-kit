package me.ixfan.wechatkit.message.out;

import me.ixfan.wechatkit.material.MediaObject;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by xfan on 16/3/27.
 */
@XmlRootElement(name = "xml")
public class ResponseVoiceMsg extends ResponseMsg {

    /**
     * 通过素材管理接口上传多媒体文件, 得到的id.
     */
    private MediaObject mediaVoice;

    @XmlElement(name = "Voice", required = true)
    public MediaObject getMediaVoice() {
        return mediaVoice;
    }

    public void setMediaVoice(MediaObject mediaVoice) {
        this.mediaVoice = mediaVoice;
    }

    @Override
    public String getMsgType() {
        return ResponseMsgType.Voice.value();
    }

    @Override
    public String[] cdataElements() {
        return new String[] { "^ToUserName", "^FromUserName", "^MsgType", "^MediaId" };
    }

    @Override
    public String toString() {
        return "VoiceMsg[" + super.toString()
                + ", MsgType='" + this.getMsgType()
                + "', Voice[MediaId='" + this.mediaVoice.getMediaId()
                + "']]";
    }
}
