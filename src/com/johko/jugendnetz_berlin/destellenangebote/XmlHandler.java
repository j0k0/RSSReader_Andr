package com.johko.jugendnetz_berlin.destellenangebote;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import android.util.Log;

public class XmlHandler extends DefaultHandler {

	final private String ITEM = new String("item");

	final private String TITLE = new String("title");

	final private String LINK = new String("link");

	final private String DATE = new String("pubDate");

	private boolean itemBoolean;

	private boolean titleBoolean;

	private boolean linkBoolean;

	private boolean dateBoolean;

	private String contentTitle;

	private String dateTitle;

	public static ArrayList<Content> myContent = new ArrayList<Content>();

	private Content nowCont;

	public XmlHandler(Content content) {
		nowCont = content;

	}

	@Override
	public void startDocument() throws SAXException {
		itemBoolean = false;
		titleBoolean = false;
		linkBoolean = false;
		dateBoolean = false;
		contentTitle = "";
		dateTitle = "";
	}

	@Override
	public void endDocument() throws SAXException {

	}

	@Override
	public void startElement(String namespaceURI, String localName,
			String qName, Attributes atts) throws SAXException {
		if (localName.equalsIgnoreCase(this.ITEM)) {
			this.itemBoolean = true;
			nowCont = new Content();
		} else if (localName.equalsIgnoreCase(this.TITLE)) {
			this.titleBoolean = true;
		} else if (localName.equalsIgnoreCase(this.LINK)) {
			this.linkBoolean = true;
		} else if (localName.equalsIgnoreCase(this.DATE)) {
			this.dateBoolean = true;
		}
	}

	@Override
	public void endElement(String namespaceURI, String localName, String qName)
			throws SAXException {
		if (localName.equalsIgnoreCase(this.ITEM)) {
			this.itemBoolean = false;
			myContent.add(nowCont);
		} else if (localName.equalsIgnoreCase(this.TITLE)) {
			this.titleBoolean = false;
			if (this.itemBoolean) {
				Log.i(XmlHandler.class.getSimpleName(), this.contentTitle);
				nowCont.setTitle(this.contentTitle);
				this.contentTitle = "";
			}
		} else if (localName.equalsIgnoreCase(this.LINK)) {
			linkBoolean = false;
		} else if (localName.equalsIgnoreCase(this.DATE)) {
			Log.i(XmlHandler.class.getSimpleName(), this.dateTitle);
			nowCont.setDate(this.dateTitle);
			this.dateTitle = "";
		}

	}

	@Override
	public void characters(char ch[], int start, int length)
			throws SAXException {
		String textBetween = new String(ch, start, length);
		String newUrl = "";
		if (this.itemBoolean) {
			if (this.titleBoolean) {
				this.contentTitle = this.contentTitle + textBetween;
			} else if (this.linkBoolean) {
				//to get to the mobile version of the website cut of the 'www' and replace by 'm'
				for (int i = start; i < (start + length); i++) {
					if ((new String(ch, i, 3)).equals("www")) {
						newUrl += "m";
						i = i + 2;
					} else {
						newUrl += ch[i];
					}
				}
				nowCont.setUrl(newUrl);
			} else if (this.dateBoolean) {
				//to only get the date, when the ',' is read, get the next 11 characters
				for (int i = start; i < (start + length); i++) {
					if (ch[i] == ',') {
						dateTitle = new String(ch, i + 2, 11);
					}
				}
			}
		}

	}

}
