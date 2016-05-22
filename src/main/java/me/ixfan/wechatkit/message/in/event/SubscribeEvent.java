package me.ixfan.wechatkit.message.in.event;

/**
 * Created by xfan on 16/3/27.
 */
public class SubscribeEvent extends EventMsg {

    /**
     * 事件KEY值，qrscene_为前缀，后面为二维码的参数值
     *
     * 说明: 当未关注公众号的用户通过扫描二维码关注公众号时, 推送的 subscribe 事件
     * 会包含 <code>EventKey</code> 和 <code>Ticket</code>.
     */
    private String eventKey;

    /**
     * 二维码的ticket，可用来换取二维码图片
     */
    private String ticket;

    public String getEventKey() {
        return eventKey;
    }

    public void setEventKey(String eventKey) {
        this.eventKey = eventKey;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    @Override
    public String getEvent() {
        return EventType.SUBSCRIBE.value();
    }
}
