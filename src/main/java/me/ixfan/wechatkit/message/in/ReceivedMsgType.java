package me.ixfan.wechatkit.message.in;

/**
 * Created by xfan on 16/3/27.
 */
public enum ReceivedMsgType {

    Text("text", "文本消息"),
    Image("image", "图片消息"),
    Voice("voice", "语音消息"),
    Video("video", "视频消息"),
    ShortVideo("shortvideo", "小视频消息"),
    Location("location", "地理位置消息"),
    Link("link", "链接消息"),
    Event("event", "事件推送");

    private String value;
    private String description;

    ReceivedMsgType(String msgType, String description) {
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
