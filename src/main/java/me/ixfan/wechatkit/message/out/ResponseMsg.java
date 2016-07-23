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

package me.ixfan.wechatkit.message.out;

import me.ixfan.wechatkit.message.out.xml.XmlSerializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Created by xfan on 16/3/26.
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(propOrder = { "toUserName", "fromUserName", "createTime", "msgType"})
public abstract class ResponseMsg implements XmlSerializable {

    /**
     * 开发者微信号
     */
    private String fromUserName;

    /**
     * 接收方帐号(收到的OpenID)
     */
    private String toUserName;

    /**
     * 消息创建时间(整型)
     */
    private Long createTime;

    /**
     * 回复消息的类型. {@code ResponseMsgType} 定义了可选的回复消息类型.
     */
    private String msgType;

    @XmlElement(name = "FromUserName", required = true)
    public String getFromUserName() {
        return fromUserName;
    }

    public void setFromUserName(String fromUserName) {
        this.fromUserName = fromUserName;
    }

    @XmlElement(name = "CreateTime", required = true)
    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    @XmlElement(name = "ToUserName", required = true)
    public String getToUserName() {
        return toUserName;
    }

    public void setToUserName(String toUserName) {
        this.toUserName = toUserName;
    }

    @XmlElement(name = "MsgType", required = true)
    public String getMsgType() {
        return msgType;
    }

    public String[] cdataElements() {
        return new String[] { "^FromUserName", "^ToUserName", "^MsgType"};
    }

    @Override
    public String toString() {
        return "ToUserName='" + this.toUserName
                + "', FromUserName='" + this.fromUserName
                + "', CreateTime='" + this.createTime
                + "'";
    }

}
