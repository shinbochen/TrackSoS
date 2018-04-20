package com.thread;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.ArrayList;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class TCPSocketThread extends Thread implements Serializable{
	
	private 	Socket				socket = null;
	private		InputStream	 		is = null;
	private		OutputStream	 	os = null;
	private		Handler 			handler;
	public		ArrayList<byte[]>		list;
	private		boolean				stoped;
	
	public TCPSocketThread( Socket socket){
		this.socket = socket;
		this.list  	= new ArrayList<byte[]>();
		this.stoped = false;
		try {
			this.is = this.socket.getInputStream();
			this.os = this.socket.getOutputStream();
		} catch (IOException e) {
			Log.d("TCPSocketThread", e.getMessage());
		}

		Log.d("TCPSocketThread", "construct ok");
	}

	public void		setHandler( Handler handler){
		this.handler = handler;		
	}
	
	public synchronized void pushSendData( byte[] buf){
		Log.d("TCPSocketThread", "pushSendData");
		this.list.add( buf );
	}
	
	public void run( ){
		int	len = 0;

		// ��Socket�����еõ�InputStream����
		// һ��ͨѶ�ܵ�����������,���Ƿ���������һ��Socket,�ͻ���Ҳ��һ��Socket,
		// ���ǾͿ���ͨ���������˵�Socket�����InputStream����ȡ�ͻ��������͵�����
		while( !stoped ){
			try {				
				
				Log.d( "TCPSocketThread", "run begin");
				Thread.sleep(500);
				
				if( this.list.size() > 0 ){
					Log.d( "TCPSocketThread", "have send data");
					this.os.write( this.list.get(0) );				
					this.list.remove(0);				
				}
				
				len = this.is.available();
				if( len > 0 ){
					Log.d( "TCPSocketThread", "have received data");
				
					byte data[] = new byte[len];
					len = this.is.read(data);
					
					
					String str = String.format("received len:%d", len);
					Log.d("TCPSocketThread", str );
		
					
		      	  	Bundle bd = new Bundle();
		      	  	bd.putByteArray("data", data);
		      	  	bd.putSerializable("thread", this);
		      	  	
		      	  	Message msg = new Message( );
		      	  	msg.setData( bd );
		      	  	msg.what = 1;
		      	  	if( this.handler != null){
		      	  		this.handler.sendMessage( msg );
		      	  	}
				}
				
				// ���socket�Ƿ��Ѿ��رա�����رվ��˳�
				try{
					this.socket.sendUrgentData(0xFF);
				}catch(Exception ex){
					Log.d( "TCPSocketThread", "run end!");
					stoped = true;
				}
	      	  	
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
				stoped = true;				
			}		
		}
		try {
			this.socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
