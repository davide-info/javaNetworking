package test;

import network.MyClient;

public class TestClient {

	public static void main(String[] args) {
		MyClient mc = new MyClient();
		final String url="http://mail.google.com/mail/u/0/";
		mc.requestAsync(url);
		System.out.println("TERMINATO");

	}


}
