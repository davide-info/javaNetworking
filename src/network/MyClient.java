package network;

import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;

import java.net.Socket;
import java.net.SocketAddress;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.time.LocalTime;
import java.util.concurrent.Future;


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
	
		InetSocketAddress socket=null;
		
		
		socket = new InetSocketAddress("localhost",2000); 
		
			
			try(SocketChannel channel = SocketChannel.open(socket)) {
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
	static class Attachment {
		AsynchronousSocketChannel channel;
		ByteBuffer buffer ;
		Thread mainThread ;
		boolean isRead ;
	}
	
	public void requestASyncChannel(String fileName) {
		try(AsynchronousSocketChannel socketChannel = AsynchronousSocketChannel.open()) {
			String serverName = "localhost";
			int serverPort = 2000 ;
			SocketAddress serverAddr = new InetSocketAddress(serverName,serverPort);
			Future<Void> result = socketChannel.connect(serverAddr);
		log("Connessione al server") ;
		result.get();
		Attachment attachment = new Attachment();
		attachment.channel = socketChannel;
		attachment.buffer = ByteBuffer.allocate(2048) ;
		attachment.isRead = false ;
		attachment.mainThread = Thread.currentThread();
		Charset cs = Charset.forName("UTF-8");
		String msg = LocalTime.now().toString() ;
		byte [] data = msg.getBytes(cs);
		attachment.buffer.put(data);
		attachment.buffer.flip();
		ReadWriteHandler readWriteHandler = new ReadWriteHandler();
		socketChannel.write(attachment.buffer, attachment, readWriteHandler);
		log("Scrittura socket 2");
		attachment.mainThread.join(); 
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	private void log(String msg) {
		System.out.println(msg);
		
	}
	class ReadWriteHandler implements CompletionHandler<Integer,Attachment>{

		@Override
		public void completed(Integer result, Attachment attachment) {
			
			// TODO Auto-generated method stub
			
		}

		@Override
		public void failed(Throwable exc, Attachment attachment) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
}
