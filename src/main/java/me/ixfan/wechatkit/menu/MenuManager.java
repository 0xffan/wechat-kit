package me.ixfan.wechatkit.menu;

import me.ixfan.wechatkit.token.WechatAccessTokenContainer;

/**
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

    public void createCustomizedMenu() {
        // TODO: 创建个性化微信菜单
    }

    public void deleteCustomizedMenu() {
        // TODO: 删除个性化微信菜单
    }
}
