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

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Warren Fan
 */
public class TemplateMessageForSendGsonTypeAdapter extends TypeAdapter<TemplateMessageForSend> {

    private final String HEX_COLOR_REGEX = "^(.+)(#([a-fA-F0-9]{6}|[a-fA-F0-9]{3}))$";
    private final Pattern hexColorPattern = Pattern.compile(HEX_COLOR_REGEX);

    @Override
    public void write(JsonWriter out, TemplateMessageForSend value) throws IOException {
        out.beginObject();

        out.name("template_id").value(value.getTemplateId());
        out.name("touser").value(value.getTouser());
        out.name("url").value(null != value.getUrl() ? value.getUrl():"");

        out.name("data").beginObject();
        for (Map.Entry<String, String> entry: value.getData().entrySet()) {
            final String k = entry.getKey();
            Matcher matcher = hexColorPattern.matcher(k);
            if (matcher.matches()) {
                out.name(matcher.group(1)).beginObject();
                out.name("value").value(entry.getValue());
                out.name("color").value(matcher.group(2));
                out.endObject();
            } else {
                out.name(k).beginObject();
                out.name("value").value(entry.getValue());
                out.endObject();
            }
        }
        out.endObject(); // end of "data" object

        out.endObject();
    }

    @Override
    public TemplateMessageForSend read(JsonReader in) throws IOException {
        return null;
    }
}
