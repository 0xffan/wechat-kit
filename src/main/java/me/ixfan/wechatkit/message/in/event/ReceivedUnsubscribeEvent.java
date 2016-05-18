package me.ixfan.wechatkit.message.in.event;

/**
 * Created by xfan on 16/3/27.
 */
public class ReceivedUnsubscribeEvent extends ReceivedEventMsg {

    @Override
    public String getEvent() {
        return EventType.UNSUBSCRIE.value();
    }
}
