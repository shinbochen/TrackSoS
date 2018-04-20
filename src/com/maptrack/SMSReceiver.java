package com.maptrack;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;



public class SMSReceiver extends BroadcastReceiver {

	public SMSReceiver() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		
		
		if( intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED") ){

			Bundle		bundle = intent.getExtras();
			int			i = 0;

			if( bundle == null ){
				return ;
			}
			Object[] 		pdus = (Object[]) bundle.get("pdus");
			SmsMessage[] 	msgs = new SmsMessage[pdus.length];
			for ( Object pdu : pdus) {
				msgs[i++] = SmsMessage.createFromPdu( (byte[]) pdu );
			}

			for( SmsMessage msg : msgs  ){	
				msg.getDisplayOriginatingAddress();
				msg.getPdu();				
			}
		}
	}

}
