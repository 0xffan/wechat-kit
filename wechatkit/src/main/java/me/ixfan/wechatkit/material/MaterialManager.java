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

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import me.ixfan.wechatkit.WeChatKitComponent;
import me.ixfan.wechatkit.common.WeChatConstants;
import me.ixfan.wechatkit.common.WechatApiResult;
import me.ixfan.wechatkit.material.model.ArticleForUpload;
import me.ixfan.wechatkit.token.TokenManager;
import me.ixfan.wechatkit.util.HttpClientUtil;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * 素材管理。
 *
 * @author Warren Fan
 */
public class MaterialManager extends WeChatKitComponent {

    public MaterialManager(TokenManager tokenManager) {
        super(tokenManager);
    }

    /**
     * 上传临时图片素材。
     *
     * @param filename 图片文件名。
     * @param dataBytes 图片文件字节流。
     * @return 上传成功后通过 {@link WechatApiResult#getMediaId()} 和 {@link WechatApiResult#getType()}
     * 可以得到图片的 <code>media_id</code> 和 <code>type</code>；
     * 若上传失败，可以通过 {@link WechatApiResult#getErrcode()} 和 {@link WechatApiResult#getErrmsg()}
     * 得到错误代码和错误信息。
     */
    public WechatApiResult uploadTemporaryImage(String filename, byte[] dataBytes) {
        return uploadTemporaryMaterial(MediaType.IMAGE, filename, dataBytes, null);
    }

    /**
     * 上传临时图片素材。
     *
     * @param image 图片文件
     * @return 上传成功后通过 {@link WechatApiResult#getMediaId()} 和 {@link WechatApiResult#getType()}
     * 可以得到图片的 <code>media_id</code> 和 <code>type</code>；
     * 若上传失败，可以通过 {@link WechatApiResult#getErrcode()} 和 {@link WechatApiResult#getErrmsg()}
     * 得到错误代码和错误信息。
     */
    public WechatApiResult uploadTemporaryImage(File image) {
        return uploadTemporaryMaterial(MediaType.IMAGE, image.getName(), null, image);
    }

    /**
     * 上传临时视频素材。
     *
     * @param filename 视频文件名。
     * @param dataBytes 视频文件字节流。
     * @return 上传成功后通过 {@link WechatApiResult#getMediaId()} 和 {@link WechatApiResult#getType()}
     * 可以得到视频的 <code>media_id</code> 和 <code>type</code>；
     * 若上传失败，可以通过 {@link WechatApiResult#getErrcode()} 和 {@link WechatApiResult#getErrmsg()}
     * 得到错误代码和错误信息。
     */
    public WechatApiResult uploadTemporaryVideo(String filename, byte[] dataBytes) {
        return uploadTemporaryMaterial(MediaType.VIDEO, filename, dataBytes, null);
    }

    /**
     * 上传临时视频素材。
     *
     * @param video 视频文件。
     * @return 上传成功后通过 {@link WechatApiResult#getMediaId()} 和 {@link WechatApiResult#getType()}
     * 可以得到视频的 <code>media_id</code> 和 <code>type</code>；
     * 若上传失败，可以通过 {@link WechatApiResult#getErrcode()} 和 {@link WechatApiResult#getErrmsg()}
     * 得到错误代码和错误信息。
     */
    public WechatApiResult uploadTemporaryVideo(File video) {
        return uploadTemporaryMaterial(MediaType.VIDEO, video.getName(), null, video);
    }

    /**
     * 上传临时语音素材。
     *
     * @param filename 语音文件名。
     * @param dataBytes 语音文件字节流。
     * @return 上传成功后通过 {@link WechatApiResult#getMediaId()} 和 {@link WechatApiResult#getType()}
     * 可以得到语音素材的 <code>media_id</code> 和 <code>type</code>；
     * 若上传失败，可以通过 {@link WechatApiResult#getErrcode()} 和 {@link WechatApiResult#getErrmsg()}
     * 得到错误代码和错误信息。
     */
    public WechatApiResult uploadTemporaryVoice(String filename, byte[] dataBytes) {
        return uploadTemporaryMaterial(MediaType.VOICE, filename, dataBytes, null);
    }

    /**
     * 上传临时语音素材。
     *
     * @param voice 语音文件。
     * @return 上传成功后通过 {@link WechatApiResult#getMediaId()} 和 {@link WechatApiResult#getType()}
     * 可以得到语音素材的 <code>media_id</code> 和 <code>type</code>；
     * 若上传失败，可以通过 {@link WechatApiResult#getErrcode()} 和 {@link WechatApiResult#getErrmsg()}
     * 得到错误代码和错误信息。
     */
    public WechatApiResult uploadTemporaryVoice(File voice) {
        return uploadTemporaryMaterial(MediaType.VOICE, voice.getName(), null, voice);
    }

    /**
     * 上传临时缩略图。
     *
     * @param filename 缩略图文件名。
     * @param dataBytes 缩略图文件字节流。
     * @return 上传成功后通过 {@link WechatApiResult#getMediaId()} 和 {@link WechatApiResult#getType()}
     * 可以得到缩略图的 <code>media_id</code> 和 <code>type</code>；
     * 若上传失败，可以通过 {@link WechatApiResult#getErrcode()} 和 {@link WechatApiResult#getErrmsg()}
     * 得到错误代码和错误信息。
     */
    public WechatApiResult uploadTemporaryThumb(String filename, byte[] dataBytes) {
        return uploadTemporaryMaterial(MediaType.THUMB, filename, dataBytes, null);
    }

    /**
     * 上传临时缩略图。
     *
     * @param thumb 缩略图文件。
     * @return 上传成功后通过 {@link WechatApiResult#getMediaId()} 和 {@link WechatApiResult#getType()}
     * 可以得到缩略图的 <code>media_id</code> 和 <code>type</code>；
     * 若上传失败，可以通过 {@link WechatApiResult#getErrcode()} 和 {@link WechatApiResult#getErrmsg()}
     * 得到错误代码和错误信息。
     */
    public WechatApiResult uploadTemporaryThumb(File thumb) {
        return uploadTemporaryMaterial(MediaType.THUMB, thumb.getName(), null, thumb);
    }

    /**
     * 新增永久图文素材。
     *
     * @param articles 要上传的图文信息。
     * @return 上传成功后通过 {@link WechatApiResult#getMediaId()} 可以得到图文素材的 <code>media_id</code>；
     * 若上传失败，可以通过 {@link WechatApiResult#getErrcode()} 和 {@link WechatApiResult#getErrmsg()}
     * 得到错误代码和错误信息。
     */
    public WechatApiResult uploadPermanentArticles(ArticleForUpload... articles) {
        Map<String, ArticleForUpload[]> data = new HashMap<>();
        data.put("articles", articles);
        try {
            Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
            JsonObject jsonResponse = HttpClientUtil.sendPostRequestWithJsonBody(WeChatConstants.WECHAT_POST_MATERIAL_UPLOAD_PERMANENT_NEWS.replace("${ACCESS_TOKEN}", super.getTokenManager().getAccessToken()), gson.toJson(data));
            return WechatApiResult.instanceOf(jsonResponse);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 为图文消息上传图片。
     *
     * @param filename 图片文件名。
     * @param dataBytes 图片文件字节流。
     * @return 上传成功后通过 {@link WechatApiResult#getUrl()} 可以得到图片的 <code>URL</code>；
     * 若上传失败，可以通过 {@link WechatApiResult#getErrcode()} 和 {@link WechatApiResult#getErrmsg()}
     * 得到错误代码和错误信息。
     */
    public WechatApiResult uploadImageForArticle(String filename, byte[] dataBytes) {
        return uploadImageForArticle(filename, dataBytes, null);
    }

    /**
     * 为图文消息上传图片。
     *
     * @param image 图片文件。
     * @return 上传成功后通过 {@link WechatApiResult#getUrl()} 可以得到图片的 <code>URL</code>；
     * 若上传失败，可以通过 {@link WechatApiResult#getErrcode()} 和 {@link WechatApiResult#getErrmsg()}
     * 得到错误代码和错误信息。
     */
    public WechatApiResult uploadImageForArticle(File image) {
        return uploadImageForArticle(image.getName(), null, image);
    }

    /**
     * 新增永久图片素材。
     *
     * @param filename　图片文件名
     * @param dataBytes　图片文件字节流
     * @return 上传成功后通过 {@link WechatApiResult#getMediaId()} 和 {@link WechatApiResult#getUrl()}
     * 可以得到图片的 <code>media_id</code> 和 <code>URL</code>；
     * 若上传失败，可以通过 {@link WechatApiResult#getErrcode()} 和 {@link WechatApiResult#getErrmsg()}
     * 得到错误代码和错误信息。
     *
     */
    public WechatApiResult uploadPermanentImage(String filename, byte[] dataBytes) {
        return uploadPermanentMaterial(MediaType.IMAGE, filename, dataBytes, null, null, null);
    }

    /**
     * 新增永久图片素材。
     *
     * @param image　图片文件
     * @return 上传成功后通过 {@link WechatApiResult#getMediaId()} 和 {@link WechatApiResult#getUrl()}
     * 可以得到图片的 <code>media_id</code> 和 <code>URL</code>；
     * 若上传失败，可以通过 {@link WechatApiResult#getErrcode()} 和 {@link WechatApiResult#getErrmsg()}
     * 得到错误代码和错误信息。
     *
     */
    public WechatApiResult uploadPermanentImage(File image) {
        return uploadPermanentMaterial(MediaType.IMAGE, image.getName(), null, image, null, null);
    }

    /**
     * 新增永久语音素材。
     *
     * @param filename 语音素材文件名
     * @param dataBytes 语音文件字节流
     * @return 上传成功后通过 {@link WechatApiResult#getMediaId()} 可以得到语音素材的 <code>media_id</code>；
     * 若上传失败，可以通过 {@link WechatApiResult#getErrcode()} 和 {@link WechatApiResult#getErrmsg()}
     * 得到错误代码和错误信息。
     */
    public WechatApiResult uploadPermanentVoice(String filename, byte[] dataBytes) {
        return uploadPermanentMaterial(MediaType.VOICE, filename, dataBytes, null, null, null);
    }

    /**
     * 新增永久语音素材。
     *
     * @param voice 语音文件
     * @return 上传成功后通过 {@link WechatApiResult#getMediaId()} 可以得到语音素材的 <code>media_id</code>；
     * 若上传失败，可以通过 {@link WechatApiResult#getErrcode()} 和 {@link WechatApiResult#getErrmsg()}
     * 得到错误代码和错误信息。
     */
    public WechatApiResult uploadPermanentVoice(File voice) {
        return uploadPermanentMaterial(MediaType.VOICE, voice.getName(), null, voice, null, null);
    }

    /**
     * 新增永久视频素材。
     *
     * @param filename 视频文件名
     * @param title 视频素材标题
     * @param introduction 视频素材简介
     * @param dataBytes 视频文件字节流
     * @return 上传成功后通过 {@link WechatApiResult#getMediaId()} 可以得到视频素材的 <code>media_id</code>；
     * 若上传失败，可以通过 {@link WechatApiResult#getErrcode()} 和 {@link WechatApiResult#getErrmsg()}
     * 得到错误代码和错误信息。
     */
    public WechatApiResult uploadPermanentVideo(String filename, String title, String introduction, byte[] dataBytes) {
        return uploadPermanentMaterial(MediaType.VIDEO, filename, dataBytes, null, title, introduction);
    }

    /**
     * 新增永久视频素材。
     *
     * @param title 视频素材标题
     * @param introduction 视频素材简介
     * @param video 视频文件
     * @return 上传成功后通过 {@link WechatApiResult#getMediaId()} 可以得到视频素材的 <code>media_id</code>；
     * 若上传失败，可以通过 {@link WechatApiResult#getErrcode()} 和 {@link WechatApiResult#getErrmsg()}
     * 得到错误代码和错误信息。
     */
    public WechatApiResult uploadPermanentVideo(String title, String introduction, File video) {
        return uploadPermanentMaterial(MediaType.VIDEO, video.getName(), null, video, title, introduction);
    }

    /**
     * 新增永久缩略图素材。
     *
     * @param dataBytes 缩略图文件
     * @return 上传成功后通过 {@link WechatApiResult#getMediaId()} 可以得到缩略图的 <code>media_id</code>；
     * 若上传失败，可以通过 {@link WechatApiResult#getErrcode()} 和 {@link WechatApiResult#getErrmsg()}
     * 得到错误代码和错误信息。
     */
    public WechatApiResult uploadPermanentThumb(String filename, byte[] dataBytes) {
        return uploadPermanentMaterial(MediaType.THUMB, filename, dataBytes, null, null, null);
    }

    /**
     * 新增永久缩略图素材。
     *
     * @param thumb 缩略图文件
     * @return 上传成功后通过 {@link WechatApiResult#getMediaId()} 可以得到缩略图的 <code>media_id</code>；
     * 若上传失败，可以通过 {@link WechatApiResult#getErrcode()} 和 {@link WechatApiResult#getErrmsg()}
     * 得到错误代码和错误信息。
     */
    public WechatApiResult uploadPermanentThumb(File thumb) {
        return uploadPermanentMaterial(MediaType.THUMB, thumb.getName(), null, thumb, null, null);
    }

    /**
     * 上传永久素材, 包括图片、语音、视频、缩略图，上传图文素材请使用{@link #uploadPermanentArticles(ArticleForUpload...)}。
     * 在上传视频素材时需要包含素材的描述信息, 即参数中的 <code>title</code> 和 <code>introduction</code>。
     *
     * @param mediaType 要上传的媒体文件类型。
     * @param filename 文件名。
     * @param dataBytes 媒体文件字节流。
     * @param file 媒体文件。
     * @param title 素材标题, 仅视频素材需要。
     * @param introduction 素材简介, 仅视频素材需要。
     * @return {@link WechatApiResult}
     */
    private WechatApiResult uploadPermanentMaterial(MediaType mediaType, String filename, byte[] dataBytes, File file, String title, String introduction) {
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
        HttpClientUtil.MultipartInput multipart;
        multipart = null == file ? new HttpClientUtil.MultipartInput(filename, mimeType, dataBytes):new HttpClientUtil.MultipartInput(filename, mimeType, file);
        filePart.put("media", multipart);

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
     * 上传临时素材, 包括图片、语音、视频、缩略图。
     *
     * @param mediaType 要上传的媒体文件类型。
     * @param filename 文件名
     * @param dataBytes 文件字节流
     * @return {@link WechatApiResult}
     */
    private WechatApiResult uploadTemporaryMaterial(MediaType mediaType, String filename, byte[] dataBytes, File file)
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
        HttpClientUtil.MultipartInput multipart;
        multipart = null == dataBytes ? new HttpClientUtil.MultipartInput(filename, mimeType, file) : new HttpClientUtil.MultipartInput(filename, mimeType, dataBytes);
        filePart.put("media", multipart);

        try {
            JsonObject jsonResponse = HttpClientUtil.sendMultipartRequestAndGetJsonResponse(
                    WeChatConstants.WECHAT_POST_MATERIAL_UPLOAD_TEMP.replace("${ACCESS_TOKEN}", super.tokenManager.getAccessToken()).replace("${TYPE}", mediaType.stringValue()),
                    null, filePart);
            return WechatApiResult.instanceOf(jsonResponse);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 为图文消息上传图片。
     *
     * @param filename 图片文件名。
     * @param image 图片文件。
     * @param dataBytes 图片字节流。
     * @return
     */
    private WechatApiResult uploadImageForArticle(String filename, byte[] dataBytes, File image) {
        Map<String, HttpClientUtil.MultipartInput> filePart = new HashMap<>();
        HttpClientUtil.MultipartInput multipart;
        multipart = null == image ? new HttpClientUtil.MultipartInput(filename, "image/*", dataBytes):new HttpClientUtil.MultipartInput(filename, "image/*", image);
        filePart.put("media", multipart);

        try {
            JsonObject jsonResponse = HttpClientUtil.sendMultipartRequestAndGetJsonResponse(
                    WeChatConstants.WECHAT_POST_MATERIAL_UPLOAD_NEWS_PIC.replace("${ACCESS_TOKEN}", super.tokenManager.getAccessToken()),
                    null, filePart);
            return WechatApiResult.instanceOf(jsonResponse);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
