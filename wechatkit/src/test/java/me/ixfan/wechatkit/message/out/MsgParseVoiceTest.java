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

import me.ixfan.wechatkit.material.MediaObject;
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
 * Created by xfan on 16/4/6.
 */
public class MsgParseVoiceTest {

    private ResponseVoiceMsg voiceMsg;
    private String voiceMsgXml;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setup() {
        voiceMsgXml = "<xml><ToUserName><![CDATA[toUser]]></ToUserName><FromUserName><![CDATA[fromUser]]></FromUserName><CreateTime>12345678</CreateTime><MsgType><![CDATA[voice]]></MsgType><Voice><MediaId><![CDATA[media_id]]></MediaId></Voice></xml>";
        voiceMsg = new ResponseVoiceMsg();
        voiceMsg.setToUserName("toUser");
        voiceMsg.setFromUserName("fromUser");
        voiceMsg.setCreateTime(12345678L);
        voiceMsg.setMediaVoice(new MediaObject("media_id"));
    }

    @After
    public void cleanup() {
        voiceMsg = null;
        voiceMsgXml = null;
    }

    @Test
    public void successfullySerializeVoiceMsgToXml() {
        try {
            assertEquals(voiceMsgXml, JAXBUtil.marshal(voiceMsg));
        } catch (Exception e) {
            e.printStackTrace();
            fail("No exception should occurred!");
        }
    }

    @Test
    public void successfullyDeserializeXmlVoiceMsgToObject() {
        try {
            ResponseVoiceMsg msg  = JAXBUtil.unmarshal(voiceMsgXml, ResponseVoiceMsg.class);
            assertEquals(voiceMsg.toString(), msg.toString());
        } catch (JAXBException e) {
            e.printStackTrace();
            fail("No exception should occurred!");
        }
    }

    @Test
    public void toUserNameIsRequired() throws JAXBException, IOException, SAXException, TransformerException {
        voiceMsg.setToUserName(null);

        thrown.expect(MarshalException.class);
        JAXBUtil.marshal(voiceMsg);
    }

    @Test
    public void fromUserNameIsRequired() throws JAXBException, IOException, SAXException, TransformerException {
        voiceMsg.setFromUserName(null);

        thrown.expect(MarshalException.class);
        JAXBUtil.marshal(voiceMsg);
    }

    @Test
    public void mediaIdOfVoiceIsRequired() throws JAXBException, IOException, SAXException, TransformerException {
        voiceMsg.getMediaVoice().setMediaId(null);

        thrown.expect(MarshalException.class);
        JAXBUtil.marshal(voiceMsg);
    }
}
