package com.johko.jugendnetz_berlin.destellenangebote;

import java.util.ArrayList;
import java.util.List;

public class Content {

	//Title of message
	private List<String> title;
	
	//URL for content
	private List<String> url;
	
	
	public Content() {
		title = new ArrayList<String>();
		url = new ArrayList<String>();
	}
	
	public List<String> getTitle(){
		return this.title;
	}

	public void setTitle(String newsTitle){
		this.title.add(newsTitle);
	}
	
	public String getUrl(int pos){
		
		if(pos >= 0){
			return this.url.get(pos);
		} else{
			return "";
		}	
	}
	
	public void setUrl(String newsUrl){
		this.url.add(newsUrl);
	}
	
}
