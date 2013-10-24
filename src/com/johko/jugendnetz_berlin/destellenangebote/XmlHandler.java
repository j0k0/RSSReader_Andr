package com.johko.jugendnetz_berlin.destellenangebote;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import android.util.Log;

public class XmlHandler extends DefaultHandler {

	final private String ITEM = new String("item");
	
	final private String TITLE = new String("title");
	
	final private String LINK = new String("link");
		
	private boolean itemBoolean;
	
	private boolean titleBoolean;
	
	private boolean linkBoolean;
	
	private String contentTitle;
	
	private Content myContent;
	
	public XmlHandler(Content content) {
		myContent = content;
	}
	
	/*public static void main(String[] args) {
		Content con = new Content();
		Handler tst = new Handler(con);
		System.out.println(atts.getValue(LINK));

	}*/
	
	@Override
	public void startDocument() throws SAXException{
		itemBoolean = false;
		titleBoolean = false;
		linkBoolean = false;
		contentTitle = "";
	}
	
	@Override
	public void endDocument() throws SAXException{
		
	}
	
	@Override
	public void startElement(String namespaceURI, String localName, String qName, Attributes atts) throws SAXException{
		if(localName.equalsIgnoreCase(this.ITEM)){
			this.itemBoolean = true;
		} else if(localName.equalsIgnoreCase(this.TITLE)){
			this.titleBoolean = true;
		} else if (localName.equalsIgnoreCase(this.LINK)){
			this.linkBoolean = true;
		}
	}
	
	@Override
	public void endElement(String namespaceURI, String localName, String qName) throws SAXException{
		if(localName.equalsIgnoreCase(this.ITEM)){
			this.itemBoolean = false;
		} else if(localName.equalsIgnoreCase(this.TITLE)){
			this.titleBoolean = false;
			
			if(this.itemBoolean){
				Log.i(XmlHandler.class.getSimpleName(), this.contentTitle);
				myContent.setTitle(this.contentTitle);
				this.contentTitle = "";
			}
		} else if(localName.equalsIgnoreCase(this.LINK)){
			linkBoolean = false; 
		}
	}
	
	@Override
	public void characters(char ch[], int start, int length) throws SAXException {
		String textBetween = new String(ch, start, length);
		String newUrl = "";
		String help = "";
		if(this.itemBoolean){
			if(this.titleBoolean){
				this.contentTitle = this.contentTitle + textBetween;
			} else if(this.linkBoolean){
				for(int i = start; i < (start+length); i++){
					if((help = new String(ch, i, 3)).equals("www")){
						newUrl += "m";
						i = i+2;
					} else {
						newUrl += ch[i];
					}
				}
				myContent.setUrl(newUrl);
				System.out.println(newUrl);
			}
		}
	}
	

}
