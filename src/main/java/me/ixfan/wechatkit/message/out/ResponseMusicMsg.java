package me.ixfan.wechatkit.message.out;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by xfan on 16/3/27.
 */
@XmlRootElement(name = "xml")
public class ResponseMusicMsg extends ResponseMsg {

    private Music music;

    @XmlElement(name = "Music")
    public Music getMusic() {
        return music;
    }

    public void setMusic(Music music) {
        this.music = music;
    }

    @Override
    public String getMsgType() {
        return ResponseMsgType.Music.value();
    }

    @Override
    public String[] cdataElements() {
        List<String> cdataElements = new ArrayList<>(Arrays.asList(super.cdataElements()));
        cdataElements.addAll(Arrays.asList(this.music.cdataElements()));
        return cdataElements.toArray(new String[cdataElements.size()]);
    }

    @Override
    public String toString() {
        return "ResponseMusicMsg[" + super.toString()
                + ", MsgType='" + this.getMsgType() + "', "
                + music.toString() + "]";
    }
}
