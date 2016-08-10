package com.tinmanarts.paylib;


import android.app.Activity;
import android.text.TextUtils;

import com.alipay.sdk.app.PayTask;
import com.tinmanarts.paylib.entity.AlipayOrderInfo;
import com.tinmanarts.paylib.entity.OrderInfo;

public class Alipay extends Pay {

	public Alipay(Activity activity, IPayConfigImp payConfig) {
		super(activity, payConfig);
	}

	private PayTask alipay; // 调用支付的sdk参数
	private String resultStatus;// 支付结果



	@Override
	public void pay(OrderInfo orderInfo) {
		
		final AlipayOrderInfo oi = (AlipayOrderInfo) orderInfo;
		Runnable payRunnable = new Runnable() {

			@Override
			public void run() {
				// 构造PayTask 对象
				alipay = new PayTask(activity);
				// 调用支付接口，获取支付结果
				// 将orderInfo组装成支付所需参数的格式payInfo
				String orderInformation = getOrderInfo(oi);
				String payInfo = orderInformation + "&sign=\"" + oi.getSign() + "\"&" + getSignType();

				String result = alipay.pay(payInfo, true);
				
				PayResult payResult = new PayResult(result);
				/**
				 * 同步返回的结果必须放置到服务端进行验证（验证的规则请看https://doc.open.alipay.com/doc2/
				 * detail.htm?spm=0.0.0.0.xdvAU6&treeId=59&articleId=103665&
				 * docType=1) 建议商户依赖异步通知
				 */
				@SuppressWarnings("unused")
				String resultInfo = payResult.getResult();// 同步返回需要验证的信息
				// 特别注意，同步结果校验的逻辑，必须放在服务端处理，切记不要放在客户端,强烈建议商户直接依赖服务端的异步通知，忽略同步返回.
				resultStatus = payResult.getResultStatus();
				if(TextUtils.equals(resultStatus, "9000")){
					getPayResult(commodity);
				}else{
					callback.onFail(commodity, "后台服务器确认失败");
				}
			}
		};

		// 必须异步调用
		Thread payThread = new Thread(payRunnable);
		payThread.start();

	}



	private String getSignType() {
		return "sign_type=\"RSA\"";
	}

	// 签名逻辑放在服务端，仅返回签名结果即可
	/*
	 * private String sign(String orderInfo) { return SignUtils.sign(orderInfo,
	 * Constant.RSA_PRIVATE); }
	 */
	/**
	 * AliPay支付，组装支付的必要参数
	 * @param commodity 商品信息
	 * @param orderInfomation 订单信息
	 * @return	符合支付宝支付要求的String字符串
	 */
	private String getOrderInfo(AlipayOrderInfo orderInfomation) {
//		AlipayOrderInfo order = (AlipayOrderInfo) orderInfo1;
		// 签约合作者身份ID
		String orderInfo = "partner=" + "\"" + payConfig.alipayId() + "\"";

		// 签约卖家支付宝账号
		orderInfo += "&seller_id=" + "\"" + payConfig.aliSellerId() + "\"";

		// 商户网站唯一订单号
		orderInfo += "&out_trade_no=" + "\"" + orderInfomation.getOut_trade_no() + "\"";

		// 商品名称
		orderInfo += "&subject=" + "\"" + orderInfomation.getSubject() + "\"";

		// 商品详情
		orderInfo += "&body=" + "\"" + orderInfomation.getBody() + "\"";

		// 商品金额
		orderInfo += "&total_fee=" + "\"" + orderInfomation.getPrice()+ "\"";

		// 服务器异步通知页面路径
		orderInfo += "&notify_url=" + "\"" + "http://notify.msp.hk/notify.htm"
				+ "\"";

		// 服务接口名称， 固定值
		orderInfo += "&service=\"mobile.securitypay.pay\"";

		// 支付类型， 固定值
		orderInfo += "&payment_type=\"1\"";

		// 参数编码， 固定值
		orderInfo += "&_input_charset=\"utf-8\"";

		// 设置未付款交易的超时时间
		// 默认30分钟，一旦超时，该笔交易就会自动被关闭。
		// 取值范围：1m～15d。
		// m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
		// 该参数数值不接受小数点，如1.5h，可转换为90m。
		orderInfo += "&it_b_pay=\"30m\"";

		// extern_token为经过快登授权获取到的alipay_open_id,带上此参数用户将使用授权的账户进行支付
		// orderInfo += "&extern_token=" + "\"" + extern_token + "\"";

		// 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
		orderInfo += "&return_url=\"m.alipay.com\"";

		// 调用银行卡支付，需配置此参数，参与签名， 固定值 （需要签约《无线银行卡快捷支付》才能使用）
		// orderInfo += "&paymethod=\"expressGateway\"";
		return orderInfo;

	}

	@Override
	public OrderInfo getOrderInfo() {
		return new AlipayOrderInfo();
	}

	@Override
	public String getChannel() {
		return "alipay";
	}
}
