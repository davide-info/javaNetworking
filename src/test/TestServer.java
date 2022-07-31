package test;

import network.MyServer;

public class TestServer {

	public static void main(String[] args) {
		MyServer ms = new MyServer();
		ms.testRequestAsync();
		System.out.println("SERVER FINITO");

	}

}
