/**
 * Copyright (c) 2014
 * Tanry Electronic Technology Co., Ltd.
 * ChangSha, China
 * 
 * All Rights Reserved.
 * 
 * First created on 2014-09-15 by lyz
 * Last edited on 2014-09-15 by lyz
 * 
 * 说明：test
 */
package pojo.form;

public class Antiaudit {	
	private String formId;
	private String formRefId;
	private String formName;
	private String antiauditor;
	private Date antiauditBranch;
	private String antiauditTime;
	private Date formNote;
	private String allPayAmt;
	private Date maxPayItem;

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

	pubic Date getMaxPayItem() {
		return this.maxPayItem;
	}

	public void setMaxPayItem(Date maxPayItem) {
		this.maxPayItem = maxPayItem;
	}

}