package network1;

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
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		finally {
			
			
		}
		
		
	}
	
	public void testRequestAsync() {
		
		try(Selector selector = Selector.open()){
			try(	ServerSocketChannel crunchifySocket = ServerSocketChannel.open()){
				InetSocketAddress crunchifyAddr = new InetSocketAddress("localhost", 2000);
				crunchifySocket.bind(crunchifyAddr);
				crunchifySocket.configureBlocking(false);
				 
				int ops = crunchifySocket.validOps();
				while(true) {
					log("I'm a server and I'm waiting for new connection and buffer select...");
					selector.select();
					Set<SelectionKey> crunchifyKeys = selector.selectedKeys();
					System.out.println("KEYS " + crunchifyKeys.size());
					Iterator<SelectionKey> crunchifyIterator = crunchifyKeys.iterator();
		 
					while (crunchifyIterator.hasNext()) {
						
						SelectionKey myKey = crunchifyIterator.next();
						
		 
						// Tests whether this key's channel is ready to accept a new socket connection
						if (myKey.isAcceptable()) {
							SocketChannel crunchifyClient = crunchifySocket.accept();
		 
							// Adjusts this channel's blocking mode to false
							crunchifyClient.configureBlocking(false);
		 
							// Operation-set bit for read operations
							crunchifyClient.register(selector, SelectionKey.OP_READ);
							log("Connection Accepted: " + crunchifyClient.getLocalAddress() + "\n");
		 
							// Tests whether this key's channel is ready for reading
						} else if (myKey.isReadable()) {
							
							SocketChannel crunchifyClient = (SocketChannel) myKey.channel();
							
							// ByteBuffer: A byte buffer.
							// This class defines six categories of operations upon byte buffers:
							// Absolute and relative get and put methods that read and write single bytes;
							// Absolute and relative bulk get methods that transfer contiguous sequences of bytes from this buffer into an array;
							ByteBuffer crunchifyBuffer = ByteBuffer.allocate(256);
							crunchifyClient.read(crunchifyBuffer);
							String result = new String(crunchifyBuffer.array()).trim();
		 
							log("Message received: " + result);
		 
							if (result.isEmpty()) {
								crunchifyClient.close();
								log("\nIt's time to close connection as we got last company name 'Crunchify'");
								log("\nServer will keep running. Try running client again to establish new connection");
							}
						}
						crunchifyIterator.remove();
					}
				}
				}
				
			}catch(IOException e) {}
		
			
		
	}

	private void log(String msg) {
		System.out.println(msg);
		
	}
}


