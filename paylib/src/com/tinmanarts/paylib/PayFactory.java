package com.tinmanarts.paylib;

import android.app.Activity;

public class PayFactory {
	public static Pay createPay(Activity activity,int type, IPayConfigImp payConfig){
		if (type == 0) {
			return new WXPay(activity, payConfig);
		} else if (type == 1) {
			return new Alipay(activity, payConfig);
		} else{
			throw new IllegalArgumentException("confirm argument \"type\" is correct");
		}
		

		
	}
}
