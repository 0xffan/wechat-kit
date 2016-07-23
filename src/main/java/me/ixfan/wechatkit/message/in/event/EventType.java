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

    SUBSCRIBE("subscribe", "关注事件"),
    UNSUBSCRIE("unsubscribe", "取消关注事件"),

    /**
     * 扫描带参数二维码事件:
     * 1. 用户未关注公众号, 则用户可以关注公众号，关注后微信会将带场景值关注事件(subscribe)推送给开发者.
     * 2. 用户已关注公众号, 则微信会将带场景值扫描事件(SCAN)推送给开发者.
     */
    SCAN("SCAN", "扫描带参数二维码事件"),

    /** 上报地理位置信息 */
    LOCATION("LOCATION", "上报地理位置事件"),

    /**
     * 自定义菜单事件: 点击菜单拉取消息时的事件推送
     */
    MENU_CLICK("CLICK", "点击菜单拉取消息时的事件推送"),
    /**
     * 自定义菜单事件: 点击菜单跳转链接时的事件推送
     */
    MENU_VIEW("VIEW", "点击菜单跳转链接时的事件推送"),
    /**
     * 自定义菜单事件: 扫码推事件的事件推送
     */
    MENU_SCAN_CODE_PUSH("scancode_push", "扫码推事件的事件推送"),
    /**
     * 自定义菜单事件: 扫码推事件且弹出“消息接收中”提示框的事件推送
     */
    MENU_SCAN_CODE_WAIT_MSG("scancode_waitmsg", "扫码推事件且弹出“消息接收中”提示框的事件推送"),
    /**
     * 自定义菜单事件: 弹出系统拍照发图的事件推送
     */
    MENU_PIC_SYS_PHOTO("pic_sysphoto", "弹出系统拍照发图的事件推送"),
    /**
     * 自定义菜单事件: 弹出拍照或者相册发图的事件推送
     */
    MENU_PIC_OR_ALBUM("pic_photo_or_album", "弹出拍照或者相册发图的事件推送"),
    /**
     * 自定义菜单事件: 弹出微信相册发图器的事件推送
     */
    MENU_PIC_WEIXIN("pic_weixin", "弹出微信相册发图器的事件推送"),
    /**
     * 自定义菜单事件: 弹出地理位置选择器的事件推送
     */
    MENU_LOCATION_SELECT("location_select", "弹出地理位置选择器的事件推送");

    private String value;
    private String description;

    EventType(String eventType, String description) {
        this.value = eventType;
        this.description = description;
    }

    public static EventType eventTypeOfEventKey(String eventKey) {
        switch (eventKey) {
            case "subscribe": return EventType.SUBSCRIBE;
            case "unsubscribe": return EventType.UNSUBSCRIE;
            case "SCAN": return EventType.SCAN;
            case "LOCATION": return EventType.LOCATION;
            case "CLICK": return EventType.MENU_CLICK;
            case "VIEW": return EventType.MENU_VIEW;
            case "scancode_push": return EventType.MENU_SCAN_CODE_PUSH;
            case "scancode_waitmsg": return EventType.MENU_SCAN_CODE_WAIT_MSG;
            case "pic_sysphoto": return EventType.MENU_PIC_SYS_PHOTO;
            case "pic_photo_or_album": return EventType.MENU_PIC_OR_ALBUM;
            case "pic_weixin": return EventType.MENU_PIC_WEIXIN;
            case "location_select": return EventType.MENU_LOCATION_SELECT;
            default: return null;
        }
    }

    public String value() {
        return this.value;
    }

    public String description() {
        return this.description;
    }
}
