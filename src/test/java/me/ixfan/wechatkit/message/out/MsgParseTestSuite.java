package me.ixfan.wechatkit.message.out;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Created by xfan on 16/4/2.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({ MsgParseMusicTest.class, MsgParseNewsTest.class,
        MsgParseVideoTest.class, MsgParseVoiceTest.class,
        MsgParseTextTest.class, MsgParseImageTest.class })
public class MsgParseTestSuite {
}
