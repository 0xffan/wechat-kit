package me.ixfan.wechatkit.message.in;

/**
 * Created by xfan on 16/3/26.
 */
public class ReceivedLocationMsg extends ReceivedMsg {

    /**
     * 消息id，64位整型
     */
    private String msgId;

    /**
     * 地理位置纬度
     */
    private Double location_X;

    /**
     * 地理位置经度
     */
    private Double location_Y;

    /**
     * 地图缩放大小
     */
    private Float scale;

    /**
     * 地理位置信息
     */
    private String label;

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public Double getLocation_X() {
        return location_X;
    }

    public void setLocation_X(Double location_X) {
        this.location_X = location_X;
    }

    public Double getLocation_Y() {
        return location_Y;
    }

    public void setLocation_Y(Double location_Y) {
        this.location_Y = location_Y;
    }

    public Float getScale() {
        return scale;
    }

    public void setScale(Float scale) {
        this.scale = scale;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public String getMsgType() {
        return ReceivedMsgType.Location.value();
    }
}
