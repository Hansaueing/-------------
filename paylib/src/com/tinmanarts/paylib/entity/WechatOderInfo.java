package com.tinmanarts.paylib.entity;

import org.json.JSONObject;

public class WechatOderInfo extends OrderInfo {
	public String getNoncestr() {
		return noncestr;
	}
	public void setNoncestr(String noncestr) {
		this.noncestr = noncestr;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	private String noncestr = null;
	private String timestamp = null;
	@Override
	public void toOrder(JSONObject json) {
		super.toOrder(json);
		noncestr = "";
		timestamp = "";
	}
}
