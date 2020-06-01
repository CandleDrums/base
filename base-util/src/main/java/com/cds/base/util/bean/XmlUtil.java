package com.cds.base.util.bean;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * 类XmlUtil.java的实现描述：xml工具类
 * 
 * @author george 2015年10月21日 下午2:30:57
 */
public class XmlUtil {

    /**
     * 类转换为xml格式
     * 
     * @param cl
     * @param ob
     *            类上面加注解 @XmlRootElement(name = "xml"),并提供无参数的构造方法
     * @return
     * @throws JAXBException
     */
    public static String getBody(Class<?> cl, Object ob) {
        return getBody(cl, ob, "UTF-8", false);
    }

    /**
     * 类转换为xml格式
     * 
     * @param cl
     * @param ob
     *            类上面加注解 @XmlRootElement(name = "xml"),并提供无参数的构造方法
     * @return
     * @throws JAXBException
     */
    public static String getBody(Class<?> cl, Object ob, String encoding) {
        return getBody(cl, ob, encoding, false);
    }

    /**
     * 类转换为xml格式
     * 
     * @param cl
     * @param ob
     *            类上面加注解 @XmlRootElement(name = "xml"),并提供无参数的构造方法
     * @param encoding
     *            编码格式
     * @param ignoreFrage
     *            忽略头信息
     * @return
     * @throws JAXBException
     */
    public static String getBody(Class<?> cl, Object ob, String encoding, boolean ignoreFrage) {
        String result = null;
        Writer write = new StringWriter();
        try {

            JAXBContext context = JAXBContext.newInstance(cl);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_ENCODING, encoding);
            marshaller.setProperty(Marshaller.JAXB_FRAGMENT, ignoreFrage);
            marshaller.marshal(ob, write);
            result = write.toString();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                write.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return result;
    }

    /**
     * 将xml字符串映射到对象中
     * 
     * @param xml
     * @param tClass
     * @return
     */
    public static Object getObjectFromXML(String xml, String alias, Class<?> tClass) {
        // 将从API返回的XML数据映射到Java对象
        XStream xStreamForResponseData = new XStream();
        xStreamForResponseData.alias(alias, tClass);
        xStreamForResponseData.ignoreUnknownElements();// 暂时忽略掉一些新增的字段
        return xStreamForResponseData.fromXML(xml);
    }

    /**
     * 将xml字符串映射到对象中 <br/>
     * tClass请使用注解
     * 
     * @param xml
     * @param tClass
     * @return
     */
    public static Object getObjectFromXML(String xml, Class<?> tClass) {
        // 将从API返回的XML数据映射到Java对象
        XStream xStream = new XStream(new DomDriver());
        xStream.ignoreUnknownElements();// 暂时忽略掉一些新增的字段
        xStream.processAnnotations(tClass);
        return xStream.fromXML(xml);
    }

    /**
     * 从XML反序列化至对象
     * 
     * @param xml
     * @param tClass
     * @param charsetName
     *            指定字符编码
     * @return
     */
    public static Object getObjectFromXML(String xml, Class<?> tClass, String charsetName) {
        // 将从API返回的XML数据映射到Java对象
        XStream xStream = new XStream(new DomDriver(charsetName));
        xStream.ignoreUnknownElements();// 暂时忽略掉一些新增的字段
        xStream.processAnnotations(tClass);
        return xStream.fromXML(xml);
    }

    /**
     * java 转换成xml
     * 
     * @Title: toXml
     * @param obj
     *            对象实例
     * @return String xml字符串
     */
    public static String toXml(Object obj) {
        XStream xstream = new XStream();
        // XStream xstream=new XStream(new DomDriver()); //直接用jaxp dom来解释
        // XStream xstream=new XStream(new DomDriver("utf-8")); //指定编码解析器,直接用jaxp dom来解释
        //// 如果没有这句，xml中的根元素会是<包.类名>；或者说：注解根本就没生效，所以的元素名就是类的属性
        xstream.processAnnotations(obj.getClass()); // 通过注解方式的，一定要有这句话
        return xstream.toXML(obj);
    }

    /**
     * 获取字符串类型的xml
     * 
     * @param xmlString
     * @return
     * @throws ParserConfigurationException
     * @throws IOException
     * @throws SAXException
     */
    public static Map<String, Object> getMapFromXML(String xmlString)
        throws ParserConfigurationException, IOException, SAXException {

        // 这里用Dom的方式解析回包的最主要目的是防止API新增回包字段
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        InputStream is = XmlUtil.getStringStream(xmlString);
        org.w3c.dom.Document document = builder.parse(is);

        // 获取到document里面的全部结点
        NodeList allNodes = document.getFirstChild().getChildNodes();
        Node node;
        Map<String, Object> map = new HashMap<String, Object>();
        int i = 0;
        while (i < allNodes.getLength()) {
            node = allNodes.item(i);
            if (node instanceof org.w3c.dom.Element) {
                map.put(node.getNodeName(), node.getNodeValue());
            }
            i++;
        }
        return map;

    }

    /**
     * 格式化xml
     * 
     * @param xml
     *            字符串类型的xml
     * @return
     * @throws UnsupportedEncodingException
     * @throws IOException
     * @throws DocumentException
     */
    public static String formatXml(String xml) throws UnsupportedEncodingException, IOException, DocumentException {
        SAXReader reader = new SAXReader();
        // 创建一个串的字符输入流
        StringReader in = new StringReader(xml);
        Document doc = reader.read(in);
        // log.info(doc.getRootElement());
        // 创建输出格式
        OutputFormat formater = OutputFormat.createPrettyPrint();
        // formater=OutputFormat.createCompactFormat();
        // 设置xml的输出编码
        formater.setEncoding("utf-8");
        // 创建输出(目标)
        StringWriter out = new StringWriter();
        // 创建输出流
        XMLWriter writer = new XMLWriter(out, formater);
        // 输出格式化的串到目标中，执行后。格式化后的串保存在out中。
        writer.write(doc);
        // 关闭流
        writer.close();
        // 返回我们格式化后的结果
        String result = out.toString();
        return result.replace("<", "&lt;").replace(">", "&gt;").replace("\n", "</br>");
    }

    /**
     * 获取字节数组输入流
     * 
     * @param sInputString
     * @return
     * @throws UnsupportedEncodingException
     */
    public static InputStream getStringStream(String sInputString) throws UnsupportedEncodingException {
        ByteArrayInputStream tInputStringStream = null;
        if (sInputString != null && !sInputString.trim().equals("")) {
            tInputStringStream = new ByteArrayInputStream(sInputString.getBytes("UTF-8"));
        }
        return tInputStringStream;
    }

}
