package me.ixfan.wechatkit.message.out;

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
 * Created by xfan on 16/4/14.
 */
public class MsgParseTextTest {

    private ResponseTextMsg textMsg;
    private String textMsgXml;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setup() {
        textMsgXml = "<xml><ToUserName><![CDATA[toUser]]></ToUserName><FromUserName><![CDATA[fromUser]]></FromUserName><CreateTime>12345678</CreateTime><MsgType><![CDATA[text]]></MsgType><Content><![CDATA[你好]]></Content></xml>";
        textMsg = new ResponseTextMsg();
        textMsg.setToUserName("toUser");
        textMsg.setFromUserName("fromUser");
        textMsg.setCreateTime(12345678L);
        textMsg.setContent("你好");
    }

    @After
    public void cleanup() {
        this.textMsg = null;
        this.textMsgXml = null;
    }

    @Test
    public void successfullySerializeTextMsgToXml() {
        try {
            ResponseTextMsg msg = JAXBUtil.unmarshal(textMsgXml, ResponseTextMsg.class);
            assertEquals(textMsg.toString(), msg.toString());
        } catch (JAXBException e) {
            e.printStackTrace();
            fail("No exception should occurred!");
        }
    }

    @Test
    public void successfullyDeserializeXmlTextMsgToObject() {
        try {
            assertEquals(textMsgXml, JAXBUtil.marshal(textMsg));
        } catch (Exception e) {
            e.printStackTrace();
            fail("No exception should occurred!");
        }
    }

    @Test
    public void toUserNameIsRequired() throws JAXBException, IOException, SAXException {
        textMsg.setToUserName(null);

        thrown.expect(MarshalException.class);
        JAXBUtil.marshal(textMsg);
    }

    @Test
    public void fromUserNameIsRequired() throws JAXBException, IOException, SAXException {
        textMsg.setFromUserName(null);

        thrown.expect(MarshalException.class);
        JAXBUtil.marshal(textMsg);
    }

    @Test
    public void createTimeIsRequired() throws JAXBException, IOException, SAXException {
        textMsg.setCreateTime(null);

        thrown.expect(MarshalException.class);
        JAXBUtil.marshal(textMsg);
    }

    @Test
    public void msgContentIsRequired() throws JAXBException, IOException, SAXException {
        textMsg.setContent(null);

        thrown.expect(MarshalException.class);
        JAXBUtil.marshal(textMsg);
    }
}
