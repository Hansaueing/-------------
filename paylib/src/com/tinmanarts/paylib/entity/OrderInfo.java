package com.tinmanarts.paylib.entity;

import org.json.JSONObject;

//订单信息
public class OrderInfo {
		// 商户网站唯一订单号
		private String out_trade_no;
		// 订单签名
		private String sign;
		// 
		
		
		
		public String getOut_trade_no() {
			return out_trade_no;
		}
		public void setOut_trade_no(String out_trade_no) {
			this.out_trade_no = out_trade_no;
		}
		public String getSign() {
			return sign;
		}
		public void setSign(String sign) {
			this.sign = sign;
		}		
		//将json字段封装为Order订单实体类
		public void toOrder(JSONObject json){
			out_trade_no = "";
			sign = "";
			
		}
		
}
