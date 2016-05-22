package me.ixfan.wechatkit;

import me.ixfan.wechatkit.menu.MenuManager;
import me.ixfan.wechatkit.token.WechatAccessTokenContainer;

/**
 * 微信开放平台功能入口.
 *
 * Created by xfan on 16/5/22.
 */
public class WechatKit {

    private String appId;
    private String appSecret;
    private WechatAccessTokenContainer accessTokenContainer;

    public WechatKit(String appId, String appSecret, WechatAccessTokenContainer accessTokenContainer) {
        this.appId = appId;
        this.appSecret = appSecret;
        this.accessTokenContainer = accessTokenContainer;
    }

    public MenuManager menuManager() {
        return MenuManager.getInstance(accessTokenContainer);
    }
}
