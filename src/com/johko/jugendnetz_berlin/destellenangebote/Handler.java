package com.johko.jugendnetz_berlin.destellenangebote;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import android.util.Log;

public class Handler extends DefaultHandler {

	final private String ITEM = new String("item");
	
	final private String TITLE = new String("title");
	
	final private String LINK = new String("link");
	
	final private String LINK_ATTR_HREF = new String("href");
	
	private boolean itemBoolean;
	
	private boolean titleBoolean;
	
	private String contentTitle;
	
	private Content myContent;
	
	public Handler(Content content) {
		myContent = content;
	}
	
	@Override
	public void startDocument() throws SAXException{
		itemBoolean = false;
		titleBoolean = false;
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
			if(itemBoolean){
				Log.i(Handler.class.getSimpleName(), atts.getValue(this.LINK_ATTR_HREF));
				myContent.setUrl(atts.getValue(this.LINK_ATTR_HREF));
			}
		}
	}
	
	@Override
	public void endElement(String namespaceURI, String localName, String qName) throws SAXException{
		if(localName.equalsIgnoreCase(this.ITEM)){
			this.itemBoolean = false;
		} else if(localName.equalsIgnoreCase(this.TITLE)){
			this.titleBoolean = false;
			
			if(this.itemBoolean){
				Log.i(Handler.class.getSimpleName(), this.contentTitle);
				myContent.setTitle(this.contentTitle);
				this.contentTitle = "";
			}
		}
	}
	
	@Override
	public void characters(char ch[], int start, int length) throws SAXException {
		String textBetween = new String(ch, start, length);
		if(this.itemBoolean){
			if(this.titleBoolean){
				this.contentTitle = this.contentTitle + textBetween;
			}
		}
	}
	

}
