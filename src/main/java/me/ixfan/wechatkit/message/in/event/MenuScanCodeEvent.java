package me.ixfan.wechatkit.message.in.event;

/**
 * 扫码推事件的事件推送.
 *
 * Created by xfan on 16/5/22.
 */
public class MenuScanCodeEvent extends EventMsg {

    /**
     * 事件KEY值，由开发者在创建菜单时设定
     */
    private String eventKey;
    /**
     * 扫描信息
     */
    private ScanCodeInfo scanCodeInfo;

    public void setEventKey(String eventKey) {
        this.eventKey = eventKey;
    }

    public void setScanCodeInfo(ScanCodeInfo scanCodeInfo) {
        this.scanCodeInfo = scanCodeInfo;
    }

    public String getEventKey() {

        return eventKey;
    }

    public ScanCodeInfo getScanCodeInfo() {
        return scanCodeInfo;
    }

}
