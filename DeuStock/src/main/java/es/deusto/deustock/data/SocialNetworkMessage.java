package es.deusto.deustock.data;

/**
 * Social Network generic message class.
 * Represent posts of different types of social networks s.a. Twitter, Reddit, etc.
 *
 * @author Erik B. Terres
 */
public class SocialNetworkMessage {

	private String author = null;
	private String message = null;
	private double sentiment = 0;

	public SocialNetworkMessage(){}

	public SocialNetworkMessage(String author, String message) {
		this.message = message;
		this.author = author;
	}

	public SocialNetworkMessage(String message) {
		this.message = message;
	}

	public SocialNetworkMessage setMessage(String message){
		this.message = message;
		return this;
	}

	public String getMessage(){
		return this.message;
	}

	public SocialNetworkMessage setAuthor(String author){
		this.author = author;
		return this;
	}

	public void setSentiment(double sentiment){this.sentiment = sentiment;}
	public double getSentiment(){return this.sentiment;}


	@Override
	public String toString() {
		return author + ":\n" + message + '\n' +"Sentiment=" + sentiment + "\n";
	}
}
