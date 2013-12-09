package com.asms.adventure;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import javax.xml.crypto.NodeSetData;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xmlpull.v1.XmlSerializer;

import com.asms.adventure.enums.CharacterType;

import android.app.Application;
import android.content.Context;
import android.util.Xml;

public class CharacterXMLManager {

	private static FileOutputStream outputStream;
	
	public static ArrayList<String[]> getCharacterList(Context context) {
		ArrayList<String[]> out = new ArrayList<String[]>();
		
		String filename = "characters.xml";
		File folder = context.getCacheDir();
		File xmlFile = new File(folder, filename);
			Document doc;
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			try {
				DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
				try {
					doc = dBuilder.parse(xmlFile);
				} catch(Exception e) {
					doc = dBuilder.newDocument();
					doc.appendChild(doc.createElement("characters"));
				}
				doc.getDocumentElement().normalize();
				
				NodeList characterNodes = doc.getElementsByTagName("character");
				for(int i = 0; i < characterNodes.getLength(); i++) {
					Element characterNode = (Element) characterNodes.item(i);
					String[] string = {
							characterNode.getAttribute("id"),
							characterNode.getAttribute("type"),
							characterNode.getAttribute("name")
					};
					out.add(string);
				}
				
				
				
			} catch(Exception e) { }
			
			return out;
	}

	public static void createNewCharacter(Context context, CharacterType characterType,
			String characterName) {
		String filename = "characters.xml";
		//File folder = context.getDir("characters", Context.MODE_PRIVATE);
		File folder = context.getCacheDir();
		File xmlFile = new File(folder, filename);
			Document doc;
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			try {
				DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
				try {
					doc = dBuilder.parse(xmlFile);
				} catch(Exception e) {
					doc = dBuilder.newDocument();
					doc.appendChild(doc.createElement("characters"));
				}
				doc.getDocumentElement().normalize();
				
				Element root = doc.getDocumentElement();
				Element characterNode = doc.createElement("character");
				characterNode.setAttribute("id", String.valueOf(new Date().getTime()));
				characterNode.setAttribute("type", characterType.name());
				characterNode.setAttribute("name", characterName);
				root.appendChild(characterNode);
				
				TransformerFactory transformerFactory = TransformerFactory.newInstance();
				Transformer transformer = transformerFactory.newTransformer();
				DOMSource source = new DOMSource(doc);
				StreamResult result = new StreamResult(xmlFile);
				transformer.transform(source, result);
				
			} catch(Exception e) { }
	}

	private static String writeXML(String string){
		XmlSerializer serializer = Xml.newSerializer();
		StringWriter writer = new StringWriter();
		try {
			serializer.setOutput(writer);
			serializer.startDocument("UTF-8", true);

			serializer.startTag("", "tag"); // <tag>
			serializer.attribute("", "attributeName", "this");
			serializer.text("text");
			serializer.endTag("", "tag"); // </tag>

			serializer.endDocument();
			return writer.toString();
		} catch (Exception e) {
			throw new RuntimeException(e);
		} 
	}

}
