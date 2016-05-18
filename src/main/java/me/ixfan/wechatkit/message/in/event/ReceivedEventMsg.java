package me.ixfan.wechatkit.message.in.event;

import me.ixfan.wechatkit.message.in.ReceivedMsg;
import me.ixfan.wechatkit.message.in.ReceivedMsgType;

/**
 * Created by xfan on 16/3/27.
 */
public abstract class ReceivedEventMsg extends ReceivedMsg {

    private String event;

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    @Override
    public String getMsgType() {
        return ReceivedMsgType.Event.value();
    }
}
