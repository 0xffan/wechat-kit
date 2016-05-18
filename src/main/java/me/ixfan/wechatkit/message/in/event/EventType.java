package me.ixfan.wechatkit.message.in.event;

/**
 * Created by xfan on 16/3/26.
 */
public enum EventType {

    SUBSCRIBE("subscribe", "关注事件"),
    UNSUBSCRIE("unsubscribe", "取消关注事件"),

    /**
     * 扫描带参数二维码事件:
     * 1. 用户未关注公众号, 则用户可以关注公众号，关注后微信会将带场景值关注事件(subscribe)推送给开发者.
     * 2. 用户已关注公众号, 则微信会将带场景值扫描事件(SCAN)推送给开发者.
     */
    SCAN("SCAN", "扫描带参数二维码事件"),

    /** 上报地理位置信息 */
    LOCATION("LOCATION", "上报地理位置事件"),

    /**
     * 自定义菜单事件
     */
    MENU_CLICK("CLICK", "点击菜单拉取消息时的事件推送"),
    MENU_VIEW("VIEW", "点击菜单跳转链接时的事件推送");

    private String value;
    private String description;

    EventType(String eventType, String description) {
        this.value = eventType;
        this.description = description;
    }

    public String value() {
        return this.value;
    }

    public String description() {
        return this.description;
    }
}
