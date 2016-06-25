package me.ixfan.wechatkit;

import me.ixfan.wechatkit.menu.MenuManager;
import me.ixfan.wechatkit.token.WechatAccessTokenContainer;
import me.ixfan.wechatkit.user.UserManager;

/**
 * 微信开放平台功能入口.
 *
 * Created by xfan on 16/5/22.
 */
public class WechatKit {

    private final String appId;
    private final String appSecret;
    public WechatAccessTokenContainer accessTokenContainer;

    public WechatKit(Builder builder) {
        this.appId = builder.appId;
        this.appSecret = builder.appSecret;
        this.accessTokenContainer = builder.accessTokenContainer;
    }

    public static class Builder {
        private final String appId;
        private final String appSecret;
        private WechatAccessTokenContainer accessTokenContainer;

        public Builder(String appId, String appSecret) {
            this.appId = appId;
            this.appSecret = appSecret;
        }

        public Builder setAccessTokenContainer(WechatAccessTokenContainer accessTokenContainer) {
            this.accessTokenContainer = accessTokenContainer;
            return this;
        }

        public WechatKit build() {
            return new WechatKit(this);
        }
    }

    public MenuManager menuManager() {
        return MenuManager.getInstance(accessTokenContainer);
    }

    public UserManager userManager() {
        return UserManager.getInstance(accessTokenContainer);
    }

}
