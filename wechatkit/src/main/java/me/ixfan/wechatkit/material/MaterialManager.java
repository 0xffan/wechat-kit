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
package me.ixfan.wechatkit.material;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import me.ixfan.wechatkit.WeChatFunctionModule;
import me.ixfan.wechatkit.common.WeChatConstants;
import me.ixfan.wechatkit.common.WechatApiResult;
import me.ixfan.wechatkit.material.model.ArticleForUpload;
import me.ixfan.wechatkit.token.TokenManager;
import me.ixfan.wechatkit.util.HttpClientUtil;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * 素材管理。
 *
 * @author Warren Fan
 */
public class MaterialManager extends WeChatFunctionModule {

    public MaterialManager(TokenManager tokenManager) {
        super(tokenManager);
    }

    /**
     * 上传临时图片素材。
     *
     * @param filename 图片文件名。
     * @param inputStream 图片文件输入源。
     * @return {@link WechatApiResult}
     */
    public WechatApiResult uploadTemporaryImage(String filename, InputStream inputStream) {
        return uploadTemporaryMaterial(MediaType.IMAGE, filename, inputStream);
    }

    /**
     * 上传临时视频素材。
     *
     * @param filename 视频文件名。
     * @param inputStream 视频文件输入源。
     * @return {@link WechatApiResult}
     */
    public WechatApiResult uploadTemporaryVideo(String filename, InputStream inputStream) {
        return uploadTemporaryMaterial(MediaType.VIDEO, filename, inputStream);
    }

    /**
     * 上传临时语音素材。
     *
     * @param filename 语音文件名。
     * @param inputStream 语音文件输入源。
     * @return {@link WechatApiResult}
     */
    public WechatApiResult uploadTemporaryVoice(String filename, InputStream inputStream) {
        return uploadTemporaryMaterial(MediaType.VOICE, filename, inputStream);
    }

    /**
     * TODO 新增永久图文素材。
     *
     * @param articles 要上传的图文信息。
     * @return {@link WechatApiResult}
     */
    public WechatApiResult uploadPermanentArticles(ArticleForUpload... articles) {
        return null;
    }

    /**
     * TODO 为图文消息上传图片。
     *
     * @param filename 图片文件名。
     * @param inputStream 图片文件输入源。
     * @return
     */
    public WechatApiResult uploadImageForArticle(String filename, InputStream inputStream) {
        return null;
    }

    /**
     * 上传临时缩略图。
     *
     * @param filename 缩略图文件名。
     * @param inputStream 缩略图文件输入源。
     * @return {@link WechatApiResult}
     */
    public WechatApiResult uploadTemporaryThumb(String filename, InputStream inputStream) {
        return uploadTemporaryMaterial(MediaType.THUMB, filename, inputStream);
    }

    /**
     * 上传永久素材, 媒体文件类型支持图片、语音、视频、缩略图。
     * 在上传视频素材时需要包含素材的描述信息, 即参数中的 <code>title</code> 和 <code>introduction</code>。
     *
     * @param mediaType 要上传的媒体文件类型。
     * @param filename 文件名。
     * @param inputStream 媒体文件输入源。
     * @param title 素材标题, 仅视频素材需要。
     * @param introduction 素材简介, 仅视频素材需要。
     * @return {@link WechatApiResult}
     */
    private WechatApiResult uploadPermanentMaterial(MediaType mediaType, String filename, InputStream inputStream, String title, String introduction) {
        Map<String, String> textPart = new HashMap<>();
        String mimeType;
        switch (mediaType) {
            case IMAGE: case THUMB:
                mimeType = "image/*"; break;
            case VIDEO:
                mimeType = "video/*";
                textPart.put("description", String.format("{\"title\":%s, \"introduction\":%s}", title, introduction));
                break;
            case VOICE:
                mimeType = "audio/*"; break;
            default: mimeType = "*/*";
        }

        Map<String, HttpClientUtil.MultipartInput> filePart = new HashMap<>();
        HttpClientUtil.MultipartInput file = new HttpClientUtil.MultipartInput(filename, mimeType, inputStream);
        filePart.put("media", file);

        try {
            JsonObject jsonResponse = HttpClientUtil.sendMultipartRequestAndGetJsonResponse(
                    WeChatConstants.WECHAT_POST_MATERIAL_UPLOAD_PERMANENT.replace("${ACCESS_TOKEN}", super.tokenManager.getAccessToken()).replace("${TYPE}", mediaType.stringValue()),
                    textPart, filePart);
            return WechatApiResult.instanceOf(jsonResponse);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 上传临时素材, 媒体文件类型支持图片、语音、视频、缩略图。
     *
     * @param mediaType 要上传的媒体文件类型。
     * @param filename 文件名
     * @param inputStream 文件源
     * @return {@link WechatApiResult}
     */
    private WechatApiResult uploadTemporaryMaterial(MediaType mediaType, String filename, InputStream inputStream)
            throws RuntimeException {
        String mimeType;
        switch (mediaType) {
            case IMAGE: case THUMB:
                mimeType = "image/*"; break;
            case VIDEO:
                mimeType = "video/*"; break;
            case VOICE:
                mimeType = "audio/*"; break;
            default: mimeType = "*/*";
        }

        Map<String, HttpClientUtil.MultipartInput> filePart = new HashMap<>();
        HttpClientUtil.MultipartInput file = new HttpClientUtil.MultipartInput(filename, mimeType, inputStream);
        filePart.put("media", file);

        try {
            JsonObject jsonResponse = HttpClientUtil.sendMultipartRequestAndGetJsonResponse(
                    WeChatConstants.WECHAT_POST_MATERIAL_UPLOAD_TEMP.replace("${ACCESS_TOKEN}", super.tokenManager.getAccessToken()).replace("${TYPE}", mediaType.stringValue()),
                    null, filePart);
            return WechatApiResult.instanceOf(jsonResponse);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
