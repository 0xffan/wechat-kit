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

/**
 * 微信推送给开发者的事件类型
 *
 * Created by xfan on 16/3/26.
 */
public enum EventType {

    /**
     * 关注事件
     */
    SUBSCRIBE { public String stringValue() { return "subscribe"; }},
    /**
     * 取消关注事件
     */
    UNSUBSCRIBE { public String stringValue() { return "unsubscribe";}},

    /**
     * 扫描带参数二维码事件:
     * 1. 用户未关注公众号, 则用户可以关注公众号，关注后微信会将带场景值关注事件(subscribe)推送给开发者.
     * 2. 用户已关注公众号, 则微信会将带场景值扫描事件(SCAN)推送给开发者.
     */
    SCAN { public String stringValue() { return "SCAN";}},

    /** 上报地理位置信息 */
    LOCATION { public String stringValue() { return "LOCATION"; }},

    /**
     * 自定义菜单事件: 点击菜单拉取消息时的事件推送
     */
    MENU_CLICK { public String stringValue() { return "CLICK"; }},
    /**
     * 自定义菜单事件: 点击菜单跳转链接时的事件推送
     */
    MENU_VIEW { public String stringValue() { return "VIEW"; }},
    /**
     * 自定义菜单事件: 扫码推事件的事件推送
     */
    MENU_SCAN_CODE_PUSH { public String stringValue() { return "scancode_push"; }},
    /**
     * 自定义菜单事件: 扫码推事件且弹出“消息接收中”提示框的事件推送
     */
    MENU_SCAN_CODE_WAIT_MSG { public String stringValue() { return "scancode_waitmsg"; }},
    /**
     * 自定义菜单事件: 弹出系统拍照发图的事件推送
     */
    MENU_PIC_SYS_PHOTO { public String stringValue() { return "pic_sysphoto"; }},
    /**
     * 自定义菜单事件: 弹出拍照或者相册发图的事件推送
     */
    MENU_PIC_OR_ALBUM { public String stringValue() { return "pic_photo_or_album"; }},
    /**
     * 自定义菜单事件: 弹出微信相册发图器的事件推送
     */
    MENU_PIC_WEIXIN { public String stringValue() { return "pic_weixin"; }},
    /**
     * 自定义菜单事件: 弹出地理位置选择器的事件推送
     */
    MENU_LOCATION_SELECT { public String stringValue() { return "location_select"; }},
    UNKNOWN { public String stringValue() { return "unknown"; }};

    public static EventType of(String eventKey) {
        switch (eventKey) {
            case "subscribe": return SUBSCRIBE;
            case "unsubscribe": return UNSUBSCRIBE;
            case "SCAN": return SCAN;
            case "LOCATION": return LOCATION;
            case "CLICK": return MENU_CLICK;
            case "VIEW": return MENU_VIEW;
            case "scancode_push": return MENU_SCAN_CODE_PUSH;
            case "scancode_waitmsg": return MENU_SCAN_CODE_WAIT_MSG;
            case "pic_sysphoto": return MENU_PIC_SYS_PHOTO;
            case "pic_photo_or_album": return MENU_PIC_OR_ALBUM;
            case "pic_weixin": return MENU_PIC_WEIXIN;
            case "location_select": return MENU_LOCATION_SELECT;
            default: return UNKNOWN;
        }
    }

    public abstract String stringValue();

}
