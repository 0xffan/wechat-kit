package me.ixfan.wechatkit.message.out;

/**
 * Created by xfan on 16/3/27.
 */
public enum ResponseMsgType {

    Text("text", "文本消息"),
    Image("image", "图片消息"),
    Voice("voice", "语音消息"),
    Video("video", "视频消息"),
    Music("music", "音乐消息"),
    News("news", "图文消息");

    private String value;
    private String description;

    ResponseMsgType(String msgType, String description) {
        this.value = msgType;
        this.description = description;
    }

    public String value() {
        return value;
    }

    public String description() {
        return description;
    }
}
