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
package me.ixfan.wechatkit.material;

import me.ixfan.wechatkit.WeChatKit;
import me.ixfan.wechatkit.common.WechatApiResult;
import me.ixfan.wechatkit.token.SimpleTokenContainer;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

/**
 * @author Warren Fan
 */
@RunWith(JUnit4.class)
public class UploadMaterialTest {

    private WeChatKit weChatKit = WeChatKit.build(null, "APPID", "APPSECRET", SimpleTokenContainer.getTokenContainer());

    @Ignore("已测试通过, 忽略是因为避免每次单元测试都向公众号上传素材.")
    @Test
    public void testUploadTempImage_file() {
        File file = getMaterialFile("WxMaterial/CqOMI6wXYAAI996.jpeg");

        WechatApiResult result = weChatKit.materialManager().uploadTemporaryImage(file);
        assertNotNull(result);
        assertNotNull("Upload image failed!", result.getMediaId());
        assertEquals(MediaType.IMAGE.stringValue(), result.getType());
    }

    @Ignore("已测试通过, 忽略是因为避免每次单元测试都向公众号上传素材.")
    @Test
    public void testUploadTempImage_bytes() {
        byte[] bytes;
        try {
            bytes = getMaterialFileBytes("WxMaterial/CqOMI6wXYAAI996.jpeg");
        } catch (FileNotFoundException e) {
            fail("Image \"WxMaterial/CqOMI6wXYAAI996.jpeg\" not exists!");
            return;
        }

        WechatApiResult result = weChatKit.materialManager().uploadTemporaryImage("uploadTempImageTest.jpeg", bytes);
        assertNotNull(result);
        assertNotNull("Upload image failed!", result.getMediaId());
        assertEquals(MediaType.IMAGE.stringValue(), result.getType());
    }

    @Ignore("已测试通过, 忽略是因为避免每次单元测试都向公众号上传素材.")
    @Test
    public void testUploadPermanentImage_file() {
        File file = getMaterialFile("WxMaterial/CqOMI6wXYAAI996.jpeg");

        WechatApiResult result = weChatKit.materialManager().uploadPermanentImage(file);
        assertNotNull(result);
        assertNotNull("Lost field 'media_id'!", result.getMediaId());
        assertNotNull("Lost field 'url'!", result.getUrl());
    }

    @Ignore("已测试通过, 忽略是因为避免每次单元测试都向公众号上传素材.")
    @Test
    public void testUploadPermanentImage_bytes() {
        byte[] bytes;
        try {
            bytes = getMaterialFileBytes("WxMaterial/CqOMI6wXYAAI996.jpeg");
        } catch (FileNotFoundException e) {
            fail("Image \"WxMaterial/CqOMI6wXYAAI996.jpeg\" not exists!");
            return;
        }

        WechatApiResult result = weChatKit.materialManager().uploadPermanentImage("uploadPermanentImageTest.jpeg", bytes);
        assertNotNull(result);
        assertNotNull("Lost field 'media_id'!", result.getMediaId());
        assertNotNull("Lost field 'url'!", result.getUrl());
    }

    private File getMaterialFile(String relativePathToFile) {
        return new File(getClass().getClassLoader().getResource(relativePathToFile).getFile());
    }

    private byte[] getMaterialFileBytes(String relativePathToFile) throws FileNotFoundException {
        File file = new File(getClass().getClassLoader().getResource(relativePathToFile).getFile());
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            byte[] bytes = new byte[(int) file.length()];
            fileInputStream.read(bytes);
            fileInputStream.close();
            return bytes;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
