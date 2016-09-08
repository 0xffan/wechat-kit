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

package me.ixfan.wechatkit;

import me.ixfan.wechatkit.material.MaterialManager;
import me.ixfan.wechatkit.menu.MenuManager;
import me.ixfan.wechatkit.message.MessageManager;
import me.ixfan.wechatkit.token.TokenManager;
import me.ixfan.wechatkit.token.WechatAccessTokenContainer;
import me.ixfan.wechatkit.user.UserManager;

/**
 * 微信开放平台功能入口.
 *
 * @author xfan
 * @since 16/5/22
 */
public class WeChatKit {

    private final String appId;
    private final String appSecret;
    private final String wechatId;

    private final TokenManager tokenManager;

    private MenuManager menuManager;
    private UserManager userManager;
    private MessageManager messageManager;
    private MaterialManager materialManager;

    private WeChatKit(Builder builder) {
        this.appId = builder.appId;
        this.appSecret = builder.appSecret;
        this.wechatId = builder.wechatId;
        this.tokenManager = new TokenManager(this.appId, this.appSecret, builder.accessTokenContainer);
    }

    public static WeChatKit build(String wechatAccountId, String appId, String appSecret, WechatAccessTokenContainer accessTokenContainer) {
        return new Builder(appId, appSecret, wechatAccountId).setAccessTokenContainer(accessTokenContainer).build();
    }


    /**
     * Builder of WeChatKit. The instance of {@link WechatAccessTokenContainer} is required.
     */
    public static class Builder {
        private final String appId;
        private final String appSecret;
        private final String wechatId;
        private WechatAccessTokenContainer accessTokenContainer;

        public Builder(String appId, String appSecret, String wechatId) {
            this.appId = appId;
            this.appSecret = appSecret;
            this.wechatId = wechatId;
        }

        public Builder setAccessTokenContainer(WechatAccessTokenContainer accessTokenContainer) {
            this.accessTokenContainer = accessTokenContainer;
            return this;
        }

        public WeChatKit build() {
            return new WeChatKit(this);
        }
    }

    /**
     * Customize menu of WeChat official accounts.
     *
     * @return Instance of ${@link MenuManager}.
     */
    public MenuManager menuManager() {
        if (null == this.menuManager) {
            this.menuManager = new MenuManager(this.tokenManager);
        }
        return this.menuManager;
    }

    /**
     * Manage WeChat users.
     *
     * @return Intance of ${@link me.ixfan.wechatkit.user.UserManager}.
     */
    public UserManager userManager() {
        if (null == this.userManager) {
            this.userManager = new UserManager(this.tokenManager);
        }
        return this.userManager;
    }

    public MessageManager messageManager() {
        if (null == this.messageManager) {
            this.messageManager = new MessageManager(this.wechatId, this.tokenManager);
        }
        return this.messageManager;
    }

    public MaterialManager materialManager() {
        if (null == this.materialManager) {
            this.materialManager = new MaterialManager(this.tokenManager);
        }
        return this.materialManager;
    }

}
