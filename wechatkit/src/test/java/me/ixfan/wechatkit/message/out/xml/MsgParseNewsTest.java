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

package me.ixfan.wechatkit.message.out.xml;

import me.ixfan.wechatkit.message.out.xml.Article;
import me.ixfan.wechatkit.message.out.xml.ResponseNewsMsg;
import me.ixfan.wechatkit.util.JAXBUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBException;
import javax.xml.bind.MarshalException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Created by xfan on 16/4/2.
 */
public class MsgParseNewsTest {

    private ResponseNewsMsg newsMsg;
    private String newsMsgXml;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setup() {
        newsMsgXml = "<xml><ToUserName><![CDATA[toUser]]></ToUserName><FromUserName><![CDATA[fromUser]]></FromUserName><CreateTime>12345678</CreateTime><MsgType><![CDATA[news]]></MsgType><ArticleCount>2</ArticleCount><Articles><item><Title><![CDATA[title1]]></Title><Description><![CDATA[description1]]></Description><PicUrl><![CDATA[picurl]]></PicUrl><Url><![CDATA[url]]></Url></item><item><Title><![CDATA[title2]]></Title><Description><![CDATA[description2]]></Description><PicUrl><![CDATA[picurl]]></PicUrl><Url><![CDATA[url]]></Url></item></Articles></xml>";
        newsMsg = new ResponseNewsMsg();
        newsMsg.setToUserName("toUser");
        newsMsg.setFromUserName("fromUser");
        newsMsg.setCreateTime(12345678L);
        List<Article> articles = new ArrayList<>();
        for (int i = 1; i <= 2; i++) {
            Article article = new Article();
            article.setTitle("title" + i);
            article.setDescription("description" + i);
            article.setPicUrl("picurl");
            article.setUrl("url");
            articles.add(article);
        }
        newsMsg.setArticleCount(articles.size());
        newsMsg.setArticles(articles);
    }

    @After
    public void cleanup() {
        newsMsg = null;
        newsMsgXml = null;
    }

    @Test
    public void successfullySerializeNewsMsgToXml() {
        try {
            assertEquals(newsMsgXml, JAXBUtil.marshal(newsMsg));
        } catch (Exception e) {
            e.printStackTrace();
            fail("No exception should occurred!");
        }
    }

    @Test
    public void successfullyDeserializeXmlNewsMsgToObject() {
        try {
            ResponseNewsMsg msg = JAXBUtil.unmarshal(newsMsgXml, ResponseNewsMsg.class);
            assertEquals(newsMsg.toString(), msg.toString());
        } catch (JAXBException e) {
            e.printStackTrace();
            fail("No exception should occurred!");
        }
    }

    @Test
    public void toUserNameIsRequired() throws JAXBException, IOException, SAXException, TransformerException {
        newsMsg.setToUserName(null);

        thrown.expect(MarshalException.class);
        JAXBUtil.marshal(newsMsg);
    }

    @Test
    public void fromUserNameIsRequired() throws JAXBException, IOException, SAXException, TransformerException {
        newsMsg.setFromUserName(null);

        thrown.expect(MarshalException.class);
        JAXBUtil.marshal(newsMsg);
    }

    @Test
    public void createTimeIsRequired() throws JAXBException, IOException, SAXException, TransformerException {
        newsMsg.setCreateTime(null);

        thrown.expect(MarshalException.class);
        JAXBUtil.marshal(newsMsg);
    }

    @Test
    public void articleCountIsRequired() throws JAXBException, IOException, SAXException, TransformerException {
        newsMsg.setArticleCount(null);

        thrown.expect(MarshalException.class);
        JAXBUtil.marshal(newsMsg);
    }

    @Test
    public void articlesIsRequired() throws JAXBException, IOException, SAXException, TransformerException {
        newsMsg.setArticles(null);

        thrown.expect(MarshalException.class);
        JAXBUtil.marshal(newsMsg);
    }
}
