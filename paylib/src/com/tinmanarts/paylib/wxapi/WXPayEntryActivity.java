package com.tinmanarts.paylib.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tinmanarts.paylib.WXPay;

/**
 * 处理支付结果的Activity
 * 
 * @author payne
 * 
 */
public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

	// private static final String TAG = "MicroMsg.SDKSample.WXPayEntryActivity";

	private IWXAPI api;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

//		api = WXAPIFactory.createWXAPI(this, "");
		api.handleIntent(getIntent(), this);
		
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
		api.handleIntent(intent, this);
	}

	@Override
	public void onReq(BaseReq req) {
	}

	/**
	 * 支付结果
	 */
	@Override
	public void onResp(BaseResp resp) {
		Log.d("text", "onPayFinish, errCode = " + resp.errCode);
//		WXPay.handler.sendEmptyMessage(0);
		
		WXPay.handler.sendEmptyMessage(resp.errCode);
	}
}