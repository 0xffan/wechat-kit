package me.ixfan.wechatkit.message.in.event;

/**
 * Created by xfan on 16/3/27.
 *
 * 点击菜单跳转链接时的事件推送.
 */
public class ReceivedMenuViewEvent extends ReceivedEventMsg {

    /**
     * 事件KEY值, 设置的跳转URL
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
        return EventType.MENU_VIEW.value();
    }
}
