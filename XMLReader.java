package XML;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
public class XMLReader {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			String url = "http://dictionaryapi.com/api/v1/references/collegiate/xml/penis?key=633cbc13-6db6-4cc9-ae43-47b7557f30b0";
			URL obj = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) obj.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			
			String inputLine;
			StringBuffer content = new StringBuffer();
			while((inputLine=in.readLine())!=null) {
				content.append(inputLine);
			}
			in.close();
			
			System.out.println(content.toString());
			
			
			
			//File fXmlFile = new File("company.xml");
			//DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			//DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			//Document doc = dBuilder.parse(fXmlFile);
			Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new InputSource(new StringReader(content.toString())));
			//optional
			doc.getDocumentElement().normalize();
			
			System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
			
			//NodeList nList = doc.getElementsByTagName("staff");
			NodeList nList = doc.getElementsByTagName("entry");
			
			System.out.println("----------------------------");
			
			for (int temp = 0; temp < nList.getLength(); temp++) {

				Node nNode = nList.item(temp);
						
				System.out.println("\nCurrent Element :" + nNode.getNodeName());
						
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode;
					System.out.println(eElement.getAttribute("id"));
					System.out.println("Defintion: "+eElement.getElementsByTagName("dt").item(0).getTextContent());
					/*
					System.out.println("Staff id : " + eElement.getAttribute("id"));
					System.out.println("First Name : " + eElement.getElementsByTagName("firstname").item(0).getTextContent());
					System.out.println("Last Name : " + eElement.getElementsByTagName("lastname").item(0).getTextContent());
					System.out.println("Nick Name : " + eElement.getElementsByTagName("nickname").item(0).getTextContent());
					System.out.println("Salary : " + eElement.getElementsByTagName("salary").item(0).getTextContent());
					*/
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
		}

	}

}
