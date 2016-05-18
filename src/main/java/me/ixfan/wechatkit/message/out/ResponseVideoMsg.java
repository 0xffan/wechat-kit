package me.ixfan.wechatkit.message.out;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by xfan on 16/3/27.
 */
@XmlRootElement(name = "xml")
public class ResponseVideoMsg extends ResponseMsg {

    private Video video;

    @XmlElement(name = "Video", required = true)
    public Video getVideo() {
        return video;
    }

    public void setVideo(Video video) {
        this.video = video;
    }

    @Override
    public String getMsgType() {
        return ResponseMsgType.Video.value();
    }

    @Override
    public String[] cdataElements() {
        return new String[] { "^ToUserName", "^FromUserName", "^MsgType",
            "^MediaId", "^Title", "^Description" };
    }

    @Override
    public String toString() {
        return "VideoMsg[" + super.toString()
                + ", MsgType='" + this.getMsgType()
                + "', " + video.toString() + "]";
    }
}
