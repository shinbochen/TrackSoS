package com.thread;
import java.lang.Thread;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;

import com.util.Util;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class TcpServer extends Thread{
	
	private		ServerSocket serversocket = null;
	private		int			 port;	
	private		boolean		 stoped;
	private		Handler 	 handler = null;
	
	public TcpServer( int port ){
		this.port = port;
		this.stoped = false;	
		
	}
	public synchronized void		stoped( ) {
		this.stoped = true;

		Socket			s1 = new Socket();
		String			ip = Util.getLocalIPAddress();
		SocketAddress addr = new InetSocketAddress(ip, port);		
		try {
			s1.connect( addr , 200 );
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}

	public void		setHandler( Handler handler){
		this.handler = handler;		
	}
	
	public void run( ){
		try {
			serversocket = new ServerSocket(port);
		} catch (IOException e1) {
			e1.printStackTrace();
			this.stoped = true;
			return;
		}
		while( !stoped ){
			try{
				
				// 调用 ServerSocket对象的accept()方法接收客户端所发送的请求
				// a c c e p t这个方法是一个阻塞的方法，如果客户端没有发送请求
				// 那么代码运行到这里被阻塞，停在这里不再向下运行了，一直等待函数的返回,
				// 这时候突然客户端发送一个请求，那个这个方法就会返回Socket对象
				// Socket对象代表服务器端和客户端之间的一个连接

				Log.d("TCPServer", "server socket start");
				Socket socket = serversocket.accept();	
				if( !stoped ){
					Log.d("TCPServer", "socket accept");
					TCPSocketThread tcp = new TCPSocketThread( socket );	
					tcp.setHandler(this.handler);
					tcp.start();
				}
				else{
					Log.d("TCPServer", "socket accept but need close");					
				}
			}
			catch (IOException e)
			{
				Log.e("TCPServer",e.getMessage());
			}  
		}
		try {
			Log.d("TCPServer", "server socket close and thread end...");			
			serversocket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
