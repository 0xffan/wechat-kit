/**
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

import me.ixfan.wechatkit.material.MediaObject;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by xfan on 16/3/27.
 */
@XmlRootElement(name = "xml")
public class ResponseImageMsg extends  ResponseMsg {

    /**
     * 通过素材管理接口上传多媒体文件, 得到的id.
     */
    private MediaObject mediaImage;

    @XmlElement(name = "Image", required = true)
    public MediaObject getMediaImage() {
        return mediaImage;
    }

    public void setMediaImage(MediaObject mediaImage) {
        this.mediaImage = mediaImage;
    }

    @Override
    public String getMsgType() {
        return ResponseMsgType.Image.value();
    }

    @Override
    public String[] cdataElements() {
        return new String[] { "^ToUserName", "^FromUserName", "^MsgType", "^MediaId" };
    }

    @Override
    public String toString() {
        return "ImageMsg[" + super.toString()
                + ", MsgType='" + this.getMsgType()
                + "', Image[MediaId='" + this.mediaImage.getMediaId()
                + "']]";
    }
}
