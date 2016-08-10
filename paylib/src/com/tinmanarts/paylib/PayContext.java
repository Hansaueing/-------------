package com.tinmanarts.paylib;


import android.app.Activity;

import com.tinmanarts.paylib.entity.Commodity;


public class PayContext {

	public static void payment(Activity activity, Commodity commodity, int type, final IPayConfigImp payConfig,final IPayResultCallback callback){
		Pay pay = PayFactory.createPay(activity, type, payConfig);	
		pay.payment(commodity, callback);
	}
}
