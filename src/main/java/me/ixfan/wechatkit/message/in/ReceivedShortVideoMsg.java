package me.ixfan.wechatkit.message.in;


/**
 * Created by xfan on 16/3/27.
 */
public class ReceivedShortVideoMsg extends ReceivedVideoMsg {

    @Override
    public String getMsgType() {
        return ReceivedMsgType.ShortVideo.value();
    }
}
