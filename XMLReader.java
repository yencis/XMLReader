package XML;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import org.json.*;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
public class XMLReader {
	
	public static void main(String[] args) {
		JSONreader();
	}
	
	
	//Comment this function out
	public static void JSONreader() {
		try {
			Scanner sc = new Scanner(System.in);
			String w = sc.next();
			String url = "https://od-api.oxforddictionaries.com/api/v2/entries/en-gb/"+w;
			URL obj = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) obj.openConnection();
			conn.setRequestProperty("app_id", "YOUR APP ID HERE");
			conn.setRequestProperty("app_key","YOUR APP KEY HERE");
			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String inputLine;
			StringBuffer content = new StringBuffer();
			while((inputLine=in.readLine())!=null) {
				content.append(inputLine);
			}
			in.close();
			
			//System.out.println(content.toString());
			JSONObject parser = new JSONObject(content.toString());
			try {
				JSONArray definition = parser.getJSONArray("results").getJSONObject(0).getJSONArray("lexicalEntries").getJSONObject(0).getJSONArray("entries").getJSONObject(0).getJSONArray("senses").getJSONObject(0).getJSONArray("definitions");
				System.out.println(definition.get(0));
			}catch(JSONException j) {
				System.out.println("Defintion unavailible (unused word)");
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	public static void xmlreader() {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		String prompt = sc.next();
		try {
			String url = "http://dictionaryapi.com/api/v1/references/collegiate/xml/"+prompt+"?key=key for dictionaryapi.com";
			//String url = "https://od-api.oxforddictionaries.com/api/v2/entries/en-gb/clock";
			URL obj = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) obj.openConnection();
			//conn.setRequestProperty("app_id", "YOUR ID");
			//conn.setRequestProperty("app_key","YOUR KEY");
			//System.out.println(conn.getHeaderField("app_key"));
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
