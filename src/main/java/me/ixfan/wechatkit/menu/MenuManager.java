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

package me.ixfan.wechatkit.menu;

import com.google.gson.Gson;
import me.ixfan.wechatkit.menu.model.MenuItem;
import me.ixfan.wechatkit.token.WechatAccessTokenContainer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Function set of menu management.
 *
 * Created by xfan on 16/3/26.
 */
public class MenuManager {

    /** 微信API - 创建自定义微信菜单 */
    private static final String WECHAT_POST_MENU_CREATE_URL = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=${ACCESS_TOKEN}";
    /** 微信API - 删除自定义的微信菜单 */
    private static final String WECHAT_GET_MENU_DELETE_URL = "https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=${ACCESS_TOKEN}";

    private static MenuManager menuManager;
    private static WechatAccessTokenContainer accessTokenContainer;

    public static MenuManager getInstance(WechatAccessTokenContainer accessTokenContainer) {
        if (null != menuManager) {
            return menuManager;
        }

        menuManager = new MenuManager(accessTokenContainer);
        return menuManager;
    }

    private MenuManager(WechatAccessTokenContainer accessTokenContainer) {
        this.accessTokenContainer = accessTokenContainer;
    }

    public void createCustomizedMenu(List<MenuItem> menu) {
        // TODO: 创建个性化微信菜单
    }

    public void deleteCustomizedMenu() {
        // TODO: 删除个性化微信菜单
    }

    public String convertMenuItemstoJsonString(MenuItem... menuItems) {
        if (null == menuItems || menuItems.length <= 0) {
            return "";
        }

        Map<String, Object> map = new HashMap<>();
        map.put("button", menuItems);
        return new Gson().toJson(map);
    }
}
