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

package me.ixfan.wechatkit.message.out.template;

/**
 * 微信消息模板。
 *
 * @author Warren Fan
 */
public class MessageTemplate {

    private String templateId;
    private String title;
    private String primaryIndustry;
    private String deputyIndustry;
    private String content;
    private String example;

    /**
     * 模板ID。
     * @return 模板ID。
     */
    public String getTemplateId() {
        return templateId;
    }

    /**
     * 模板标题。
     * @return 模板标题。
     */
    public String getTitle() {
        return title;
    }

    /**
     * 模板所属行业的一级行业。
     * @return 模板所属行业的一级行业。
     */
    public String getPrimaryIndustry() {
        return primaryIndustry;
    }

    /**
     * 模板所属行业的二级行业。
     * @return 模板所属行业的二级行业。
     */
    public String getDeputyIndustry() {
        return deputyIndustry;
    }

    /**
     * 模板内容。
     * @return 模板内容。
     */
    public String getContent() {
        return content;
    }

    /**
     * 模板示例。
     * @return 模板示例。
     */
    public String getExample() {
        return example;
    }
}
