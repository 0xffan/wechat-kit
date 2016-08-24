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
import com.google.gson.JsonObject;
import me.ixfan.wechatkit.common.WeChatConstants;
import me.ixfan.wechatkit.menu.model.MenuItem;
import me.ixfan.wechatkit.token.TokenManager;
import me.ixfan.wechatkit.util.HttpClientUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Function set of menu management.
 *
 * Created by xfan on 16/3/26.
 */
public class MenuManager {

    private Logger logger = LoggerFactory.getLogger(MenuManager.class);

    private TokenManager tokenManager;

    public MenuManager(TokenManager tokenManager) {
        this.tokenManager = tokenManager;
    }

    /**
     * Create customized menus.
     * @param menus {@link MenuItem}s to be created.
     */
    public boolean createCustomizedMenu(List<MenuItem> menus) {
        String menusInJson = convertMenuItemsToJsonString((MenuItem[]) menus.toArray());
        try {
            JsonObject response = HttpClientUtil.sendPostRequestWithJsonBody(
                    WeChatConstants.WECHAT_POST_MENU_CREATE_URL
                            .replace("${ACCESS_TOKEN}", this.tokenManager.getAccessToken()),
                    menusInJson);
            int wechatResponseCode = response.get(WeChatConstants.WECHAT_API_RESPONSE_KEY_ERRCODE).getAsInt();
            if (0 != wechatResponseCode) {
                logger.error("Failed to create customized menu, {}", response.toString());
                return false;
            }
        } catch (IOException e) {
            logger.error("Error occurred while creating customized menu.");
            e.printStackTrace();
            return false;
        }

        return true;
    }

    /**
     * Delete customized menus.
     */
    public boolean deleteCustomizedMenu() {
        try {
            JsonObject response = HttpClientUtil.sendGetRequestAndGetJsonResponse(
                    WeChatConstants.WECHAT_GET_MENU_DELETE_URL.replace("${ACCESS_TOKEN}",
                                                                       this.tokenManager.getAccessToken()));
            int wechatResponseCode = response.get(WeChatConstants.WECHAT_API_RESPONSE_KEY_ERRCODE).getAsInt();
            if (0 != wechatResponseCode) {
                logger.error("Failed to delete menu, {}", response.toString());
                return false;
            }
        } catch (IOException e) {
            logger.error("Error occurred while deleting customized menu.");
            e.printStackTrace();
        }

        return true;
    }

    /**
     * Serialize {@link MenuItem} array to JSON string.
     * @param menuItems
     * @return
     */
    public String convertMenuItemsToJsonString(MenuItem... menuItems) {
        if (null == menuItems || menuItems.length <= 0) {
            return "";
        }

        Map<String, Object> map = new HashMap<>();
        map.put("button", menuItems);
        return new Gson().toJson(map);
    }
}
