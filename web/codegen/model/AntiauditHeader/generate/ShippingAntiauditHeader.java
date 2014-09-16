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

	public String getFormName() {
		return this.formName;
	}

	public void setFormName(String formName) {
		this.formName = formName;
	}

	public String getAntiauditor() {
		return this.antiauditor;
	}

	public void setAntiauditor(String antiauditor) {
		this.antiauditor = antiauditor;
	}

	public Date getAntiauditBranch() {
		return this.antiauditBranch;
	}

	public void setAntiauditBranch(Date antiauditBranch) {
		this.antiauditBranch = antiauditBranch;
	}

	public String getAntiauditTime() {
		return this.antiauditTime;
	}

	public void setAntiauditTime(String antiauditTime) {
		this.antiauditTime = antiauditTime;
	}

	public Date getFormNote() {
		return this.formNote;
	}

	public void setFormNote(Date formNote) {
		this.formNote = formNote;
	}

	public String getAllPayAmt() {
		return this.allPayAmt;
	}

	public void setAllPayAmt(String allPayAmt) {
		this.allPayAmt = allPayAmt;
	}

	public String getMaxPayItem() {
		return this.maxPayItem;
	}

	public void setMaxPayItem(String maxPayItem) {
		this.maxPayItem = maxPayItem;
	}

}