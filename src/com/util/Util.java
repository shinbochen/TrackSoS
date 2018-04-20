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
			// ����Provider�ľ�ȷ�� �߾���
			criteria.setAccuracy(Criteria.ACCURACY_FINE);
			// ����Provider�Ƿ���Ҫ�߶���Ϣ
			criteria.setAltitudeRequired(false);
			// ����Provider�Ƿ���Ҫ��λ��Ϣ
			criteria.setBearingRequired(false);
			// ����Provider�Ƿ��������
			criteria.setCostAllowed(true);
			// ����Provider�Ƿ���Ҫ�ٶ���Ϣ
			criteria.setSpeedRequired(true);
			//criteria.setSpeedAccuracy( 3/*Criteria.ACCURACY_HIGH*/ );
			
			criteria.setPowerRequirement(Criteria.POWER_LOW); // �͹���
			
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
        // ͨ��GPS���Ƕ�λ����λ������Ծ�ȷ���֣�ͨ��24�����Ƕ�λ��������Ϳտ��ĵط���λ׼ȷ���ٶȿ죩 
        boolean gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        // ͨ��WLAN���ƶ�����(3G/2G)ȷ����λ�ã�Ҳ����AGPS������GPS��λ����Ҫ���������ڻ��ڸ������Ⱥ��ï�ܵ����ֵȣ��ܼ��ĵط���λ�� 
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
	
	//��ȡ����IP����
	static public String getLocalIPAddress(){
		try{
			for (Enumeration<NetworkInterface> mEnumeration = NetworkInterface.getNetworkInterfaces(); mEnumeration.hasMoreElements();){
				NetworkInterface intf = mEnumeration.nextElement();
				for (Enumeration<InetAddress> enumIPAddr = intf.getInetAddresses(); enumIPAddr.hasMoreElements();)
				{
					InetAddress inetAddress = enumIPAddr.nextElement();
					//������ǻػ���ַ
					if (!inetAddress.isLoopbackAddress())
					{
						//ֱ�ӷ��ر���IP��ַ
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
