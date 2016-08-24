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

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 用户关注事件。用户未关注时，通过扫码进行关注后的事件推送数据包中包含 <code>EventKey</code> 和 <code>Ticket</code>。
 *
 * Created by xfan on 16/3/27.
 */
@XmlRootElement(name = "xml")
public class SubscribeEvent extends EventMsg {

    /**
     * 事件KEY值，qrscene_为前缀，后面为二维码的参数值
     *
     * 说明: 当未关注公众号的用户通过扫描二维码关注公众号时, 推送的 subscribe 事件
     * 会包含 <code>EventKey</code> 和 <code>Ticket</code>.
     */
    private String eventKey;

    /**
     * 二维码的ticket，可用来换取二维码图片
     */
    private String ticket;

    @XmlElement(name = "EventKey")
    public String getEventKey() {
        return eventKey;
    }

    public void setEventKey(String eventKey) {
        this.eventKey = eventKey;
    }

    @XmlElement(name = "Ticket")
    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    @Override
    public String getEvent() {
        return EventType.SUBSCRIBE.stringValue();
    }
}
