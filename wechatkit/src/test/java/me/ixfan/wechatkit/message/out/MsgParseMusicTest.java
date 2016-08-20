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

package me.ixfan.wechatkit.message.out;

import me.ixfan.wechatkit.message.out.xml.Music;
import me.ixfan.wechatkit.message.out.xml.ResponseMusicMsg;
import me.ixfan.wechatkit.util.JAXBUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBException;
import javax.xml.bind.MarshalException;
import javax.xml.transform.TransformerException;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Created by xfan on 16/4/2.
 */
public class MsgParseMusicTest {

    private String msgInXml;
    private ResponseMusicMsg musicMsg;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setup() {
        msgInXml = "<xml><ToUserName><![CDATA[toUser]]></ToUserName><FromUserName><![CDATA[fromUser]]></FromUserName><CreateTime>12345678</CreateTime><MsgType><![CDATA[music]]></MsgType><Music><Title><![CDATA[TITLE]]></Title><Description><![CDATA[<p>DESCRIPTION</p>]]></Description><MusicUrl><![CDATA[http://www.music-url.com/musicid]]></MusicUrl><HQMusicUrl><![CDATA[http://www.music-url.com/musicid/hq]]></HQMusicUrl><ThumbMediaId><![CDATA[media_id_2333]]></ThumbMediaId></Music></xml>";
        musicMsg = new ResponseMusicMsg();
        musicMsg.setFromUserName("fromUser");
        musicMsg.setToUserName("toUser");
        musicMsg.setCreateTime(12345678L);
        Music music = new Music();
        music.setTitle("TITLE");
        music.setDescription("<p>DESCRIPTION</p>");
        music.setMusicUrl("http://www.music-url.com/musicid");
        music.setHqMusicUrl("http://www.music-url.com/musicid/hq");
        music.setThumbMediaId("media_id_2333");
        musicMsg.setMusic(music);
    }

    @After
    public void clean() {
        musicMsg = null;
        msgInXml = null;
    }

    @Test
    public void successfullySerializeMusicMsgToXml() {
        try {
            assertEquals(msgInXml, JAXBUtil.marshal(musicMsg));
        } catch (Exception e) {
            e.printStackTrace();
            fail("No exception should occurred!");
        }
    }

    @Test
    public void successfullyDeserializeXmlMusicMsgToObject() {
        try {
            ResponseMusicMsg msg = JAXBUtil.unmarshal(msgInXml, ResponseMusicMsg.class);
            assertEquals(musicMsg.toString(), msg.toString());
        } catch (JAXBException e) {
            e.printStackTrace();
            fail("Exception should not occurred!");
        }
    }

    @Test
    public void toUserNameIsRequired() throws JAXBException, IOException, SAXException, TransformerException {
        musicMsg.setToUserName(null);

        thrown.expect(MarshalException.class);
        System.out.println(JAXBUtil.marshal(musicMsg));
    }

    @Test
    public void fromUserNameIsRequired() throws JAXBException, IOException, SAXException, TransformerException {
        musicMsg.setFromUserName(null);

        thrown.expect(MarshalException.class);
        System.out.println(JAXBUtil.marshal(musicMsg));
    }

    @Test
    public void createTimeIsRequired() throws JAXBException, IOException, SAXException, TransformerException {
        musicMsg.setCreateTime(null);

        thrown.expect(MarshalException.class);
        System.out.println(JAXBUtil.marshal(musicMsg));
    }

}
