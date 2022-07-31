package network;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class MyServer {
	public void testRequest() {
		
		Socket socket = null;
		
		try(ServerSocket servSock = new ServerSocket(2000)) {
			
			 socket = servSock.accept();
			 System.out.println("SOCKET "+socket);
			 System.out.println("SOCKET CLOSED " + socket.isClosed());
			 System.out.println("SERVSOCK "+ servSock.isClosed());
			 
		
			 BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			 BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			 
			 System.out.println("READER TEST REQUEST "+reader);
			 StringBuffer lineStr = new StringBuffer();
			 
			 String line="";
			 while((line = reader.readLine())!=null) {
				 lineStr.append(line);
			 }
			 
			 writer.append("Messaggio " + lineStr.toString()+ " Ricevuto dal client");
			 
			 System.out.println("LINESTR "+lineStr);
			 } catch (IOException e) {
					System.out.println("ECCEZIONE " + e);
				}
		finally {
			
			
		}
		
		
	}
	
	public void testRequestAsync() {
		try(ServerSocketChannel serverSocket = ServerSocketChannel.open()) {
			SocketChannel client = null;
			serverSocket.bind(new InetSocketAddress(2000));
			client = serverSocket.accept();
			ByteBuffer buffer = ByteBuffer.allocate(1024);
			while(client.read(buffer)>0) {
				buffer.flip();
				System.out.println(new String(buffer.array()));
				buffer.clear();
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private void log(String msg) {
		System.out.println(msg);
		
	}
}


