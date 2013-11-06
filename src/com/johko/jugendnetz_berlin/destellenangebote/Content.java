package com.johko.jugendnetz_berlin.destellenangebote;

public class Content {

	// Title of message
	private String title;

	// URL for content
	private String url;

	private String date;

	public Content() {
		title = "";
		url = "";
		date = "";
	}

	public String getTitle() {

		return this.title;
	}

	public String getTitle2(int position) {

		return title;
	}

	public void setTitle(String newsTitle) {
		this.title = newsTitle;

	}

	public String getUrl(int pos) {

		if (pos >= 0) {

			return this.url;
		} else {
			return "";
		}
	}

	public void setUrl(String newsUrl) {
		this.url = newsUrl;
	}

	public String getDate() {

		return this.date;
	}

	public String getDate2(int pos) {

		return date;
	}

	public void setDate(String newDate) {
		this.date = newDate;
	}

}
