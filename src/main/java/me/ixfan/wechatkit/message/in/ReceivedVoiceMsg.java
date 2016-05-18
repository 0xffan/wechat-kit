package me.ixfan.wechatkit.message.in;

/**
 * Created by xfan on 16/3/26.
 */
public class ReceivedVoiceMsg extends ReceivedMsg {

    /**
     * 消息ID, 64位整型
     */
    private String msgId;

    /**
     * 语音消息媒体id, 可以调用多媒体文件下载接口拉取数据.
     */
    private String mediaId;

    /**
     * 语音格式, 如amr, speex等.
     */
    private String format;

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    @Override
    public String getMsgType() {
        return ReceivedMsgType.Voice.value();
    }
}
