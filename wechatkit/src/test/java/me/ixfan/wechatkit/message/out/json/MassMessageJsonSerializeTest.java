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

package me.ixfan.wechatkit.message.out.json;

import me.ixfan.wechatkit.message.out.OutMessageType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

/**
 * @author Warren Fan
 */
@RunWith(JUnit4.class)
public class MassMessageJsonSerializeTest {

    private final String NEWS_TO_TAG = "{\"filter\":{\"is_to_all\":false,\"tag_id\":2},\"msgtype\":\"mpnews\",\"mpnews\":{\"media_id\":\"123dsdajkasd231jhksad\"}}";
    private final String TEXT_TO_Tag = "{\"filter\":{\"is_to_all\":false,\"tag_id\":2},\"msgtype\":\"text\",\"text\":{\"content\":\"CONTENT\"}}";
    private final String IMG_TO_TAG = "{\"filter\":{\"is_to_all\":false,\"tag_id\":2},\"msgtype\":\"image\",\"image\":{\"media_id\":\"123dsdajkasd231jhksad\"}}";
    private final String VOICE_TO_TAG = "{\"filter\":{\"is_to_all\":false,\"tag_id\":2},\"msgtype\":\"voice\",\"voice\":{\"media_id\":\"123dsdajkasd231jhksad\"}}";
    private final String VIDEO_TO_TAG = "{\"filter\":{\"is_to_all\":false,\"tag_id\":2},\"msgtype\":\"mpvideo\",\"mpvideo\":{\"media_id\":\"IhdaAQXuvJtGzwwc0abfXnzeezfO0NgPK6AQYShD8RQYMTtfzbLdBIQkQziv2XJc\"}}";
    private final String CARD_TO_TAG = "{\"filter\":{\"is_to_all\":false,\"tag_id\":\"t2\"},\"msgtype\":\"wxcard\",\"wxcard\":{\"card_id\":\"123dsdajkasd231jhksad\"}}";

    private final String NEWS_TO_USERS = "{\"touser\":[\"OPENID1\",\"OPENID2\"],\"msgtype\":\"mpnews\",\"mpnews\":{\"media_id\":\"123dsdajkasd231jhksad\"}}";
    private final String TEXT_TO_USERS = "{\"touser\":[\"OPENID1\",\"OPENID2\"],\"msgtype\":\"text\",\"text\":{\"content\":\"hello from boxer.\"}}";
    private final String IMG_TO_USERS = "{\"touser\":[\"OPENID1\",\"OPENID2\"],\"msgtype\":\"image\",\"image\":{\"media_id\":\"BTgN0opcW3Y5zV_ZebbsD3NFKRWf6cb7OPswPi9Q83fOJHK2P67dzxn11Cp7THat\"}}";
    private final String VOICE_TO_USERS = "{\"touser\":[\"OPENID1\",\"OPENID2\"],\"msgtype\":\"voice\",\"voice\":{\"media_id\":\"mLxl6paC7z2Tl-NJT64yzJve8T9c8u9K2x-Ai6Ujd4lIH9IBuF6-2r66mamn_gIT\"}}";
    private final String VIDEO_TO_USERS = "{\"touser\":[\"OPENID1\",\"OPENID2\"],\"msgtype\":\"video\",\"video\":{\"media_id\":\"123dsdajkasd231jhksad\",\"title\":\"TITLE\",\"description\":\"DESCRIPTION\"}}";
    private final String CARD_TO_USERS = "{\"touser\":[\"OPENID1\",\"OPENID2\"],\"msgtype\":\"wxcard\",\"wxcard\":{\"card_id\":\"123dsdajkasd231jhksad\"}}";

    @Test
    public void successfullySerializeMassTextMsgToTag() {
        MessageForMassSend msg = new MessageForMassSend(OutMessageType.TEXT, "CONTENT", "2", false);
        assertEquals(TEXT_TO_Tag, msg.toJsonString());
    }

    @Test
    public void successfullySerializeMassNewsMsgToTag() {
        MessageForMassSend msg = new MessageForMassSend(OutMessageType.MP_NEWS, "123dsdajkasd231jhksad", "2", false);
        assertEquals(NEWS_TO_TAG, msg.toJsonString());
    }

    @Test
    public void successfullySerializeMassImgMsgToTag() {
        MessageForMassSend msg = new MessageForMassSend(OutMessageType.IMAGE, "123dsdajkasd231jhksad", "2", false);
        assertEquals(IMG_TO_TAG, msg.toJsonString());
    }

    @Test
    public void successfullySerializeMassVoiceMsgToTag() {
        MessageForMassSend msg = new MessageForMassSend(OutMessageType.VOICE, "123dsdajkasd231jhksad", "2", false);
        assertEquals(VOICE_TO_TAG, msg.toJsonString());
    }

    @Test
    public void successfullySerializeMassVideoMsgToTag() {
        MessageForMassSend msg = new MessageForMassSend(OutMessageType.MP_VIDEO, "IhdaAQXuvJtGzwwc0abfXnzeezfO0NgPK6AQYShD8RQYMTtfzbLdBIQkQziv2XJc", "2", false);
        assertEquals(VIDEO_TO_TAG, msg.toJsonString());
    }

    @Test
    public void successfullySerializeMassCardMsgToTag() {
        MessageForMassSend msg = new MessageForMassSend(OutMessageType.WX_CARD, "123dsdajkasd231jhksad", "t2", false);
        assertEquals(CARD_TO_TAG, msg.toJsonString());
    }

    @Test
    public void successfullySerializeMassTextMsgToUsers() {
        MessageForMassSend msg = new MessageForMassSend(OutMessageType.TEXT, "hello from boxer.", Arrays.asList("OPENID1", "OPENID2"));
        assertEquals(TEXT_TO_USERS, msg.toJsonString());
    }

    @Test
    public void successfullySerializeMassNewsMsgToUsers() {
        MessageForMassSend msg = new MessageForMassSend(OutMessageType.MP_NEWS, "123dsdajkasd231jhksad", Arrays.asList("OPENID1", "OPENID2"));
        assertEquals(NEWS_TO_USERS, msg.toJsonString());
    }

    @Test
    public void successfullySerializeMassImgMsgToUsers() {
        MessageForMassSend msg = new MessageForMassSend(OutMessageType.IMAGE, "BTgN0opcW3Y5zV_ZebbsD3NFKRWf6cb7OPswPi9Q83fOJHK2P67dzxn11Cp7THat", Arrays.asList("OPENID1", "OPENID2"));
        assertEquals(IMG_TO_USERS, msg.toJsonString());
    }
    @Test
    public void successfullySerializeMassVoiceMsgToUsers() {
        MessageForMassSend msg = new MessageForMassSend(OutMessageType.VOICE, "mLxl6paC7z2Tl-NJT64yzJve8T9c8u9K2x-Ai6Ujd4lIH9IBuF6-2r66mamn_gIT", Arrays.asList("OPENID1", "OPENID2"));
        assertEquals(VOICE_TO_USERS, msg.toJsonString());
    }

    @Test
    public void successfullySerializeMassVideoMsgToUsers() {
        MessageForMassSend msg = new MessageForMassSend(OutMessageType.VIDEO, "123dsdajkasd231jhksad", Arrays.asList("OPENID1", "OPENID2"));
        msg.setTitle("TITLE");
        msg.setDescription("DESCRIPTION");
        assertEquals(VIDEO_TO_USERS, msg.toJsonString());
    }

    @Test
    public void successfullySerializeMassCardMsgToUsers() {
        MessageForMassSend msg = new MessageForMassSend(OutMessageType.WX_CARD, "123dsdajkasd231jhksad", Arrays.asList("OPENID1", "OPENID2"));
        assertEquals(CARD_TO_USERS, msg.toJsonString());
    }

}
