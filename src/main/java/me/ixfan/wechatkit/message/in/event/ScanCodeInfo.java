package me.ixfan.wechatkit.message.in.event;

/**
 * 扫描信息
 * Created by xfan on 16/5/22.
 */
public class ScanCodeInfo {

    /**
     * 扫描类型，一般是 qrcode
     */
    private String scanType;
    /**
     * 扫描结果，即二维码对应的字符串信息
     */
    private String scanResult;

    public void setScanType(String scanType) {
        this.scanType = scanType;
    }

    public void setScanResult(String scanResult) {
        this.scanResult = scanResult;
    }

    public String getScanType() {

        return scanType;
    }

    public String getScanResult() {
        return scanResult;
    }
}
