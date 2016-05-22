package me.ixfan.wechatkit.message.in.event;

/**
 * Created by xfan on 16/3/27.
 *
 * 点击菜单跳转链接时的事件推送.
 */
public class MenuViewEvent extends EventMsg {

    /**
     * 事件KEY值, 设置的跳转URL
     */
    private String eventKey;
    /**
     * 指菜单ID，如果是个性化菜单，则可以通过这个字段，知道是哪个规则的菜单被点击了。
     */
    private String menuId;

    public String getEventKey() {
        return eventKey;
    }

    public void setEventKey(String eventKey) {
        this.eventKey = eventKey;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public String getMenuId() {

        return menuId;
    }

    @Override
    public String getEvent() {
        return EventType.MENU_VIEW.value();
    }
}
