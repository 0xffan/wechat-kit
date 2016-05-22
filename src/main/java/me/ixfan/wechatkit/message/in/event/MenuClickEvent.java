package me.ixfan.wechatkit.message.in.event;

/**
 * Created by xfan on 16/3/27.
 *
 * 点击菜单拉取消息时的事件推送.
 */
public class MenuClickEvent extends EventMsg {

    /**
     * 事件KEY值, 与自定义菜单接口中KEY值对应
     */
    private String eventKey;

    public String getEventKey() {
        return eventKey;
    }

    public void setEventKey(String eventKey) {
        this.eventKey = eventKey;
    }

    @Override
    public String getEvent() {
        return EventType.MENU_CLICK.value();
    }
}
