package com.tinmanarts.paylib;


import org.json.JSONException;
import org.json.JSONObject;

import com.tinmanarts.paylib.entity.Commodity;
import com.tinmanarts.paylib.entity.OrderInfo;

import android.app.Activity;
import android.util.Log;


public abstract class Pay{
	protected Activity activity;
	protected Commodity commodity;
	protected IPayResultCallback callback;
	protected IPayConfigImp payConfig;

	public Pay(Activity activity, IPayConfigImp payConfig) {
		this.activity = activity;
		this.payConfig = payConfig;
	}
	
	
	public void payment(Commodity commodity, IPayResultCallback callback) {
		this.commodity = commodity;
		this.callback = callback;
		
		String url = payConfig.commodityUrl(commodity);
		byte[] buf = Util.httpGet(url);
		if (buf != null && buf.length > 0) {
			
			String content = new String(buf);
			Log.e("get server pay params:", content);
			try {
				JSONObject json = new JSONObject(content);
				// 商品信息应从移动端获取
				//Commodity commodity = Commodity.json2Commodity(json);
				if (null != json && !json.has("retcode")) {
						OrderInfo orderInfo = getOrderInfo();
						// 订单信息
						orderInfo.toOrder(json);
						// 发起支付
						pay(orderInfo);
				} else {
					callback.onFail(commodity,"解析商品错误");
				}
			} catch (JSONException e) {
				callback.onFail(commodity, "解析商品失败");
			}
		}else {
			callback.onFail(commodity, "网络错误");
		}
		
	}

	
	
	/**
	 * 支付动作
	 * @param commodity 待支付的商品
	 * @param orderInfo	订单信息
	 */
	public abstract void pay(OrderInfo orderInfo);
	
	
	/**
	 * @param commodity  购买的商品
	 * @param callback	支付结果回调
	 */
	public void getPayResult(Commodity commodity){
		byte[] buf = Util.httpGet("http://checkOrder?payChannel="+getChannel()+"commodity?commodityId="+commodity.getId());
		if (buf != null && buf.length > 0) {
			String content = new String(buf);
			if(content.contains("")){
				callback.onSuccess(commodity);
			}else{
				callback.onFail(commodity, "服务器验证错误");
			}
		}
	}
	/**
	 * 实例出具体的支付订单类
	 * @return
	 */
	public abstract OrderInfo getOrderInfo();
	public abstract String getChannel(); 
}
