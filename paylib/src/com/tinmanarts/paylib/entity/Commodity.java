package com.tinmanarts.paylib.entity;

//商品类
public class Commodity {
	
	public Commodity(){
		super();
	}
	
	public Commodity(String id, String subject, String body, String total_fee) {
		super();
		this.id = id;
		this.subject = subject;
		this.body = body;
		this.total_fee = total_fee;
	}

	private String id;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	// 	商品名称
	private String subject;
	// 	商品详情
	private String body;
	//	商品金额
	private String total_fee;
	
	
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public String getTotal_fee() {
		return total_fee;
	}
	public void setTotal_fee(String total_fee) {
		this.total_fee = total_fee;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}

	@Override
	public boolean equals(Object o) {
		if(o==this){
			return true;
		}
		if(o instanceof Commodity){
			Commodity other = (Commodity)o;
			if(this.getId().length()!=other.getId().length()){
				return false;
			}
			if(this.hashCode()!=other.hashCode()){
				return false;
			}
			char[] value1 = this.getId().toCharArray();
            int offset1 = 0;
            char[] value2 = other.getId().toCharArray();
            int offset2 = 0;
            for (int i=0;i<value1.length;i++ ) {
                if (value1[offset1] != value2[offset2]) {
                    return false;
                }
                offset1++;
                offset2++;
            }
            return true;
		}else {
			return false;
		}
	}

	@Override
	public String toString() {
		return "Commodity [id=" + id + ", subject=" + subject + ", body="
				+ body + ", total_fee=" + total_fee + "]";
	}
	
	
}
