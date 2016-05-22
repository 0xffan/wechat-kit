package me.ixfan.wechatkit.menu;

import me.ixfan.wechatkit.token.WechatAccessTokenContainer;

/**
 * Created by xfan on 16/3/26.
 */
public class MenuManager {

    private final String WECHAT_MENU_CREATE_URL_POST = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=";
    private final String WECHAT_MENU_DELETE_URL_GET = "https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=";

    private static MenuManager menuManager;
    private WechatAccessTokenContainer accessTokenContainer;

    public static MenuManager getInstance(WechatAccessTokenContainer accessTokenContainer) {
        if (null == menuManager) {
            return new MenuManager(accessTokenContainer);
        } else {
            return menuManager;
        }
    }

    public MenuManager(WechatAccessTokenContainer accessTokenContainer) {
        this.accessTokenContainer = accessTokenContainer;
    }

    public void createCustomizedMenu(String accessToken) {
        // TODO: 创建个性化微信菜单
    }

    public void deleteCustomizedMenu(String accessToken) {
        // TODO: 删除个性化微信菜单
    }
}
