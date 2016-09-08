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
package me.ixfan.wechatkit.message.out.xml;

import me.ixfan.wechatkit.WeChatKit;
import me.ixfan.wechatkit.material.MediaObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

/**
 * @author Warren Fan
 */
@RunWith(JUnit4.class)
public class GenerateResponseXmlMsgTest {

    private final String expectedTextMsg  = "<xml><ToUserName><![CDATA[toUser]]></ToUserName><FromUserName><![CDATA[fromUser]]></FromUserName><CreateTime>12345678</CreateTime><MsgType><![CDATA[text]]></MsgType><Content><![CDATA[你好]]></Content></xml>";
    private final String expectedImageMsg = "<xml><ToUserName><![CDATA[toUser]]></ToUserName><FromUserName><![CDATA[fromUser]]></FromUserName><CreateTime>12345678</CreateTime><MsgType><![CDATA[image]]></MsgType><Image><MediaId><![CDATA[media_id]]></MediaId></Image></xml>";
    private final String expectedVoiceMsg = "<xml><ToUserName><![CDATA[toUser]]></ToUserName><FromUserName><![CDATA[fromUser]]></FromUserName><CreateTime>12345678</CreateTime><MsgType><![CDATA[voice]]></MsgType><Voice><MediaId><![CDATA[media_id]]></MediaId></Voice></xml>";
    private final String expectedVideoMsg = "<xml><ToUserName><![CDATA[toUser]]></ToUserName><FromUserName><![CDATA[fromUser]]></FromUserName><CreateTime>12345678</CreateTime><MsgType><![CDATA[video]]></MsgType><Video><MediaId><![CDATA[media_id]]></MediaId><Title><![CDATA[title]]></Title><Description><![CDATA[description]]></Description></Video></xml>";
    private final String expectedMusicMsg = "<xml><ToUserName><![CDATA[toUser]]></ToUserName><FromUserName><![CDATA[fromUser]]></FromUserName><CreateTime>12345678</CreateTime><MsgType><![CDATA[music]]></MsgType><Music><Title><![CDATA[TITLE]]></Title><Description><![CDATA[DESCRIPTION]]></Description><MusicUrl><![CDATA[MUSIC_Url]]></MusicUrl><HQMusicUrl><![CDATA[HQ_MUSIC_Url]]></HQMusicUrl><ThumbMediaId><![CDATA[media_id]]></ThumbMediaId></Music></xml>";
    private final String expectedNewsMsg  = "<xml><ToUserName><![CDATA[toUser]]></ToUserName><FromUserName><![CDATA[fromUser]]></FromUserName><CreateTime>12345678</CreateTime><MsgType><![CDATA[news]]></MsgType><ArticleCount>2</ArticleCount><Articles><item><Title><![CDATA[title1]]></Title><Description><![CDATA[description1]]></Description><PicUrl><![CDATA[picurl]]></PicUrl><Url><![CDATA[url]]></Url></item><item><Title><![CDATA[title]]></Title><Description><![CDATA[description]]></Description><PicUrl><![CDATA[picurl]]></PicUrl><Url><![CDATA[url]]></Url></item></Articles></xml>";
    private WeChatKit weChatKit = WeChatKit.build("fromUser", "appid", "appsecret", null);

    @Test
    public void generatedCorrectTextResponseMessageInXml() {
        String xmlMsg = weChatKit.messageManager().generateTextResponseMessageInXml("toUser", "你好");
        assertEquals("Generate wrong XML text message.", expectedTextMsg, xmlMsg.replaceAll("<CreateTime>\\d+</CreateTime>", "<CreateTime>12345678</CreateTime>"));
    }

    @Test
    public void generatedCorrectTextResponseMessageInXml_Object() {
        ResponseTextMsg textMsg = new ResponseTextMsg("fromUser", "toUser", 12345678L, "你好");

        String xmlMsg = weChatKit.messageManager().generateResponseMessageInXml(textMsg);
        assertEquals("Generate wrong XML text message.", expectedTextMsg, xmlMsg);
    }

    @Test
    public void generatedCorrectImageResponseMessageInXml() {
        String xmlMsg = weChatKit.messageManager().generateImageResponseMessageInXml("toUser", "media_id");
        assertEquals("Generate wrong XML text message.", expectedImageMsg, xmlMsg.replaceAll("<CreateTime>\\d+</CreateTime>", "<CreateTime>12345678</CreateTime>"));
    }

    @Test
    public void generatedCorrectImageResponseMessageInXml_Object() {
        ResponseImageMsg imageMsg = new ResponseImageMsg("fromUser", "toUser", 12345678L);
        imageMsg.setMediaImage(new MediaObject("media_id"));

        String xmlMsg = weChatKit.messageManager().generateResponseMessageInXml(imageMsg);
        assertEquals("Generate wrong XML text message.", expectedImageMsg, xmlMsg);
    }

    @Test
    public void generatedCorrectVoiceResponseMessageInXml() {
        String xmlMsg = weChatKit.messageManager().generateVoiceResponseMessageInXml("toUser", "media_id");
        assertEquals("Generate wrong XML text message.", expectedVoiceMsg, xmlMsg.replaceAll("<CreateTime>\\d+</CreateTime>", "<CreateTime>12345678</CreateTime>"));
    }

    @Test
    public void generatedCorrectVoiceResponseMessageInXml_Object() {
        ResponseVoiceMsg voiceMsg = new ResponseVoiceMsg("fromUser", "toUser", 12345678L);
        voiceMsg.setMediaVoice(new MediaObject("media_id"));

        String xmlMsg = weChatKit.messageManager().generateResponseMessageInXml(voiceMsg);
        assertEquals("Generate wrong XML text message.", expectedVoiceMsg, xmlMsg);
    }

    @Test
    public void generatedCorrectVideoResponseMessageInXml() {
        String xmlMsg = weChatKit.messageManager().generateVideoResponseMessageInXml("toUser", "title", "description", "media_id");
        assertEquals("Generate wrong XML text message.", expectedVideoMsg, xmlMsg.replaceAll("<CreateTime>\\d+</CreateTime>", "<CreateTime>12345678</CreateTime>"));
    }

    @Test
    public void generatedCorrectVideoResponseMessageInXml_Object() {
        ResponseVideoMsg videoMsg = new ResponseVideoMsg("fromUser", "toUser", 12345678L);
        Video video = new Video("title", "description", "media_id");
        videoMsg.setVideo(video);

        String xmlMsg = weChatKit.messageManager().generateResponseMessageInXml(videoMsg);
        assertEquals("Generate wrong XML text message.", expectedVideoMsg, xmlMsg);
    }

    @Test
    public void generatedCorrectMusicResponseMessageInXml() {
        Music music = new Music("TITLE", "DESCRIPTION", "MUSIC_Url", "media_id");
        music.setHqMusicUrl("HQ_MUSIC_Url");

        String xmlMsg = weChatKit.messageManager().generateMusicResponseMessageInXml("toUser", music);
        assertEquals("Generate wrong XML text message.", expectedMusicMsg, xmlMsg.replaceAll("<CreateTime>\\d+</CreateTime>", "<CreateTime>12345678</CreateTime>"));
    }

    @Test
    public void generatedCorrectMusicResponseMessageInXml_Object() {
        Music music = new Music("TITLE", "DESCRIPTION", "MUSIC_Url", "media_id");
        music.setHqMusicUrl("HQ_MUSIC_Url");
        ResponseMusicMsg musicMsg = new ResponseMusicMsg("fromUser", "toUser", 12345678L);
        musicMsg.setMusic(music);

        String xmlMsg = weChatKit.messageManager().generateResponseMessageInXml(musicMsg);
        assertEquals("Generate wrong XML text message.", expectedMusicMsg, xmlMsg);
    }

    @Test
    public void generatedCorrectNewsResponseMessageInXml() {
        Article article1 = new Article("title1", "description1", "picurl", "url");
        Article article2 = new Article("title", "description", "picurl", "url");

        String xmlMsg = weChatKit.messageManager().generateNewsResponseMessageInXml("toUser", article1, article2);
        assertEquals("Generate wrong XML text message.", expectedNewsMsg, xmlMsg.replaceAll("<CreateTime>\\d+</CreateTime>", "<CreateTime>12345678</CreateTime>"));
    }

    @Test
    public void generatedCorrectNewsResponseMessageInXml_Object() {
        Article article1 = new Article("title1", "description1", "picurl", "url");
        Article article2 = new Article("title", "description", "picurl", "url");
        ResponseNewsMsg newsMsg = new ResponseNewsMsg("fromUser", "toUser", 12345678L);
        newsMsg.setArticles(Arrays.asList(article1, article2));

        String xmlMsg = weChatKit.messageManager().generateResponseMessageInXml(newsMsg);
        assertEquals("Generate wrong XML text message.", expectedNewsMsg, xmlMsg);
    }

}
