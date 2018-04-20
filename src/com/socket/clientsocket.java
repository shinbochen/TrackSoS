package com.socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.BindException;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class clientsocket implements Runnable {
	public	Socket					socket = null;
	public 	InputStream 			in = null;
	public 	OutputStream			out = null;
	private String					ip;
	private int						port;
	public  Handler 				handler;
	public	ArrayList<byte[]>		list;
	private boolean					stoped;	
	
	public clientsocket( String strIP, int port) {
		try {
			if( this.in != null){
				this.in.close();
				this.in = null;
			}
			if( this.out != null ){
				this.out.close();
				this.out = null;
			}
			if( this.socket != null ){
				this.socket.close();
				this.socket = null;
			}
			this.ip = strIP;
			this.port = port;
			this.handler = null;
			this.list = new ArrayList<byte[]>();
			this.stoped = false;
		} 
		catch (IOException e){
			
			e.printStackTrace();
			Log.e("CloseSocket", "close execption:"+e.getMessage() );
		}
		// TODO Auto-generated constructor stub
	}
	public void		setHandler( Handler handler){
		this.handler = handler;		
	}
	public synchronized void		pushSendData( byte[] data ){
		list.add(data);		
	}
	public synchronized void		stoped( ){
		this.stoped = true;
	}
  	private boolean 	connect( String ip, int port ){
		boolean		result = false;
		try{				
			socket = new Socket();
			SocketAddress addr = new InetSocketAddress(ip, port);			
			socket.connect( addr , 10000 );
			
			if( socket.isConnected() ){
				result = true;
				in = socket.getInputStream();
	    		out = socket.getOutputStream();
			}
		}
		catch( ConnectException e ){
			e.printStackTrace();
			Log.e("Socket Connect",  "SocketTimeout:"+ e.getMessage() );
		}
    	catch( BindException e ){
    		e.printStackTrace();
    		Log.e("Socket Connect", "PortOpen:"+e.getMessage() );
    	}
		catch (UnknownHostException e){
			e.printStackTrace();
			Log.e("Socket Connect",  "UnknownHost:"+ e.getMessage() );			
		} 
		catch (IOException e) {
			e.printStackTrace();
			Log.e("Socket Connect",  "IOException:"+ e.getMessage() );			
		}
		return result;
	}
	private void  		send( byte[] data ){
		
		try {
			if( socket.isConnected() && !socket.isOutputShutdown() ){
				out.write( data );
			}
		}
    	catch( SocketException e ){		    		
    		e.printStackTrace();
    		Log.e("clientsocket.send", "socket error!"+e.getMessage() );
    	}
		catch (IOException e) {
			e.printStackTrace();
			Log.e("clientsocket.send", "socket disconnect!"+e.getMessage() );
		} 
	}
	private byte[]  	receive( ){
		
		byte[]		  	buf = null;
		int			  	len = 0;	
		
		if( this.socket.isConnected() && !this.socket.isInputShutdown() ){
			try {
				len = this.in.available();	
				
				
				if( len > 0 ){		
					buf = new byte[len];
					len = this.in.read(buf, 0, len );
				}
			} catch (IOException e) {					
				e.printStackTrace();
	    		Log.e("recvServerData","IO exeception:" + e.getMessage() );
			}
		}
		return buf;
	}
	public void 		run(){
		byte[]		recv = null;
		byte[]		send = null;
		int			nCnt = 0;
		while (true) {  
			try {
				if( this.stoped ){
					break;
				}
				Thread.sleep(2000);
				
				Message msg = new Message();
				msg.what = nCnt++;
				this.handler.sendMessage(msg);				
				
				Log.e("thread", "aa");
				/*if( this.socket.isConnected() ){
					
					if( this.list.size() > 0 ){
						send = this.list.get(0);
						if( send != null){
							this.send( send );
						}
						this.list.remove(0);
					}
					
					recv = this.receive();
					if( (recv != null) && (this.handler != null) ){
						Message msg = new Message();
						msg.getData().putByteArray("data", recv);
						this.handler.sendMessage(msg);					
					}
				}
				else{
					connect( this.ip, this.port);
				}*/
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			finally{
				
				
			}
		}
	}

}
