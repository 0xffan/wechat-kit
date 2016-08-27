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

import me.ixfan.wechatkit.message.out.xml.ResponseVideoMsg;
import me.ixfan.wechatkit.message.out.xml.Video;
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
public class MsgParseVideoTest {

    private ResponseVideoMsg videoMsg;
    private String videoMsgXml;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setup() {
        videoMsgXml = "<xml><ToUserName><![CDATA[toUser]]></ToUserName><FromUserName><![CDATA[fromUser]]></FromUserName><CreateTime>12345678</CreateTime><MsgType><![CDATA[video]]></MsgType><Video><MediaId><![CDATA[media_id]]></MediaId><Title><![CDATA[title]]></Title><Description><![CDATA[description]]></Description></Video></xml>";
        videoMsg = new ResponseVideoMsg();
        videoMsg.setToUserName("toUser");
        videoMsg.setFromUserName("fromUser");
        videoMsg.setCreateTime(12345678L);
        Video video = new Video();
        video.setMediaId("media_id");
        video.setTitle("title");
        video.setDescription("description");
        videoMsg.setVideo(video);
    }

    @After
    public void cleanup() {
        videoMsgXml = null;
        videoMsg = null;
    }

    @Test
    public void successfullySerializeVideoMsgToXml() {
        try {
            assertEquals(videoMsgXml, JAXBUtil.marshal(videoMsg));
        } catch (Exception e) {
            e.printStackTrace();
            fail("No exception should occurred!");
        }
    }

    @Test
    public void successfullyDeserializeXmlVideoMsgToObject() {
        try {
            ResponseVideoMsg msg = JAXBUtil.unmarshal(videoMsgXml, ResponseVideoMsg.class);
            assertEquals(videoMsg.toString(), msg.toString());
        } catch (JAXBException e) {
            e.printStackTrace();
            fail("No exception should occurred!");
        }
    }

    @Test
    public void toUserNameIsRequired() throws JAXBException, IOException, SAXException, TransformerException {
        videoMsg.setToUserName(null);

        thrown.expect(MarshalException.class);
        JAXBUtil.marshal(videoMsg);
    }

    @Test
    public void fromUserNameIsRequired() throws JAXBException, IOException, SAXException, TransformerException {
        videoMsg.setFromUserName(null);

        thrown.expect(MarshalException.class);
        JAXBUtil.marshal(videoMsg);
    }

    @Test
    public void mediaIdOfVideoIsRequired() throws JAXBException, IOException, SAXException, TransformerException {
        videoMsg.getVideo().setMediaId(null);

        thrown.expect(MarshalException.class);
        JAXBUtil.marshal(videoMsg);
    }
}
