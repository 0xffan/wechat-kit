package me.ixfan.wechatkit.message.in.event;

/**
 * Created by xfan on 16/3/27.
 */
public class ReceivedQrSceneEvent extends ReceivedEventMsg {

    /**
     * 事件KEY值，是一个32位无符号整数, 即创建二维码时的二维码scene_id
     */
    private String eventKey;

    /**
     * 二维码的ticket, 可用来换取二维码图片
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
        return EventType.SCAN.value();
    }
}
