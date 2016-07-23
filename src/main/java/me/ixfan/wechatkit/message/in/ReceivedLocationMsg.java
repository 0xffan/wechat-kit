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
