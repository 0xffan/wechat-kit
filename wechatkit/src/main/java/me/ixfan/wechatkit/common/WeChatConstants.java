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
package me.ixfan.wechatkit.common;

/**
 * @author Warren Fan
 */
public class WeChatConstants {

    /** 微信API JSON相应结果中返回码的key */
    public static final String WECHAT_API_RESPONSE_KEY_ERRCODE = "errcode";
    /** 微信API JSON相应结果中返回消息的key */
    public static final String WECHAT_API_RESPONSE_LEY_ERRMSG = "errmsg";

    /** 微信API - 获取 <code>access_token</code> */
    public static final String WECHAT_GET_OBTAIN_ACCESS_TOKEN = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=${APPID}&secret=${APPSECRET}";

    /** 微信API - 创建自定义微信菜单 */
    public static final String WECHAT_POST_MENU_CREATE_URL = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=${ACCESS_TOKEN}";
    /** 微信API - 删除自定义的微信菜单 */
    public static final String WECHAT_GET_MENU_DELETE_URL = "https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=${ACCESS_TOKEN}";

    /** 微信API - 获取公众号已创建的标签 */
    public static final String WECHAT_GET_TAGS_GET = "https://api.weixin.qq.com/cgi-bin/tags/get?access_token=${ACCESS_TOKEN}";
    /** 微信API - 创建标签 */
    public static final String WECHAT_POST_TAGS_CREATE = "https://api.weixin.qq.com/cgi-bin/tags/create?access_token=${ACCESS_TOKEN}";
    /** 微信API - 编辑标签 */
    public static final String WECHAT_POST_TAGS_UPDATE = "https://api.weixin.qq.com/cgi-bin/tags/update?access_token=${ACCESS_TOKEN}";
    /** 微信API - 删除标签 */
    public static final String WECHAT_POST_TAGS_DELETE = "https://api.weixin.qq.com/cgi-bin/tags/delete?access_token=${ACCESS_TOKEN}";
    /** 微信API - 批量为用户打标签 */
    public static final String WECHAT_POST_TAGS_BATCH_TAGGING = "https://api.weixin.qq.com/cgi-bin/tags/members/batchtagging?access_token=${ACCESS_TOKEN}";
    /** 微信API - 批量为用户取消标签 */
    public static final String WECHAT_POST_TAGS_BATCH_UNTAGGING = "https://api.weixin.qq.com/cgi-bin/tags/members/batchuntagging?access_token=${ACCESS_TOKEN}";
    /** 微信API - 获取用户身上的标签列表 */
    public static final String WECHAT_POST_TAGS_GET_TAGS_OF_USER = "https://api.weixin.qq.com/cgi-bin/tags/getidlist?access_token=${ACCESS_TOKEN}";

    /** 微信API - 获取标签下粉丝列表 */
    public static final String WECHAT_GET_USER_WITH_TAG = "https://api.weixin.qq.com/cgi-bin/user/tag/get?access_token=${ACCESS_TOKEN}";
    /** 微信API - 获取用户列表 */
    public static final String WECHAT_GET_USER_LIST = "https://api.weixin.qq.com/cgi-bin/user/get?access_token=${ACCESS_TOKEN}&next_openid=${NEXT_OPENID}";
    /** 微信API - 获取用户基本信息 */
    public static final String WECHAT_GET_USER_GET_INFO = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=${ACCESS_TOKEN}&openid=${OPENID}&lang=zh_CN";
    /** 微信API - 批量获取用户基本信息 */
    public static final String WECHAT_POST_USER_BATCH_GET_INFO = "https://api.weixin.qq.com/cgi-bin/user/info/batchget?access_token=${ACCESS_TOKEN}";
    /** 微信API - 设置用户备注名 */
    public static final String WECHAT_POST_USER_UPDATE_REMARK = "https://api.weixin.qq.com/cgi-bin/user/info/updateremark?access_token=${ACCESS_TOKEN}";

    /** 微信API - 上传临时素材 */
    public static final String WECHAT_POST_MATERIAL_UPLOAD_TEMP = "https://api.weixin.qq.com/cgi-bin/media/upload?access_token=${ACCESS_TOKEN}&type=${TYPE}";
}
