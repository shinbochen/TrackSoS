package com.util;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.util.Log;

public class Util {
	
	static public Location getLocation( Context ctx ){
		String		pkgName = ctx.getPackageName();
		String 		serviceName = Context.LOCATION_SERVICE;
		double		latitude,longitude;
        String 		provider = null;
        Location 	location = null;
		
		int i = 0 == ctx.getPackageManager().checkPermission("android.permission.ACCESS_COARSE_LOCATION", pkgName) ? 1: 0;
		int	j = 0 == ctx.getPackageManager().checkPermission("android.permission.ACCESS_FINE_LOCATION", pkgName) ? 1: 0;
		if( (i != 0) && (j !=0) ){

			LocationManager locationManager = (LocationManager)ctx.getSystemService(Context.LOCATION_SERVICE);  
			
			Criteria criteria = new Criteria();
			// 设置Provider的精确度 高精度
			criteria.setAccuracy(Criteria.ACCURACY_FINE);
			// 设置Provider是否需要高度信息
			criteria.setAltitudeRequired(false);
			// 设置Provider是否需要方位信息
			criteria.setBearingRequired(false);
			// 设置Provider是否产生费用
			criteria.setCostAllowed(true);
			// 设置Provider是否需要速度信息
			criteria.setSpeedRequired(true);
			//criteria.setSpeedAccuracy( 3/*Criteria.ACCURACY_HIGH*/ );
			
			criteria.setPowerRequirement(Criteria.POWER_LOW); // 低功耗
			
			provider = locationManager.getBestProvider(criteria, true);
			if( provider == null ){
				Log.e("util.Util","No provider");
			}
			else{			
				Log.e("util.Util","have provider");
				location = locationManager.getLastKnownLocation(provider);
			}
		}
		else{
			Log.e("util","no Permission");
			
		}
		return location;
	}
	
	static public Boolean isGPSEnable( Context ctx ){
		
		LocationManager 	  locationManager;
        
        locationManager  = (LocationManager) ctx.getSystemService(Context.LOCATION_SERVICE); 
        // 通过GPS卫星定位，定位级别可以精确到街（通过24颗卫星定位，在室外和空旷的地方定位准确、速度快） 
        boolean gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        // 通过WLAN或移动网络(3G/2G)确定的位置（也称作AGPS，辅助GPS定位。主要用于在室内或遮盖物（建筑群或茂密的深林等）密集的地方定位） 
        boolean network = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        
        if (gps || network) { 
            return true; 
        }  
        else{
  			return false; 
        }
    }
	static public void openGPS( Context ctx ){
    	Intent intent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
		ctx.startActivity(intent);		
	}
	
	//获取本地IP函数
	static public String getLocalIPAddress(){
		try{
			for (Enumeration<NetworkInterface> mEnumeration = NetworkInterface.getNetworkInterfaces(); mEnumeration.hasMoreElements();){
				NetworkInterface intf = mEnumeration.nextElement();
				for (Enumeration<InetAddress> enumIPAddr = intf.getInetAddresses(); enumIPAddr.hasMoreElements();)
				{
					InetAddress inetAddress = enumIPAddr.nextElement();
					//如果不是回环地址
					if (!inetAddress.isLoopbackAddress())
					{
						//直接返回本地IP地址
						return inetAddress.getHostAddress().toString();
					}
				}
			}
		}
		catch (SocketException ex)
		{
			Log.e("Error", ex.toString());
		}
		return "";
	}
	
}
