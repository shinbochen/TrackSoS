package com.maptrack.tracksos;

import android.content.Context;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

public class FloatFrame extends android.widget.LinearLayout {
	private float mTouchRawX;  
	private float mTouchRawY;  
	  
	private WindowManager wm = (WindowManager) getContext().getApplicationContext().getSystemService(Context.WINDOW_SERVICE);  
	private WindowManager.LayoutParams wmParams = new WindowManager.LayoutParams();  
    private Context mContext;  
    View 	viewExtFrame;  
    Button 	btnExt;     
    private GestureDetector gestureDetector;  
    
	public FloatFrame(Context context) {
		super(context);
		mContext = context;  
        gestureDetector = new GestureDetector(context, gestureListener);  
  
  
        View view = View.inflate(context, R.layout.button, null);  
        //viewExtFrame = view.findViewById(R.id.id_btn_exit);  
        btnExt = (Button)view.findViewById(R.id.id_btn_ext);  
        int ids[] = { R.id.id_btn_ext, R.id.id_btn_exit };  
        for (int id : ids) {  
            view.findViewById(id).setOnClickListener(onClick);  
        }  
        //view.findViewById(R.id.id_btn_exit).setOnTouchListener(frameOnTouchListener);
        view.setOnTouchListener(frameOnTouchListener);
        
        this.addView(view);  
          
	}
	private void clickView(int id) {  
        switch (id) {  
  
        case R.id.id_btn_ext:  
            int visible = viewExtFrame.getVisibility();  
            if (visible == View.VISIBLE) {  
                viewExtFrame.setVisibility(View.INVISIBLE);  
                btnExt.setText("<<");  
            } else {  
                viewExtFrame.setVisibility(View.VISIBLE);  
                btnExt.setText(">>");  
            }  
              
            break;  
        case R.id.id_btn_exit:  
            wm.removeView(FloatFrame.this);// 点击退出，销毁悬浮窗口  
            Log.e("float view", "exit");  
            break;  
        default:  
  
        }  
    }  
	// 响应悬浮窗口中的Button点击  
    View.OnClickListener onClick = new View.OnClickListener() {  
  
        @Override  
        public void onClick(View v) {  
            // TODO Auto-generated method stub  
            //clickView(v.getId());  
        	Log.d("float view", "onclick");
        }  
    }; 
    // 点击Listener  
    OnTouchListener frameOnTouchListener = new OnTouchListener() {  
  
        public boolean onTouch(View v, MotionEvent event) {  
            if (null == gestureDetector || null == event) {  
                return false;  
            }  
            return gestureDetector.onTouchEvent(event);  
        }  
  
    };  
	// 手势识别  
    GestureDetector.OnGestureListener gestureListener = new GestureDetector.OnGestureListener() {  
  
        @Override  
        public boolean onDown(MotionEvent e) {  
            Log.d("OnGestureListener", "onDown");  
            mTouchRawX = e.getX();  
            mTouchRawY = e.getY();  
  
            return false;  
        }  
  
        @Override  
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {  
        	Log.d("OnGestureListener", "onFling");  
            return true;  
        }  
  
        @Override  
        public void onLongPress(MotionEvent e) {  
        	Log.d("OnGestureListener", "onLongPress");  
  
        }  
  
        @Override  
        public boolean onScroll(MotionEvent e1, MotionEvent e2,  
                float distanceX, float distanceY) {  
            mTouchRawX = e2.getRawX();  
            mTouchRawY = e2.getRawY();  
            //mHandler.sendEmptyMessage(MESSAGE_MOVE);  
  
            return true;  
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
  
    }; 
}
