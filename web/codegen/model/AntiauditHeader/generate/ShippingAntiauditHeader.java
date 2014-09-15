/**
 * Copyright (c) 2014
 * Tanry Electronic Technology Co., Ltd.
 * ChangSha, China
 * 
 * All Rights Reserved.
 * 
 * First created on Tue Sep 16 12:09:07 CST 2014 by lyz
 * Last edited on Tue Sep 16 12:09:07 CST 2014 by lyz
 * 
 * 说明：餐厅配送反审核单表头
 */
package pojo.form;

public class ShippingAntiauditHeader {	
	private String formId;
	private String formRefId;
	private String formName;
	private String antiauditor;
	private Date antiauditBranch;
	private String antiauditTime;
	private Date formNote;
	private String allPayAmt;
	private String maxPayItem;

	pubic String getFormId() {
		return this.formId;
	}

	public void setFormId(String formId) {
		this.formId = formId;
	}

	pubic String getFormRefId() {
		return this.formRefId;
	}

	public void setFormRefId(String formRefId) {
		this.formRefId = formRefId;
	}

	pubic String getFormName() {
		return this.formName;
	}

	public void setFormName(String formName) {
		this.formName = formName;
	}

	pubic String getAntiauditor() {
		return this.antiauditor;
	}

	public void setAntiauditor(String antiauditor) {
		this.antiauditor = antiauditor;
	}

	pubic Date getAntiauditBranch() {
		return this.antiauditBranch;
	}

	public void setAntiauditBranch(Date antiauditBranch) {
		this.antiauditBranch = antiauditBranch;
	}

	pubic String getAntiauditTime() {
		return this.antiauditTime;
	}

	public void setAntiauditTime(String antiauditTime) {
		this.antiauditTime = antiauditTime;
	}

	pubic Date getFormNote() {
		return this.formNote;
	}

	public void setFormNote(Date formNote) {
		this.formNote = formNote;
	}

	pubic String getAllPayAmt() {
		return this.allPayAmt;
	}

	public void setAllPayAmt(String allPayAmt) {
		this.allPayAmt = allPayAmt;
	}

	pubic String getMaxPayItem() {
		return this.maxPayItem;
	}

	public void setMaxPayItem(String maxPayItem) {
		this.maxPayItem = maxPayItem;
	}

}