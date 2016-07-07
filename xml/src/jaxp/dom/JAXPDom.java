package jaxp.dom;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class JAXPDom {
	
	//org.w3c.dom w3c��׼�ӿ�
	//org.xml.sax sax�ӿ�
	//javax.xml.parse jaxp����ʵ����
	
	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException, TransformerException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document document = builder.parse(new File("src\\books.xml"));
		//��ӡ�ڶ����������
		dumpSecondAuthor(document);
		//��������Ԫ�ؽڵ�
		listAllElements(document);
		//�޸�ĳ��Ԫ�ؽڵ�����
		updateElement(document);
		//���ӽڵ�
		insertElement(document, document.getFirstChild());
		
	}

	private static void insertElement(Document document, Node node) throws TransformerException {
		Attr id = document.createAttribute("id");
		id.setTextContent("10013");
		Element book = document.createElement("book");
		book.setAttributeNode(id);
		Element title = document.createElement("title");
		title.setTextContent("Java�������");
		Element author = document.createElement("author");
		author.setTextContent("benqcz");
		Element price = document.createElement("price");
		price.setTextContent("100.00");
		book.appendChild(title);
		book.appendChild(author);
		book.appendChild(price);
		node.appendChild(book);
		TransformerFactory factory = TransformerFactory.newInstance();
		Transformer transformer = factory.newTransformer();
		transformer.transform(new DOMSource(document), new StreamResult("src\\books.xml"));
	}

	private static void updateElement(Document document) throws TransformerException {
		document.getElementsByTagName("author").item(1).setTextContent("sue");
		TransformerFactory factory = TransformerFactory.newInstance();
		Transformer transformer = factory.newTransformer();
		transformer.transform(new DOMSource(document), new StreamResult("src\\books.xml"));
	}

	private static void dumpSecondAuthor(Document document) {
		NodeList nodes = document.getElementsByTagName("author");
		if (nodes != null)
			System.out.println(nodes.item(1).getTextContent());
	}
	
	public static void listAllElements(Node node) {
		if (Node.ELEMENT_NODE == node.getNodeType())
			System.out.println(node.getNodeName());
		NodeList nodes = node.getChildNodes();
		if (nodes != null)
			for (int i = 0; i < nodes.getLength(); i++)
				listAllElements(nodes.item(i));
	}
	
}