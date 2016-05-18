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
 * Created by xfan on 16/4/14.
 */
public class MsgParseImageTest {

    private ResponseImageMsg imageMsg;
    private String imageMsgXml;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setup() {
        imageMsgXml = "<xml><ToUserName><![CDATA[toUser]]></ToUserName><FromUserName><![CDATA[fromUser]]></FromUserName><CreateTime>12345678</CreateTime><MsgType><![CDATA[image]]></MsgType><Image><MediaId><![CDATA[media_id]]></MediaId></Image></xml>";
        imageMsg = new ResponseImageMsg();
        imageMsg.setToUserName("toUser");
        imageMsg.setFromUserName("fromUser");
        imageMsg.setCreateTime(12345678L);
        imageMsg.setMediaImage(new MediaObject("media_id"));
    }

    @After
    public void cleanup() {
        imageMsg = null;
        imageMsgXml = null;
    }

    @Test
    public void successfullySerializeImageMsgToXml() {
        try {
            assertEquals(imageMsgXml, JAXBUtil.marshal(imageMsg));
        } catch (Exception e) {
            e.printStackTrace();
            fail("No exception should occurred!");
        }
    }

    @Test
    public void successfullyDeserializeXmlImageMsgToObject() {
        try {
            ResponseImageMsg msg = JAXBUtil.unmarshal(imageMsgXml, ResponseImageMsg.class);
            assertEquals(imageMsg.toString(), msg.toString());
        } catch (JAXBException e) {
            e.printStackTrace();
            fail("No exception should occurred!");
        }
    }

    @Test
    public void toUserNameIsRequired() throws JAXBException, IOException, SAXException {
        imageMsg.setToUserName(null);

        thrown.expect(MarshalException.class);
        JAXBUtil.marshal(imageMsg);
    }

    @Test
    public void toFromNameIsRequired() throws JAXBException, IOException, SAXException {
        imageMsg.setFromUserName(null);

        thrown.expect(MarshalException.class);
        JAXBUtil.marshal(imageMsg);
    }

    @Test
    public void createTimeIsRequired() throws JAXBException, IOException, SAXException {
        imageMsg.setCreateTime(null);

        thrown.expect(MarshalException.class);
        JAXBUtil.marshal(imageMsg);
    }

    @Test
    public void imageObjectIsRequired() throws JAXBException, IOException, SAXException {
        imageMsg.setMediaImage(null);

        thrown.expect(MarshalException.class);
        JAXBUtil.marshal(imageMsg);
    }

    @Test
    public void meidaIdOfImageIsRequired() throws JAXBException, IOException, SAXException {
        imageMsg.getMediaImage().setMediaId(null);

        thrown.expect(MarshalException.class);
        JAXBUtil.marshal(imageMsg);
    }
}
