package me.ixfan.wechatkit.user;

import me.ixfan.wechatkit.token.WechatAccessTokenContainer;

/**
 * TODO: 用户管理
 * Created by xfan on 16/3/26.
 */
public class UserManager {

    /** 微信API - 获取公众号已创建的标签 */
    private static final String WECHAT_GET_TAGS_GET = "https://api.weixin.qq.com/cgi-bin/tags/get?access_token=${ACCESS_TOKEN}";
    /** 微信API - 创建标签 */
    private static final String WECHAT_POST_TAGS_CREATE = "https://api.weixin.qq.com/cgi-bin/tags/create?access_token=${ACCESS_TOKEN}";
    /** 微信API - 编辑标签 */
    private static final String WECHAT_POST_TAGS_UPDATE = "https://api.weixin.qq.com/cgi-bin/tags/update?access_token=${ACCESS_TOKEN}";
    /** 微信API - 删除标签 */
    private static final String WECHAT_POST_TAGS_DELETE = "https://api.weixin.qq.com/cgi-bin/tags/delete?access_token=${ACCESS_TOKEN}";
    /** 微信API - 批量为用户打标签 */
    private static final String WECHAT_POST_TAGS_BATCH_TAGGING = "https://api.weixin.qq.com/cgi-bin/tags/members/batchtagging?access_token=${ACCESS_TOKEN}";
    /** 微信API - 批量为用户取消标签 */
    private static final String WECHAT_POST_TAGS_BATCH_UNTAGGING = "https://api.weixin.qq.com/cgi-bin/tags/members/batchuntagging?access_token=${ACCESS_TOKEN}";
    /** 微信API - 获取用户身上的标签列表 */
    private static final String WECHAT_POST_TAGS_GET_TAGS_OF_USER = "https://api.weixin.qq.com/cgi-bin/tags/getidlist?access_token=${ACCESS_TOKEN}";

    /** 微信API - 获取标签下粉丝列表 */
    private static final String WECHAT_GET_USER_WITH_TAG = "https://api.weixin.qq.com/cgi-bin/user/tag/get?access_token=${ACCESS_TOKEN}";
    /** 微信API - 获取用户列表 */
    private static final String WECHAT_GET_USER_LIST = "https://api.weixin.qq.com/cgi-bin/user/get?access_token=${ACCESS_TOKEN}&next_openid=${NEXT_OPENID}";
    /** 微信API - 获取用户基本信息 */
    private static final String WECHAT_GET_USER_GET_INFO = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=${ACCESS_TOKEN}&openid=${OPENID}&lang=zh_CN";
    /** 微信API - 批量获取用户基本信息 */
    private static final String WECHAT_POST_USER_BATCH_GET_INFO = "https://api.weixin.qq.com/cgi-bin/user/info/batchget?access_token=${ACCESS_TOKEN}";
    /** 微信API - 设置用户备注名 */
    private static final String WECHAT_POST_USER_UPDATE_REMARK = "https://api.weixin.qq.com/cgi-bin/user/info/updateremark?access_token=${ACCESS_TOKEN}";

    private static UserManager userManager;
    private static WechatAccessTokenContainer accessTokenContainer;

    public static UserManager getInstance(WechatAccessTokenContainer accessTokenContainer) {
        if (null != userManager) {
            return userManager;
        }

        userManager = new UserManager(accessTokenContainer);
        return userManager;
    }

    private UserManager(WechatAccessTokenContainer accessTokenContainer) {
        this.accessTokenContainer = accessTokenContainer;
    }




}
