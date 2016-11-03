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

package me.ixfan.wechatkit.user;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import me.ixfan.wechatkit.WeChatKitComponent;
import me.ixfan.wechatkit.common.WeChatConstants;
import me.ixfan.wechatkit.exceptions.WeChatApiErrorException;
import me.ixfan.wechatkit.token.TokenManager;
import me.ixfan.wechatkit.user.model.UserTag;
import me.ixfan.wechatkit.user.model.WeChatFollower;
import me.ixfan.wechatkit.util.HttpClientUtil;
import org.apache.http.util.Args;
import org.apache.http.util.TextUtils;

import java.io.IOException;
import java.util.*;

/**
 * 用户管理
 *
 * Created by xfan on 16/3/26.
 */
public class UserManager extends WeChatKitComponent {

    public UserManager(TokenManager tokenManager) {
        super(tokenManager);
    }

    /**
     * 获取关注者列表。关注者列表由一串 OpenID（加密后的微信号，每个用户对每个公众号的OpenID是唯一的）组成。一次拉取调用最多拉取10000个关注者的 OpenID，可以通过多次拉取的方式来满足需求。
     *
     * @param nextOpenId 第一个拉取的OPENID，不填默认从头开始拉取。
     * @return 关注者的 OpenId 数组，最后一个为 <code>next_openid</code>。
     * @throws WeChatApiErrorException 如果微信API调用失败，返回了错误码和错误信息，会抛出此异常。
     */
    public String[] getFollowerList(String nextOpenId) throws WeChatApiErrorException {
        JsonObject jsonResp;
        try {
            final String url = WeChatConstants.WECHAT_GET_USER_LIST
                                .replace("${ACCESS_TOKEN}", super.tokenManager.getAccessToken());
            jsonResp = HttpClientUtil.sendGetRequestAndGetJsonResponse(url.replace("${NEXT_OPENID}", null != nextOpenId ? nextOpenId:""));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (jsonResp.has("count") && jsonResp.get("count").getAsInt() > 0) {
            ArrayList<String> openids = new ArrayList<>();
            jsonResp.get("data").getAsJsonObject()
                    .get("openid").getAsJsonArray()
                    .forEach(e -> openids.add(e.getAsString()));
            if (jsonResp.has("next_openid")) {
                openids.add(jsonResp.get("next_openid").getAsString());
            }
            return openids.toArray(new String[0]);
        } else {
            throw new WeChatApiErrorException(jsonResp.get("errcode").getAsInt(), jsonResp.get("errmsg").getAsString());
        }
    }

    /**
     * 获取微信用户基本信息（包括 UnionId 机制）。同一用户，对同一个微信开放平台下的不同应用，UnionId 是相同的。
     *
     * @param openId 用户的 OpenId。
     * @param lang 返回国家地区语言版本，zh_CN 简体，zh_TW 繁体，en 英语。默认为 zh_CN。
     * @return {@link WeChatFollower}
     * @throws WeChatApiErrorException 如果微信API调用失败，返回了错误码和错误信息，会抛出此异常。
     */
    public WeChatFollower getUserInfo(final String openId, String lang) throws WeChatApiErrorException {
        Args.notBlank(openId, "OpenId");
        if (TextUtils.isBlank(lang)) {
            lang = "zh_CN";
        }
        Args.check(lang.equals("zh_CN") || lang.equals("zh_TW") || lang.equals("en"), "'lang' 的值只能为 zh_CN, zh_TW 或 en.");

        final String url = WeChatConstants.WECHAT_GET_USER_INFO.replace("${ACCESS_TOKEN}", super.tokenManager.getAccessToken())
                                            .replace("${OPENID}", openId)
                                            .replace("${LANG}", lang);
        JsonObject jsonResponse;
        try {
            jsonResponse = HttpClientUtil.sendGetRequestAndGetJsonResponse(url);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (jsonResponse.has("subscribe")) {
            return WeChatFollower.fromJson(jsonResponse);
        } else {
            throw new WeChatApiErrorException(jsonResponse.get("errcode").getAsInt(), jsonResponse.get("errmsg").getAsString());
        }
    }

    /**
     * 批量获取用户信息，最多支持一次拉取100条。
     * @param openIds 用户的 OpenId 列表。
     * @param lang 国家地区语言版本，zh_CN 简体，zh_TW 繁体，en 英语，默认为 zh-CN。
     * @return {@link WeChatFollower}s
     * @throws WeChatApiErrorException 如果微信API调用失败，返回了错误码和错误信息，会抛出此异常。
     */
    public List<WeChatFollower> batchGetUserInfo(final List<String> openIds, String lang)
            throws WeChatApiErrorException {
        Args.notEmpty(openIds, "OpenIds");
        if (TextUtils.isBlank(lang)) {
            lang = "zh_CN";
        }
        Args.check(lang.equals("zh_CN") || lang.equals("zh_TW") || lang.equals("en"), "'lang' 的值只能为 zh_CN, zh_TW 或 en.");

        final String finalLang = lang;
        JsonObject jsonResponse;
        try {
            final List<Map<String, String>> data = new ArrayList<>();
            openIds.forEach(openid -> {
                Map<String, String> map = new HashMap<>();
                map.put("openid", openid);
                map.put("lang", finalLang);
                data.add(map);
            });
            final Map<String, List<Map<String, String>>> jsonMap = new HashMap<>();
            jsonMap.put("user_list", data);
            Gson gson = new Gson();
            jsonResponse = HttpClientUtil.sendPostRequestWithJsonBody(WeChatConstants.WECHAT_POST_BATCH_GET_USER_INFO.replace("${ACCESS_TOKEN}", super.tokenManager.getAccessToken()), gson.toJson(jsonMap));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (jsonResponse.has("user_info_list")) {
            return WeChatFollower.batchFromJson(jsonResponse.get("user_info_list").getAsJsonArray());
        } else {
            throw new WeChatApiErrorException(jsonResponse.get("errcode").getAsInt(), jsonResponse.get("errmsg").getAsString());
        }
    }

    /**
     * 创建标签。
     *
     * @param tagName 标签名。30个字符以内。
     * @return {@link UserTag} 创建的标签。
     * @throws WeChatApiErrorException 如果微信API调用失败，返回了错误码和错误信息，会抛出此异常。
     */
    public UserTag createTag(String tagName) throws WeChatApiErrorException {
        Args.notEmpty(tagName, "Tag name");
        final String url = WeChatConstants.WECHAT_POST_CREATE_TAG.replace("${ACCESS_TOKEN}", super.tokenManager.getAccessToken());
        final String jsonData = "{\"tag\":{\"name\":\"" + tagName + "\"}}";
        JsonObject jsonResponse;
        try {
            jsonResponse = HttpClientUtil.sendPostRequestWithJsonBody(url, jsonData);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (jsonResponse.has("tag")) {
            return new UserTag(jsonResponse.getAsJsonObject("tag").get("id").getAsInt(), jsonResponse.getAsJsonObject("tag").get("name").getAsString());
        } else {
            throw new WeChatApiErrorException(jsonResponse.get("errcode").getAsInt(), jsonResponse.get("errmsg").getAsString());
        }
    }

    /**
     * 获取已创建的标签。
     *
     * @return 已有的标签列表。
     * @throws WeChatApiErrorException 如果微信API调用失败，返回了错误码和错误信息，会抛出此异常。
     */
    public List<UserTag> getExistingTags() throws WeChatApiErrorException {
        final String url = WeChatConstants.WECHAT_GET_GET_TAGS.replace("${ACCESS_TOKEN}", super.tokenManager.getAccessToken());
        JsonObject jsonResp;
        try {
            jsonResp = HttpClientUtil.sendGetRequestAndGetJsonResponse(url);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (jsonResp.has("tags")) {
            Gson gson = new Gson();
            return Arrays.asList(gson.fromJson(jsonResp.getAsJsonArray("tags"), UserTag[].class));
        } else {
            throw new WeChatApiErrorException(jsonResp.get("errcode").getAsInt(), jsonResp.get("errmsg").getAsString());
        }
    }

    /**
     * 更新标签。
     *
     * @param tagId 标签ID。
     * @param tagName 新的标签名。
     * @throws WeChatApiErrorException 如果微信API调用失败，返回了错误码和错误信息，会抛出此异常。
     */
    public void updateTag(int tagId, String tagName) throws WeChatApiErrorException {
        Args.notNegative(tagId, "Tag ID");
        Args.notEmpty(tagName, "Tag name");

        final String url = WeChatConstants.WECHAT_POST_UPDATE_TAG.replace("${ACCESS_TOKEN}", super.tokenManager.getAccessToken());
        final String jsonData = "{\"tag\":{\"id\":" + String.valueOf(tagId) + ",\"name\":\"" + tagName + "\"}}";
        JsonObject jsonResp;
        try {
            jsonResp = HttpClientUtil.sendPostRequestWithJsonBody(url, jsonData);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (0 != jsonResp.get("errcode").getAsInt()) {
            throw new WeChatApiErrorException(jsonResp.get("errcode").getAsInt(), jsonResp.get("errmsg").getAsString());
        }
    }

    /**
     * 删除标签。
     *
     * @param tagId 标签ID。
     * @throws WeChatApiErrorException 如果微信API调用失败，返回了错误码和错误信息，会抛出此异常。
     */
    public void deleteTag(int tagId) throws WeChatApiErrorException {
        Args.notNegative(tagId, "Tag ID");

        final String url = WeChatConstants.WECHAT_POST_DELETE_TAG.replace("${ACCESS_TOKEN}", super.tokenManager.getAccessToken());
        final String jsonData = "{\"tag\":{\"id\":" + String.valueOf(tagId) + "}}";
        JsonObject jsonResp;
        try {
            jsonResp = HttpClientUtil.sendPostRequestWithJsonBody(url, jsonData);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (0 != jsonResp.get("errcode").getAsInt()) {
            throw new WeChatApiErrorException(jsonResp.get("errcode").getAsInt(), jsonResp.get("errmsg").getAsString());
        }
    }

}
