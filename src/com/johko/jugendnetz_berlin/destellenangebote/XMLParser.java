package com.johko.jugendnetz_berlin.destellenangebote;

import java.io.InputStream;
import java.util.List;

import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import android.util.Log;
 
public class XMLParser {
 
    /*public static List<Content> parse(InputStream is) {
        List<Content> contents = null;
        try {
            // create a XMLReader from SAXParser
            XMLReader xmlReader = SAXParserFactory.newInstance().newSAXParser()
                    .getXMLReader();
            // create a SAXXMLHandler
            XmlHandler saxHandler = new XmlHandler();
            // store handler in XMLReader
            xmlReader.setContentHandler(saxHandler);
            // the process starts
            xmlReader.parse(new InputSource(is));
            // get the `Laptop list`
            contents = XmlHandler.getContent();
 
        } catch (Exception ex) {
            Log.d("XML", "SAXXMLParser: parse() failed");
            ex.printStackTrace();
        }
 
        // return Laptop list
        return contents;
    }*/
}
