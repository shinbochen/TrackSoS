package com.maptrack.tracksos;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;


public class FloatGridView extends LinearLayout {
	
	public 	 GridView			m_gridView = null;
	
	private  Context			m_context;
	
    private  List<IClickFunc>	m_listClickFunc = null;
    private	 String[]			m_listText;
    private	 int[]				m_listImage;		
    
    private	 int				m_containerLayout;
    private  int				m_viewID;
    
    private  int				m_childLayout;
    private  int				m_imageid;
    private  int				m_textid;
    
    
	
	public FloatGridView(Context context) {
		super(context);  
		m_context = context;        
	}
	
	public void LoadView( ){
  
        View view = View.inflate(m_context, m_containerLayout, null);  
        
        m_gridView = (GridView)view.findViewById( m_viewID );
        GridAdapter adapter = new GridAdapter( m_context, m_childLayout, m_imageid, m_textid, m_listImage, m_listText);
		
        m_gridView.setAdapter(adapter);
        m_gridView.setOnItemClickListener( GridItemClickListener );        
        this.addView(view);  
	}
	public void SetLayoutID( int containerLayout, int viewid, int childLayout, int imageid, int textid){
		m_containerLayout = containerLayout;
		m_viewID = viewid;
		
		m_childLayout = childLayout;
		m_imageid = imageid;
		m_textid = textid;
	}
	public void SetResource( int[] listImage, String[] listText){
		m_listImage = listImage;
		m_listText = listText;
	}
	public void SetClickListener( List<IClickFunc> funclist){
		m_listClickFunc = funclist;		
	}
	
	OnItemClickListener	GridItemClickListener = new OnItemClickListener(){

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
			IClickFunc func = m_listClickFunc.get(arg2);
			func.f();
		}	
	};
	
}

class GridAdapter extends BaseAdapter {
	
    private Context  			m_oContext = null;
    private	String[]			m_listText;
    private	int[]				m_listImage;			
    public	int					m_layoutid;
    public 	int					m_textid;
    public  int					m_imageid;
    public	int[][]				m_sourceid = null;
	
    public  GridAdapter( Context  oContext, int layoutid, int imageid, int textid,  int[] listImage, String[]  listText){
    	
    	m_oContext = oContext;      	
    	m_layoutid = layoutid;
    	m_textid = textid;
    	m_imageid = imageid;
    	m_listText = listText;
    	m_listImage =listImage;
    }
	@Override
	public int getCount() {
		return m_listImage.length;
	}
	@Override
	public Object getItem(int arg0) {
		return arg0;
	}
	@Override
	public long getItemId(int position) {
		return position;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {		
		
	   LayoutInflater li = (LayoutInflater) m_oContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	   convertView = li.inflate(m_layoutid, null);
	   ImageView iv = (ImageView)convertView.findViewById(m_imageid);
	   TextView tv = (TextView)convertView.findViewById( m_textid );
	   
	   iv.setImageResource( m_listImage[position] );
	   tv.setText( m_listText[position] );
       
       return convertView;

	}
}


