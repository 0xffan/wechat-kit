package me.ixfan.wechatkit.message.in;

/**
 * Created by xfan on 16/3/26.
 */
public abstract class ReceivedMsg {

    /**
     * 发送方帐号(一个OpenID)
     */
    private String fromUserName;

    /**
     * 开发者微信号
     */
    private String toUserName;

    /**
     * 消息创建时间(整型)
     */
    private Long createTime;

    /**
     * 接收消息的类型. {@code ReceivedMsgType} 定义了可选的接收消息类型.
     */
    private String msgType;

    public String getToUserName() {
        return toUserName;
    }

    public void setToUserName(String toUserName) {
        this.toUserName = toUserName;
    }

    public String getFromUserName() {
        return fromUserName;
    }

    public void setFromUserName(String fromUserName) {
        this.fromUserName = fromUserName;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public String getMsgType() {
        return msgType;
    }

}
