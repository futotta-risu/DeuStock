package com.futtosarisu.deustock.data;


public class SocialNetworkMessage {

	private String author = null;

	private String message = null;

	private float sentiment = 0;

	public SocialNetworkMessage() {}

	public SocialNetworkMessage(String author, String message) {
		this.message = message;
		this.author = author;
	}

	public SocialNetworkMessage(String message) {
		this.message = message;
	}

	public SocialNetworkMessage setText(String message){
		this.message = message;
		return this;
	}
	public SocialNetworkMessage setAuthor(String author){
		this.author = author;
		return this;
	}
	
}
