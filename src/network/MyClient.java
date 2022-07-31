package network;

import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;

import java.net.Socket;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.time.LocalTime;


public class MyClient {
	public void request(String fileName) {
		
		try(Socket  socket = new Socket("localhost",2000)) {
		
		// socket.connect(InetSocketAddress.createUnresolved("localhost", 3000));
		 

		OutputStream streamW = socket.getOutputStream();
		
		String pageContent = this.getHtmlPageFromURl(fileName);
		String msg = LocalTime.now().toString()+ " " + pageContent;
		streamW.write(msg.getBytes());
		System.out.println("MESSAGGIO INVIATO SERVER "+msg);
		
		
		}catch(Exception e) {}
	
	}
	public void requestAsync(String fileName) {
		SocketChannel channel= null;
		InetSocketAddress socket=null;
		try	{
		
		socket = new InetSocketAddress("localhost",2000); 
		
			
			channel = SocketChannel.open(socket);
			String content = getHtmlPageFromURl(fileName);
			System.out.println("CONTENUTO " + content);
			byte [] byteMsg = content.getBytes();
			ByteBuffer buffer = ByteBuffer.wrap(byteMsg);
			
			channel.write(buffer);
			buffer.clear();
			 
			// wait for 2 seconds before sending next message
			Thread.sleep(2000);
			
			 
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private String getHtmlPageFromURl(String fileName) {
		StringBuilder result = new StringBuilder();
		BufferedReader reader=null;
		
		try {
			URL url = new URL(fileName);
			System.out.println("URL " + url);
			 reader = new BufferedReader(new InputStreamReader(url.openStream()));
			String line = "";
			while((line = reader.readLine())!=null) {
			
				result.append(line);
				
			}
		
			
		} catch (IOException e) {
			System.err.println("ECCEZIONE "+e);
			System.exit(1);
		}
		finally {
			if(reader!=null) {
				try {
					reader.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return result.toString();
		
	}
	
}
