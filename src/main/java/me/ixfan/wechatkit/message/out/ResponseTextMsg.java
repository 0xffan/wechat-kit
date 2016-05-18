package me.ixfan.wechatkit.message.out;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by xfan on 16/3/26.
 */
@XmlRootElement(name = "xml")
public class ResponseTextMsg extends ResponseMsg {

    /**
     * 回复的消息内容（换行：在content中能够换行，微信客户端就支持换行显示）
     */
    private String content;

    @XmlElement(name = "Content", required = true)
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String getMsgType() {
        return ResponseMsgType.Text.value();
    }

    @Override
    public String[] cdataElements() {
        return new String[] {"^ToUserName", "^FromUserName", "^MsgType", "^Content" };
    }

    @Override
    public String toString() {
        return "TextMsg[" + super.toString()
                + ", MsgType='" + this.getMsgType()
                + ", Content='" + this.content
                + "']";
    }

}
