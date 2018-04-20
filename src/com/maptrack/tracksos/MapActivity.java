package com.maptrack.tracksos;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.GestureDetector.OnGestureListener;
import android.view.View.OnTouchListener;
import android.view.WindowManager.LayoutParams;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.maptrack.tracksos.IClickFunc;

public class MapActivity extends Activity implements OnTouchListener, OnGestureListener{

	private static final String  	MAPURL = "file:///android_asset/map.html";
    private FloatGridView 			m_FGV = null;

	private GestureDetector 		m_GestureDetector = null;   
    private	WebView					m_WebView;
    private	int						m_WWHeight;
    
    private List<IClickFunc>		m_ListFunc = null;
    private int[]					m_listImage;
    private String[]				m_listText;
    private	Handler					m_handler;
    
    
    
    Timer timer = new Timer();  
    TimerTask task = new TimerTask(){   
        public void run() {  
            setTitle("hear me?");  
        }            
    };  

    
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
        setContentView(R.layout.map);
        
        m_listImage = new int[2];
        m_listText = new String[2];
        
        m_listImage[0] = R.drawable.setting;
        m_listImage[1] = R.drawable.setting;
        
        m_listText[0] = "set1";
        m_listText[1] = "set2";


        m_GestureDetector = new GestureDetector(this);   
        m_GestureDetector.setIsLongpressEnabled(true);       
        

        m_WWHeight = this.getWindowManager().getDefaultDisplay().getHeight();
        
        m_WebView = (WebView)this.findViewById(R.id.webView1);
        
        //this.view.setClickable(true);
       // this.view.setLongClickable(true);

        m_WebView.getSettings().setJavaScriptEnabled(true);  

        m_WebView.addJavascriptInterface(new JavaScriptInterface(), "App");
        m_WebView.loadUrl( MAPURL );
        m_ListFunc = new ArrayList<IClickFunc>();
        
        m_ListFunc.add(new IClickFunc(){
			@Override
			public void f() {
				Log.d("maptracktivy", "f1");
			}});

        m_ListFunc.add(new IClickFunc(){
			@Override
			public void f() {
				Log.d("maptracktivy", "f2");
			}});
        
        m_handler = new Handler() {  
            public void handleMessage(Message msg) {   
                 switch (msg.what) {
                 	case 1:
                 		break;
                 }
                 super.handleMessage(msg);   
            }   
       };    
       
       

       timer.schedule(task, 10000); 
	}
	

    private void showFloatView() {  
    	        
        if( m_FGV == null){
	        m_FGV = new FloatGridView( getApplicationContext() );
	        m_FGV.SetLayoutID(R.layout.button2, R.id.id_gridView, R.layout.iconbtn, R.id.id_imageView, R.id.id_textView);
	        m_FGV.SetResource(m_listImage, m_listText);
	        m_FGV.SetClickListener(m_ListFunc);
	        m_FGV.LoadView();
        }
        
        // ��ȡWindowManager  
        // ����LayoutParams(ȫ�ֱ�������ز���        
        WindowManager wm = (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE);        
        LayoutParams wmParams = new WindowManager.LayoutParams();    
        
        wmParams.type =  LayoutParams.TYPE_PHONE; // ����window type  
        wmParams.format = PixelFormat.RGBA_8888; // ����ͼƬ��ʽ��Ч��Ϊ����͸��  
  
        
        // �����flags���Ե�Ч����ͬ���������� ���������ɴ������������κ��¼�,ͬʱ��Ӱ�������¼���Ӧ�� 
        // ����Window flag  
        // wmParams.flags = LayoutParams.Flag_
        wmParams.flags = LayoutParams.FLAG_NOT_FOCUSABLE;  
        //wmParams.flags = LayoutParams.FLAG_NOT_TOUCH_MODAL | LayoutParams.FLAG_NOT_FOCUSABLE;  
        //wmParams.flags = LayoutParams.FLAG_NOT_TOUCH_MODAL |  LayoutParams.FLAG_NOT_FOCUSABLE | LayoutParams.FLAG_NOT_TOUCHABLE;
  
        wmParams.gravity = Gravity.LEFT | Gravity.TOP; // �����������������Ͻ�  
        
        wmParams.x = 0;
        wmParams.y = m_WWHeight;  
  
        // �����������ڳ�������  
        wmParams.width =  android.view.ViewGroup.LayoutParams.WRAP_CONTENT;  
        wmParams.height = android.view.ViewGroup.LayoutParams.WRAP_CONTENT;  
  
        wm.addView(m_FGV, wmParams);  
    }  
  
	private void hideFloatView() {
		if( m_FGV == null || m_FGV.isShown() == false){
			return;
		}
        WindowManager wm = (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE);  
        wm.removeView(m_FGV);		
	}

	@Override
	public boolean onDown(MotionEvent e) {
		// TODO Auto-generated method stub
		if( m_FGV == null || m_FGV.isShown() == false){
			showFloatView();
		}
		else{
			hideFloatView();			
		}
		return false;
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		// �������ͣ� 
        // e1����1��ACTION_DOWN MotionEvent 
        // e2�����һ��ACTION_MOVE MotionEvent 
        // velocityX��X���ϵ��ƶ��ٶȣ�����/�� 
        // velocityY��Y���ϵ��ƶ��ٶȣ�����/�� 
     
        // �������� �� 
        // X�������λ�ƴ���FLING_MIN_DISTANCE�����ƶ��ٶȴ���FLING_MIN_VELOCITY������/��  
        Log.d("OnGestureListener", "onFling");    
        
        final int FLING_MIN_DISTANCE = 100, FLING_MIN_VELOCITY = 200; 
        
        if (e1.getX() - e2.getX() > FLING_MIN_DISTANCE && Math.abs(velocityX) > FLING_MIN_VELOCITY) { 
            // Fling left  
            Log.i("MyGesture", "Fling left"); 
            Toast.makeText(this, "Fling Left", Toast.LENGTH_SHORT).show(); 
        } else if (e2.getX() - e1.getX() > FLING_MIN_DISTANCE && Math.abs(velocityX) > FLING_MIN_VELOCITY) { 
            // Fling right  
            Log.i("MyGesture", "Fling right"); 
            Toast.makeText(this, "Fling Right", Toast.LENGTH_SHORT).show(); 
            this.hideFloatView();
            this.finish();
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onShowPress(MotionEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		return m_GestureDetector.onTouchEvent(ev);
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		return false;
	}

	
	
	 public class JavaScriptInterface {
	      public JavaScriptInterface() {
	      }

	      public void closewin() { 
	      }

	      public void triggerEvent(String event)
	      {
	      }
	 }
}


/*
private Handler handler = new Handler();  

private Runnable myRunnable= new Runnable() {    
    public void run() {  
         
        if (run) {  
            handler.postDelayed(this, 1000);  
            count++;  
        }  
        tvCounter.setText("Count: " + count);  

    }  
}; 


handler.post(myRunnable);

handler.post(myRunnable,time);
*/
