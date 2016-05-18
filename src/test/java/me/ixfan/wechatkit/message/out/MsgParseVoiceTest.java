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
    public void toUserNameIsRequired() throws JAXBException, IOException, SAXException {
        voiceMsg.setToUserName(null);

        thrown.expect(MarshalException.class);
        JAXBUtil.marshal(voiceMsg);
    }

    @Test
    public void fromUserNameIsRequired() throws JAXBException, IOException, SAXException {
        voiceMsg.setFromUserName(null);

        thrown.expect(MarshalException.class);
        JAXBUtil.marshal(voiceMsg);
    }

    @Test
    public void mediaIdOfVoiceIsRequired() throws JAXBException, IOException, SAXException {
        voiceMsg.getMediaVoice().setMediaId(null);

        thrown.expect(MarshalException.class);
        JAXBUtil.marshal(voiceMsg);
    }
}
