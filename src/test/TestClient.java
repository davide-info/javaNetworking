package test;

import network.MyClient;

public class TestClient {

	public static void main(String[] args) {
		MyClient mc = new MyClient();
		final String url="https://www.youtube.com/watch?v=kUug01HEW94/";
		mc.requestAsync(url);
		System.out.println("TERMINATO");

	}


}
