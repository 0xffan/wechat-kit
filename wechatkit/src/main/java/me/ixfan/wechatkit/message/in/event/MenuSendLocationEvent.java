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
 * 弹出地理位置选择器的事件推送。
 *
 * @author Warren Fan
 */
@XmlRootElement(name = "xml")
public class MenuSendLocationEvent extends EventMsg {
    /**
     * 事件KEY值，由开发者在创建菜单时设定。
     */
    private String eventKey;
    /**
     * 发送的位置信息。
     */
    private SendLocationInfo sendLocationInfo;

    public String getEventKey() {
        return eventKey;
    }

    public void setEventKey(String eventKey) {
        this.eventKey = eventKey;
    }

    public SendLocationInfo getSendLocationInfo() {
        return sendLocationInfo;
    }

    public void setSendLocationInfo(SendLocationInfo sendLocationInfo) {
        this.sendLocationInfo = sendLocationInfo;
    }

    @Override
    public String getEvent() {
        return EventType.MENU_LOCATION_SELECT.stringValue();
    }

    @XmlAccessorType(XmlAccessType.NONE)
    private class SendLocationInfo {
        /**
         * X 坐标信息。
         */
        private double locationX;
        /**
         * Y 坐标信息。
         */
        private double getLocationY;
        /**
         * 精度，可理解为精度或者比例尺、越精细的话 scale 越高。
         */
        private float scale;
        /**
         * 地理位置的字符串信息。
         */
        private String label;
        /**
         * 朋友圈POI的名字，可能为空。
         */
        private String poiName;

        @XmlElement(name = "Location_X")
        public double getLocationX() {
            return locationX;
        }

        public void setLocationX(double locationX) {
            this.locationX = locationX;
        }

        @XmlElement(name = "Location_Y")
        public double getGetLocationY() {
            return getLocationY;
        }

        public void setGetLocationY(double getLocationY) {
            this.getLocationY = getLocationY;
        }

        @XmlElement(name = "Scale")
        public float getScale() {
            return scale;
        }

        public void setScale(float scale) {
            this.scale = scale;
        }

        @XmlElement(name = "Label")
        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        @XmlElement(name = "Poiname")
        public String getPoiName() {
            return poiName;
        }

        public void setPoiName(String poiName) {
            this.poiName = poiName;
        }
    }
}
