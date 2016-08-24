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

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * 通过菜单发送图片事件, 发图方式包括弹出系统拍照发图、弹出拍照或者相册发图、弹出微信相册发图。
 *
 * @author Warren Fan
 */
@XmlRootElement(name = "xml")
public class MenuSendPictureEvent extends EventMsg {

    /**
     * 事件KEY值，由开发者在创建菜单时设定。
     */
    private String eventKey;
    /**
     * 发送的图片信息。
     */
    private SendPicsInfo sendPicsInfo;

    @XmlElement(name = "EventKey")
    public String getEventKey() {
        return eventKey;
    }

    public void setEventKey(String eventKey) {
        this.eventKey = eventKey;
    }

    @XmlElement(name = "SendPicsInfo")
    public SendPicsInfo getSendPicsInfo() {
        return sendPicsInfo;
    }

    public void setSendPicsInfo(SendPicsInfo sendPicsInfo) {
        this.sendPicsInfo = sendPicsInfo;
    }

    @XmlAccessorType(XmlAccessType.NONE)
    private class SendPicsInfo {
        /**
         * 发送的图片数量。
         */
        private int count;
        /**
         * 图片列表。
         */
        List<PictureItem> picList;

        @XmlElement(name = "Count")
        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        @XmlElement(name = "item")
        @XmlElementWrapper(name = "PicList")
        public List<PictureItem> getPicList() {
            return picList;
        }

        public void setPicList(List<PictureItem> picList) {
            this.picList = picList;
        }
    }

    @XmlAccessorType(XmlAccessType.NONE)
    private class PictureItem {
        /**
         * 图片的MD5值，开发者若需要，可用于验证接收到图片。
         */
        private String picMd5Sum;

        @XmlElement(name = "PicMd5Sum")
        public String getPicMd5Sum() {
            return picMd5Sum;
        }

        public void setPicMd5Sum(String picMd5Sum) {
            this.picMd5Sum = picMd5Sum;
        }
    }
}
