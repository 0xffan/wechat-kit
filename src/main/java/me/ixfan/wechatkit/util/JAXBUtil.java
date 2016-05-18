package me.ixfan.wechatkit.util;

import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;
import me.ixfan.wechatkit.message.out.xml.XmlSerializable;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.bind.*;
import javax.xml.transform.Result;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * Created by xfan on 16/3/31.
 */
public class JAXBUtil {

    public static <T extends XmlSerializable> String marshal(T object) throws JAXBException, IOException, SAXException {
        JAXBContext jaxbContext = JAXBContext.newInstance(object.getClass());
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, false);
        jaxbMarshaller.setProperty(Marshaller.JAXB_ENCODING, StandardCharsets.UTF_8.name());
        generateAndSetSchema(jaxbContext, jaxbMarshaller);

        OutputFormat outputFormat = new OutputFormat();
        outputFormat.setOmitDocumentType(true);
        outputFormat.setOmitXMLDeclaration(true);
        outputFormat.setCDataElements(object.cdataElements());
        outputFormat.setPreserveEmptyAttributes(true);
        outputFormat.setIndenting(false);

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        XMLSerializer serializer = new XMLSerializer(outputFormat);
        serializer.setOutputByteStream(os);

        jaxbMarshaller.marshal(object, serializer.asContentHandler());

        return os.toString(StandardCharsets.UTF_8.name());
    }

    public static <T> T unmarshal(String msgInXml, Class<T> tClass) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(tClass);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

        T object = (T) jaxbUnmarshaller.unmarshal(new ByteArrayInputStream(msgInXml.getBytes(StandardCharsets.UTF_8)));

        return object;
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

        public byte[] getSchemaContent() {
            return schemaOutputStream.toByteArray();
        }
    }
}
