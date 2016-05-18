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
import java.io.UnsupportedEncodingException;

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
    public void toUserNameIsRequired() throws JAXBException, IOException, SAXException {
        musicMsg.setToUserName(null);

        thrown.expect(MarshalException.class);
        System.out.println(JAXBUtil.marshal(musicMsg));
    }

    @Test
    public void fromUserNameIsRequired() throws JAXBException, IOException, SAXException {
        musicMsg.setFromUserName(null);

        thrown.expect(MarshalException.class);
        System.out.println(JAXBUtil.marshal(musicMsg));
    }

    @Test
    public void createTimeIsRequired() throws JAXBException, IOException, SAXException {
        musicMsg.setCreateTime(null);

        thrown.expect(MarshalException.class);
        System.out.println(JAXBUtil.marshal(musicMsg));
    }

}
