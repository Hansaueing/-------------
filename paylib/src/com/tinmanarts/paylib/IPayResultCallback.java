package com.tinmanarts.paylib;

import com.tinmanarts.paylib.entity.Commodity;



public interface IPayResultCallback {
	void onSuccess(Commodity commodity);
	void onFail(Commodity commodity, String error);


}
