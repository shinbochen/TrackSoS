package com.thread;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;


class UdpData{
	
	public InetAddress  ip;
	public int			port;
	public byte[]		dataBuf = null;
	public	int			dataLen = 0;
	
	public UdpData( String ip, int port, byte[] buf, int nLen){
		try {
			this.ip = InetAddress.getByName( ip );
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		this.port = port;
		this.dataBuf = new byte[nLen];
		this.dataLen = nLen;
		System.arraycopy(buf, 0, this.dataBuf, 0, nLen);		
	}	
}

public class UdpServer extends Thread {
	
	private static final int DATA_LEN = 4096;
	
	private byte[] 			inbuf = null;
	private int 			port = 30000;
	private DatagramPacket 	inPacket = null;
	private DatagramSocket 	socket = null;  
	public  Handler 		handler = null;
	public	ArrayList<UdpData>		list;
	
	private boolean		stoped;	
	
	
	
	public UdpServer( int port ){		
		this.port = port;	
		this.inbuf = new byte[ UdpServer.DATA_LEN ];
		this.inPacket = new DatagramPacket(inbuf , inbuf.length);
		this.list = new ArrayList<UdpData>();
		this.stoped = false;
	}
	public synchronized void pushSendData( String ip, int port, byte[] buf, int len){
		UdpData data = new UdpData(ip, port, buf, len);
		list.add( data );		
	}
	public void		setHandler( Handler handler){
		this.handler = handler;		
	}
	public synchronized void		stoped( ){
		this.stoped = true;
	}
	public void run( ){
		UdpData 		udata;
		DatagramPacket	outPacket;

		while( !stoped ){
			try {
				this.socket = new DatagramSocket(port);
				while( list.size() > 0 ){
					
					udata = list.get( 0 );
					outPacket = new DatagramPacket( udata.dataBuf, udata.dataLen, udata.ip, udata.port );
					socket.send(outPacket);
					this.list.remove( 0 );
				}		
				
				socket.receive(inPacket);
				
				if( inPacket.getLength() > 0 ){
					
	
		            // 通过数据报得到发送方的IP和端口号，并打印  
		            InetAddress sendIP = inPacket.getAddress();  
					
					Bundle data = new Bundle();
					data.putString("ip", sendIP.getHostAddress());
					data.putInt("port", inPacket.getPort());
					data.putByteArray("data", inPacket.getData());
	
					Message msg = new Message();
					msg.setData(data );
					msg.what = 0;
					this.handler.sendMessage(msg);				
				}
	  
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
		}
		
		socket.close();
	}
}

