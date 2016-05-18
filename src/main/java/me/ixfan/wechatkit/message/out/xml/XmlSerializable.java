package me.ixfan.wechatkit.message.out.xml;

/**
 * Created by xfan on 16/4/2.
 */
public interface XmlSerializable {
    default String[] cdataElements() {
        return new String[0];
    }
}
