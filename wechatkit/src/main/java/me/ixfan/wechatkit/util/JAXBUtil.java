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

package me.ixfan.wechatkit.util;

import me.ixfan.wechatkit.message.out.xml.XmlSerializable;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.bind.*;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;

/**
 * Created by xfan on 16/3/31.
 */
public class JAXBUtil {

    /**
     * Serialize {@link XmlSerializable} instance to XML string. There is no XML declaration in the result XML string.
     * @param object Object to be serialized.
     * @param <T> The type of the object to be serialized, it must implements {@link XmlSerializable} interface.
     * @return XML string.
     * @throws JAXBException
     * @throws IOException
     * @throws SAXException
     * @throws TransformerException
     */
    public static <T extends XmlSerializable> String marshal(T object)
            throws JAXBException, IOException, SAXException, TransformerException {
        JAXBContext jaxbContext = JAXBContext.newInstance(object.getClass());
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, false);
        jaxbMarshaller.setProperty(Marshaller.JAXB_ENCODING, StandardCharsets.UTF_8.name());
        generateAndSetSchema(jaxbContext, jaxbMarshaller);

        ByteArrayOutputStream os1 = new ByteArrayOutputStream();
        jaxbMarshaller.marshal(object, os1);

        Transformer transformer = SAXTransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.ENCODING, StandardCharsets.UTF_8.name());
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
        transformer.setOutputProperty(OutputKeys.STANDALONE, "no");
        transformer.setOutputProperty(OutputKeys.INDENT, "no");
        transformer.setOutputProperty(OutputKeys.CDATA_SECTION_ELEMENTS, object.cdataElements());

        StreamResult result = new StreamResult(new ByteArrayOutputStream());
        transformer.transform(new StreamSource(new StringReader(os1.toString(StandardCharsets.UTF_8.name()))), result);

        return ((ByteArrayOutputStream)result.getOutputStream()).toString(StandardCharsets.UTF_8.name());
    }

    public static <T> T unmarshal(String msgInXml, Class<T> tClass) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(tClass);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

        return (T) jaxbUnmarshaller.unmarshal(new ByteArrayInputStream(msgInXml.getBytes(StandardCharsets.UTF_8)));
    }

    private static void generateAndSetSchema(JAXBContext jaxbContext, Marshaller marshaller) throws IOException, SAXException {
        ByteArrayStreamOutputResolver schemaOutput = new ByteArrayStreamOutputResolver();
        jaxbContext.generateSchema(schemaOutput);

        ByteArrayInputStream schemaInputStream = new ByteArrayInputStream(schemaOutput.getSchemaContent());
        SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Schema schema = sf.newSchema(new StreamSource(schemaInputStream));

        marshaller.setSchema(schema);
    }

    private static class ByteArrayStreamOutputResolver extends SchemaOutputResolver {

        private ByteArrayOutputStream schemaOutputStream;

        private int INITIAL_SCHEMA_BUFFER_SIZE = 1024;

        @Override
        public Result createOutput(String namespaceUri, String suggestedFileName) throws IOException {
            schemaOutputStream = new ByteArrayOutputStream(INITIAL_SCHEMA_BUFFER_SIZE);
            StreamResult result = new StreamResult(schemaOutputStream);
            result.setSystemId("");
            return result;
        }

        byte[] getSchemaContent() {
            return schemaOutputStream.toByteArray();
        }
    }
}
