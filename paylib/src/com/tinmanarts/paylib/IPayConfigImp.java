package com.tinmanarts.paylib;

import com.tinmanarts.paylib.entity.Commodity;

public interface IPayConfigImp {
	public String commodityUrl(Commodity commodity);
	
	public String wechatId();
	public String wechatPartnerId();
	public String wechatAppId();
	
	public String alipayId();
	public String aliSellerId();
	
}
