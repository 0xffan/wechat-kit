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
import me.ixfan.wechatkit.user.model.UserTag;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author Warren Fan
 */
@RunWith(JUnit4.class)
public class TagManagerTest {

    private WeChatKit weChatKit = WeChatKit.build(null, "APPID", "APPSECRET", SimpleTokenContainer.getTokenContainer());

    @Ignore("测试通过")
    @Test
    public void testCreateTagSuccessfully() {
        try {
            UserTag tag = weChatKit.userManager().createTag("标签管理测试");
            assertNotNull(tag);
            assertTrue(tag.getId() > 0);
            assertEquals("实际创建的标签名与传入的不一样！", "标签管理测试", tag.getName());
        } catch (WeChatApiErrorException e) {
            fail(e.getMessage());
        }
    }

    @Ignore("测试通过")
    @Test
    public void testGetExistingTagsSuccess() {
        try {
            List<UserTag> tags = weChatKit.userManager().getExistingTags();
            assertNotNull(tags);
            assertTrue(tags.size() > 0);
        } catch (WeChatApiErrorException e) {
            fail(e.getMessage());
        }
    }

    @Ignore("测试通过")
    @Test
    public void testUpdateTagSuccess() {
        try {
            weChatKit.userManager().updateTag(100, "标签测试");
        } catch (WeChatApiErrorException e) {
            fail(e.getMessage());
        }
    }

    @Ignore("测试通过")
    @Test
    public void testDeleteTagSuccess() {
        try {
            weChatKit.userManager().deleteTag(101);
        } catch (WeChatApiErrorException e) {
            fail(e.getMessage());
        }
    }

    @Ignore("测试通过")
    @Test
    public void testBatchTaggingUsers() {
        try {
            weChatKit.userManager().batchTaggingUsersWithTag(100, Arrays.asList("op8KJwO3XUAMMsvDXqwVMWskLxV0"));
        } catch (WeChatApiErrorException e) {
            fail(e.getMessage());
        }
    }

    @Ignore("测试通过")
    @Test
    public void testGetUsersWithTag() {
        try {
            String[] openIds = weChatKit.userManager().getUsersWithTag(100, null);
            assertNotNull(openIds);
            assertTrue("此标签下没有用户！", openIds.length > 0);
            assertEquals("op8KJwO3XUAMMsvDXqwVMWskLxV0", openIds[0]);
        } catch (WeChatApiErrorException e) {
            fail(e.getMessage());
        }
    }

    @Ignore("测试通过")
    @Test
    public void testBatchUntaggingUsers() {
        try {
            weChatKit.userManager().batchUntaggingUsers(100, Arrays.asList("op8KJwO3XUAMMsvDXqwVMWskLxV0"));
        } catch (WeChatApiErrorException e) {
            fail(e.getMessage());
        }
    }

    @Ignore("测试通过")
    @Test
    public void getTagsOfUser() {
        try {
            int[] tagsIds = weChatKit.userManager().getTagsOfUser("op8KJwO3XUAMMsvDXqwVMWskLxV0");
            assertNotNull(tagsIds);
            assertTrue("此用户身上没有标签！", tagsIds.length > 0);
        } catch (WeChatApiErrorException e) {
            fail(e.getMessage());
        }
    }
}
