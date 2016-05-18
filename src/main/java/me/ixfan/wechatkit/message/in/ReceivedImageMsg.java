package me.ixfan.wechatkit.message.in;

/**
 * Created by xfan on 16/3/26.
 */
public class ReceivedImageMsg extends ReceivedMsg {

    /**
     * 消息id，64位整型
     */
    private String msgId;

    /**
     * 图片链接
     */
    private String picUrl;

    /**
     * 图片消息媒体id, 可以调用多媒体文件下载接口拉取数据
     */
    private String mediaId;

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }

    @Override
    public String getMsgType() {
        return ReceivedMsgType.Image.value();
    }
}
