package com.analyzer.domain.dbo;

import com.googlecode.objectify.annotation.Entity;

@Entity
public class ProfitAndLossDbObject {

	
	private float averageReturn;

	
	private float prevAverageReturn;
	
	
	private float totalInvestment;
	
	
	private float totalReturn;
	
	
	private float prevTotalReturn;

	
	private float totalReturnIfBank;
	
	
	private float totalReturnVsIfBank;
	
	
	private float prevTotalReturnVsIfBank;
	
	
	private float diffInCurrentAndPrevAverageReturn;
	
	
	private float diffInCurrentAndPrevTotalReturn;
	
	
	private String applicableDate;


	public float getAverageReturn() {
		return averageReturn;
	}


	public void setAverageReturn(float averageReturn) {
		this.averageReturn = averageReturn;
	}


	public float getPrevAverageReturn() {
		return prevAverageReturn;
	}


	public void setPrevAverageReturn(float prevAverageReturn) {
		this.prevAverageReturn = prevAverageReturn;
	}


	public float getTotalInvestment() {
		return totalInvestment;
	}


	public void setTotalInvestment(float totalInvestment) {
		this.totalInvestment = totalInvestment;
	}


	public float getTotalReturn() {
		return totalReturn;
	}


	public void setTotalReturn(float totalReturn) {
		this.totalReturn = totalReturn;
	}


	public float getPrevTotalReturn() {
		return prevTotalReturn;
	}


	public void setPrevTotalReturn(float prevTotalReturn) {
		this.prevTotalReturn = prevTotalReturn;
	}


	public float getTotalReturnIfBank() {
		return totalReturnIfBank;
	}


	public void setTotalReturnIfBank(float totalReturnIfBank) {
		this.totalReturnIfBank = totalReturnIfBank;
	}


	public float getTotalReturnVsIfBank() {
		return totalReturnVsIfBank;
	}


	public void setTotalReturnVsIfBank(float totalReturnVsIfBank) {
		this.totalReturnVsIfBank = totalReturnVsIfBank;
	}


	public float getPrevTotalReturnVsIfBank() {
		return prevTotalReturnVsIfBank;
	}


	public void setPrevTotalReturnVsIfBank(float prevTotalReturnVsIfBank) {
		this.prevTotalReturnVsIfBank = prevTotalReturnVsIfBank;
	}


	public float getDiffInCurrentAndPrevAverageReturn() {
		return diffInCurrentAndPrevAverageReturn;
	}


	public void setDiffInCurrentAndPrevAverageReturn(float diffInCurrentAndPrevAverageReturn) {
		this.diffInCurrentAndPrevAverageReturn = diffInCurrentAndPrevAverageReturn;
	}


	public float getDiffInCurrentAndPrevTotalReturn() {
		return diffInCurrentAndPrevTotalReturn;
	}


	public void setDiffInCurrentAndPrevTotalReturn(float diffInCurrentAndPrevTotalReturn) {
		this.diffInCurrentAndPrevTotalReturn = diffInCurrentAndPrevTotalReturn;
	}


	public String getApplicableDate() {
		return applicableDate;
	}


	public void setApplicableDate(String applicableDate) {
		this.applicableDate = applicableDate;
	}
	
}
