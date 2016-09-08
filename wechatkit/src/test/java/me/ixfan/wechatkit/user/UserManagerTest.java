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
package me.ixfan.wechatkit.user;

import me.ixfan.wechatkit.WeChatKit;
import me.ixfan.wechatkit.exceptions.WeChatApiErrorException;
import me.ixfan.wechatkit.token.SimpleTokenContainer;
import me.ixfan.wechatkit.user.model.WeChatFollower;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.*;

/**
 * @author Warren Fan
 */
@RunWith(JUnit4.class)
public class UserManagerTest {
    private WeChatKit weChatKit = WeChatKit.build(null, "APPID", "APPSECRET", SimpleTokenContainer.getTokenContainer());

    @Ignore("测试通过，忽略以防止每次单元测试都调用微信API")
    @Test
    public void testGetFollowersOpenIdList() {
        String[] openids;
        try {
            openids = weChatKit.userManager().getFollowerList(null);
        } catch (WeChatApiErrorException e) {
            fail(e.getMessage());
            return;
        }
        assertNotNull(openids);
        assertTrue("应该有关注者的，怎么没获取到？！", openids.length > 0);
    }

    @Ignore("测试通过，忽略以防止每次单元测试都调用微信API")
    @Test
    public void testGetUserInfoSuccessfully() {
        final String myOpenId = "op8KJwO3XUAMMsvDXqwVMWskLxV0";
        WeChatFollower user;
        try {
            user = weChatKit.userManager().getUserInfo(myOpenId, "zh_TW");
        } catch (WeChatApiErrorException e) {
            fail(e.getMessage());
            return;
        }
        assertNotNull(user);
        assertEquals("Get wrong nickname!", "大小棥", user.getNickname());
        assertEquals("", myOpenId, user.getOpenid());
        assertNotNull("用户头像URL没获取到", user.getHeadimgurl());
        assertTrue("关注时间没获取到", null != user.getSubscribeTime() && user.getSubscribeTime().compareTo(0L) > 0);
    }
}
