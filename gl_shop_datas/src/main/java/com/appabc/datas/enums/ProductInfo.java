package com.appabc.datas.enums;

public interface ProductInfo {
	
	public enum UnitEnum implements ProductInfo {
		
		UNIT_TON("UNIT001"), // 吨
		UNIT_CUBE("UNIT002"); // 立方
		
		
		private String val;
		
		private UnitEnum(String val){
			this.val = val;
		}
		
		public String getVal() {
			return val;
		}
		
	}

}
