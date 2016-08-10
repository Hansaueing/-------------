package com.tinmanarts.paylib;


import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tinmanarts.paylib.entity.OrderInfo;
import com.tinmanarts.paylib.entity.WechatOderInfo;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Handler;
import android.os.Message;


public class WXPay extends Pay{

	private IWXAPI api;	//调用支付的sdk参数
	public static Handler handler;
	
	
	@SuppressLint("HandlerLeak")
	public WXPay(Activity activity, IPayConfigImp payConfig) {
		super(activity, payConfig);

		handler = new Handler(){
			//处理WXPayEntryActivity的支付结果
			@Override
			public void handleMessage(Message msg) {
				if(msg.what==BaseResp.ErrCode.ERR_OK){
					//getPayResult
					getPayResult(commodity);
				}else{
					callback.onFail(commodity, "支付失败");
				}
			}
		};
	}





	@Override
	public void pay(OrderInfo orderInfo) {
		WechatOderInfo order = (WechatOderInfo) orderInfo;
		PayReq req = new PayReq();
		req.appId 			= payConfig.wechatId();
		req.partnerId 		= payConfig.wechatPartnerId();
		req.prepayId		= order.getOut_trade_no();//订单号
		req.nonceStr		= order.getNoncestr();
		req.timeStamp		= order.getTimestamp();
		req.packageValue	= "Sign=WXPay";
		req.sign			 = order.getSign();	//签名
		
		api = WXAPIFactory.createWXAPI(activity, payConfig.wechatAppId(),false);	
		// app注册到微信,并发起支付行为
		api.registerApp(payConfig.wechatAppId());
		api.sendReq(req);
		
	}
	


	@Override
	public OrderInfo getOrderInfo() {
		return new WechatOderInfo();
	}



	@Override
	public String getChannel() {
		return "wechat";
	}

}
