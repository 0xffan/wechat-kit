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

package me.ixfan.wechatkit.message.out.json;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author Warren Fan
 */
@RunWith(JUnit4.class)
public class HexColorRegexTest {

    private final Pattern hexColorPattern = Pattern.compile("^(.+)(#([a-fA-F0-9]{6}|[a-fA-F0-9]{3}))$");
    private final List<String> paramsWithSpecifiedColor =
                    Arrays.asList("paramName#f1f1f1", "paramName#fff", "paramName#333333",
                                  "paramName#3cb371", "paramName#333", "paramName#556B2F",
                                  "paramName#708090", "paramName#AF3", "paramName#3Cbe7A",
                                  "first#173177", "keynote1#173177", "remark#173177");

    @Test
    public void testParamWithHexColorLengthOf6IsMatch() {
        Matcher matcher = hexColorPattern.matcher("paramName#f3f1f3");
        assertTrue(matcher.matches());
        assertEquals("Matched wrong parameter name!", "paramName", matcher.group(1));
        assertEquals("Matched wrong color!", "#f3f1f3", matcher.group(2));
    }

    @Test
    public void testParamWithHexColorLengthOf3IsMatch() {
        Matcher matcher = hexColorPattern.matcher("paramName#fff");
        assertTrue(matcher.matches());
        assertEquals("Matched wrong parameter name!", "paramName", matcher.group(1));
        assertEquals("Matched wrong color!", "#fff", matcher.group(2));
    }

    @Test
    public void testBunchParamsWithSpecifiedColorAreMatch() {
        for (String s: paramsWithSpecifiedColor) {
            Matcher matcher = hexColorPattern.matcher(s);
            assertTrue(matcher.matches());
        }
    }

    @Test
    public void testParamWithoutColorIsNotMatch() {
        Matcher matcher = hexColorPattern.matcher("paramName");
        assertFalse(matcher.matches());
    }
}
