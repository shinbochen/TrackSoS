package com.maptrack.tracksos;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import com.socket.clientsocket;
import com.thread.TCPSocketThread;
import com.thread.TcpServer;
import com.thread.UdpServer;
import com.util.Util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.WindowManager.LayoutParams;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class TrackSOSActivity extends Activity implements OnTouchListener, OnGestureListener {
	public	Socket				socket1 = null;
	public	boolean				flag;
	public 	Handler 			handler;
	public 	Thread				thread;
	
	private GestureDetector 	mGestureDetector = null;   
	
	private	TcpServer			tcpServer=null;
	private	UdpServer			udpServer=null;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

		//Intent 	intent = new Intent();
	    //intent.setClass( this, MapActivity.class );	   
		//startActivityForResult(intent, 3 );	
		
        

        /*TextView tv = (TextView) findViewById(R.id.textView);
        //设置tv的监听器  
        tv.setOnTouchListener(this); 
        tv.setFocusable(true);
        //必须，view才能够处理不同于Tap（轻触）的hold
        tv.setClickable(true); 
        tv.setLongClickable(true); */
        
        mGestureDetector = new GestureDetector(this);   
        mGestureDetector.setIsLongpressEnabled(true);                

        this.handler = new Handler() {
          public void handleMessage(Message msg) {
        	  String		str;
        	  switch( msg.what){
        	  case 1:		// TCP Data
            	  Bundle bd = msg.getData();
            	  
            	  byte[] data = bd.getByteArray("data");            	  
            	  str = String.format("len:%d", data.length);            	  
            	  Log.d("handler", str);          
            	  

            	  
            	  byte[] data1 = new byte[5];
            	  
            	  TCPSocketThread th = (TCPSocketThread)bd.getSerializable("thread");
            	  th.pushSendData(data1);
            	  
        		  break;        	  
        	  
        	  default:
        		  break;
        	  }
          }
        };
               
        
        /*btnStart.setOnClickListener( new View.OnClickListener() {
			

			@Override
			public void onClick(View v) {
				Button b = (Button)v;
				
				if( tcpServer == null ){
					tcpServer = new TcpServer(8000);
					tcpServer.setHandler(handler);
					tcpServer.start();
					b.setText("server stop");
					
				}
				else{
					tcpServer.stoped();
					tcpServer = null;
					b.setText("server start");
				}
				
				
				if( !flag ){
					b.setText("stop");
					socket = new clientsocket("192.168.0.111",8888);
					socket.setHandler( handler );
					if( thread == null || !thread.isAlive() ){
						thread = new Thread(socket,"ppp");
					}
					
					thread.start();
					
				}
				else{
					socket.stoped();
					b.setText("start");
				}
				Location location = Util.getLocation( getApplicationContext() );
				
				if( location != null){
					String		str =new String();
					str = String.format("%f,%f", location.getLongitude(), location.getLatitude() );
					text.setText(str);
					
				}
				else{
					text.setText("no location");
					
				}
				flag = !flag;
			}
		});
        btnsend.setOnClickListener( new View.OnClickListener() {
		
			@Override
			public void onClick(View v) {

				Button b = (Button)v;
				if( null == socket1){
				
					b.setText("..stop..");
					socket1 = new Socket();
					
					String			ip = Util.getLocalIPAddress();
					text.setText(ip);
					SocketAddress addr = new InetSocketAddress(ip, 8000);		
					
					try {
						socket1.connect( addr , 2000 );
						
	
						Log.d("TrackSOSActivity", "connect complete");
						byte[] buf = new byte[4];
						
						buf[0] = 1;
						buf[1] = 2;
						buf[2] = 3;
						buf[3] = 4;
						
						
						
						//String		str = Byte.toString(buf[0]);
						
						//System.arraycopy(byte[]("hello world!"), 0, buf, 0, 13);
	
						//Log.d("TrackSOSActivity", "ready send data"+str);
	
						OutputStream			out = socket1.getOutputStream();
						
						out.write( buf );
						Log.d("TrackSOSActivity", "send data complete!");
						
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				else{
					b.setText("..send..");
					try {
						socket1.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
					Log.d("TrackSOSActivity", "socket1.close");
					socket1 = null;
					
					
				}
			}
		});
        btnUdpServer.setOnClickListener( new View.OnClickListener(){

			@Override
			public void onClick(View v) {
				
			}
        	
        });*/
    }
    

	public static void close(Context context) {
		//if (view_obj != null && view_obj.isShown()) {
		//	WindowManager wm = (WindowManager) context.getSystemService(Activity.WINDOW_SERVICE);
		//	wm.removeView(view_obj);
		//}
	}
    
    public static void show(Context context, View floatingViewObj) {
    	
    	WindowManager		wm;
    	LayoutParams		params = null;
    	// 加载xml文件中样式例子代码
    	// ********************************Start**************************
    	// LayoutInflater inflater =LayoutInflater.from(getApplicationContext());
    	// View view = inflater.inflate(R.layout.topframe, null);
    	// wm =WindowManager)context.getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
    	// 加载xml文件中样式例子代码
    	// *********************************End***************************
    	//
    	// 关闭浮动显示对象然后再显示
    	close(context);
    	//FloatingFunc.floatingViewObj = floatingViewObj;


    	//view_obj = floatingViewObj;
    	Rect frame = new Rect();
    	// 这一句是关键，让其在top 层显示
    	// getWindow()
    	// window.getDecorView().getWindowVisibleDisplayFrame(frame);
    	//TOOL_BAR_HIGH = frame.top;


    	wm = (WindowManager) context.getSystemService(Activity.WINDOW_SERVICE);
    	params.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT | WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY;
    	params.flags = LayoutParams.FLAG_NOT_TOUCH_MODAL | LayoutParams.FLAG_NOT_FOCUSABLE;


    	// 设置悬浮窗口长宽数据
    	params.width = WindowManager.LayoutParams.WRAP_CONTENT;
    	params.height = WindowManager.LayoutParams.WRAP_CONTENT;
    	// 设定透明度
    	params.alpha = 80;
    	// 设定内部文字对齐方式
    	params.gravity = Gravity.LEFT | Gravity.TOP;

    	// 以屏幕左上角为原点，设置x、y初始值ֵ
    	params.x = (int) (wm.getDefaultDisplay().getWidth() - params.width);
    	params.y = (int) 0;
    	// tv = new MyTextView(TopFrame.this);
    	wm.addView(floatingViewObj, params);
    	}


	@Override
	public boolean onDown(MotionEvent e) {
        Log.d("OnGestureListener", "onDown");   
        //Toast.makeText(this, "onDown", Toast.LENGTH_SHORT).show();  
        return false;  
	}


	@Override
	// 用户按下触摸屏、快速移动后松开，由1个MotionEvent ACTION_DOWN, 多个ACTION_MOVE, 1个ACTION_UP触发  
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,	float velocityY) {
		// 参数解释： 
        // e1：第1个ACTION_DOWN MotionEvent 
        // e2：最后一个ACTION_MOVE MotionEvent 
        // velocityX：X轴上的移动速度，像素/秒 
        // velocityY：Y轴上的移动速度，像素/秒 
     
        // 触发条件 ： 
        // X轴的坐标位移大于FLING_MIN_DISTANCE，且移动速度大于FLING_MIN_VELOCITY个像素/秒  
        Log.d("OnGestureListener", "onFling");    
        
        final int FLING_MIN_DISTANCE = 100, FLING_MIN_VELOCITY = 200; 
        
        if (e1.getX() - e2.getX() > FLING_MIN_DISTANCE && Math.abs(velocityX) > FLING_MIN_VELOCITY) { 
            // Fling left  
            Log.i("MyGesture", "Fling left"); 
            Toast.makeText(this, "Fling Left", Toast.LENGTH_SHORT).show(); 

    		Intent 	intent = new Intent();
    	    intent.setClass( this, MapActivity.class );	   
    		startActivityForResult(intent, 3 );	
        } else if (e2.getX() - e1.getX() > FLING_MIN_DISTANCE && Math.abs(velocityX) > FLING_MIN_VELOCITY) { 
            // Fling right  
            Log.i("MyGesture", "Fling right"); 
            Toast.makeText(this, "Fling Right", Toast.LENGTH_SHORT).show(); 
        } else if(e2.getY()-e1.getY()>FLING_MIN_DISTANCE && Math.abs(velocityY)>FLING_MIN_VELOCITY) {
            // Fling down  
            Log.i("MyGesture", "Fling down"); 
            Toast.makeText(this, "Fling down", Toast.LENGTH_SHORT).show();
        } else if(e1.getY()-e2.getY()>FLING_MIN_DISTANCE && Math.abs(velocityY)>FLING_MIN_VELOCITY) {
            // Fling up  
            Log.i("MyGesture", "Fling up"); 
            Toast.makeText(this, "Fling up", Toast.LENGTH_SHORT).show();
        } 
       
       
        return false;
	}


	@Override
	public void onLongPress(MotionEvent e) {
        Log.d("OnGestureListener", "onLongPress");    
		// TODO Auto-generated method stub
		
	}


	@Override
    // 用户按下触摸屏，并拖动，由1个MotionEvent ACTION_DOWN, 多个ACTION_MOVE触发  
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        Log.d("OnGestureListener", "onScroll");   
		return false;
	}


	@Override
	public void onShowPress(MotionEvent e) {
        Log.d("OnGestureListener", "onShowPress");   
		
	}


	@Override
	public boolean onSingleTapUp(MotionEvent e) {
        Log.d("OnGestureListener", "onSingleTapUp");   
		return false;
	}


	/*@Override
	public boolean onTouch(View v, MotionEvent event) {
		Log.d("main", "ontouch");
		return mGestureDetector.onTouchEvent(event);
	}*/


	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		return mGestureDetector.onTouchEvent(ev);
	}


	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		return false;
	}
}