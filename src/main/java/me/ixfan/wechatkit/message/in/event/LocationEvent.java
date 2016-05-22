package me.ixfan.wechatkit.message.in.event;

/**
 * Created by xfan on 16/3/27.
 */
public class LocationEvent extends EventMsg {

    /**
     * 地理位置纬度
     */
    private Double latitude;

    /**
     * 地理位置经度
     */
    private Double longitude;

    /**
     * 地理位置精度
     */
    private Double precision;

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getPrecision() {
        return precision;
    }

    public void setPrecision(Double precision) {
        this.precision = precision;
    }

    @Override
    public String getEvent() {
        return EventType.LOCATION.value();
    }
}
