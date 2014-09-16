/**
 * Copyright (c) 2014
 * Tanry Electronic Technology Co., Ltd.
 * ChangSha, China
 * 
 * All Rights Reserved.
 * 
 * First created on Tue Sep 16 16:28:32 CST 2014 by lyz
 * Last edited on Tue Sep 16 16:28:32 CST 2014 by lyz
 * 
 * 说明：餐厅配送反审核单表明细
 */
package pojo.form;

public class ShippingAntiauditDetail {	
	private String formId;
	private String formRefId;
	private String itemId;
	private Double antiauditReceiveCount;
	private Double antiauditReturnCount;
	private Double antiauditPayAmt;

	public String getFormId() {
		return this.formId;
	}

	public void setFormId(String formId) {
		this.formId = formId;
	}

	public String getFormRefId() {
		return this.formRefId;
	}

	public void setFormRefId(String formRefId) {
		this.formRefId = formRefId;
	}

	public String getItemId() {
		return this.itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public Double getAntiauditReceiveCount() {
		return this.antiauditReceiveCount;
	}

	public void setAntiauditReceiveCount(Double antiauditReceiveCount) {
		this.antiauditReceiveCount = antiauditReceiveCount;
	}

	public Double getAntiauditReturnCount() {
		return this.antiauditReturnCount;
	}

	public void setAntiauditReturnCount(Double antiauditReturnCount) {
		this.antiauditReturnCount = antiauditReturnCount;
	}

	public Double getAntiauditPayAmt() {
		return this.antiauditPayAmt;
	}

	public void setAntiauditPayAmt(Double antiauditPayAmt) {
		this.antiauditPayAmt = antiauditPayAmt;
	}

}