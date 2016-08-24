/*
 * MIT License
 *
 * Copyright (c) 2016 Warren Fan
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package me.ixfan.wechatkit.message.in.event;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 通过菜单扫码的事件推送, 分两种: 1. 扫码推事件的事件推送; 2. 扫码推事件且弹出“消息接收中”提示框的事件推送。
 *
 * Created by xfan on 16/5/22.
 */
@XmlRootElement(name = "xml")
public class MenuScanCodeEvent extends EventMsg {

    /**
     * 事件KEY值，由开发者在创建菜单时设定
     */
    private String eventKey;
    /**
     * 扫描信息
     */
    private ScanCodeInfo scanCodeInfo;

    @XmlElement(name = "EventKey", required = true)
    public String getEventKey() {
        return eventKey;
    }

    public void setEventKey(String eventKey) {
        this.eventKey = eventKey;
    }

    @XmlElement(name = "ScanCodeInfo")
    public ScanCodeInfo getScanCodeInfo() {
        return scanCodeInfo;
    }

    public void setScanCodeInfo(ScanCodeInfo scanCodeInfo) {
        this.scanCodeInfo = scanCodeInfo;
    }

    /**
     * 扫描结果信息.
     *
     * Created by xfan on 16/5/22.
     */
    @XmlAccessorType(XmlAccessType.NONE)
    private class ScanCodeInfo {

        /**
         * 扫描类型，一般是 qrcode
         */
        private String scanType;
        /**
         * 扫描结果，即二维码对应的字符串信息
         */
        private String scanResult;

        @XmlElement(name = "ScanType")
        public String getScanType() {
            return scanType;
        }

        public void setScanType(String scanType) {
            this.scanType = scanType;
        }

        @XmlElement(name = "ScanResult")
        public String getScanResult() {
            return scanResult;
        }

        public void setScanResult(String scanResult) {
            this.scanResult = scanResult;
        }
    }

}
