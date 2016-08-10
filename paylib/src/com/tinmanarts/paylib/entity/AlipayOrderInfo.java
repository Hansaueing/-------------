package com.tinmanarts.paylib.entity;

import org.json.JSONObject;
/**
 * 封装订单信息的实体类
 * 订单信息的具体内容需与后台服务器沟通
 * @author payne
 *
 */
public class AlipayOrderInfo extends OrderInfo {
	private String subject;
	private String body;
	private String price;
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	
	@Override
	public void toOrder(JSONObject json) {
		super.toOrder(json);
		subject=" ";
		body = " ";
		price= " ";
	}
		
	
	
	
//	public void toOrder(JSONObject json){
//		super.toOrder(json);
//		total_fee = "";
//	}
	
	
}
