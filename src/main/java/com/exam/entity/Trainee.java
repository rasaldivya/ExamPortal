package com.exam.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

//@Entity
public class Trainee {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String fullName;
	private String armyNo;
	private String armyRank;
	private String unit;
	private String trade;	
	public Trainee() {
		super();
	}
	public Trainee(int id, String fullName, String armyNo, String armyRank, String unit, String trade) {
		super();
		this.id = id;
		this.fullName = fullName;
		this.armyNo = armyNo;
		this.armyRank = armyRank;
		this.unit = unit;
		this.trade = trade;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getArmyNo() {
		return armyNo;
	}
	public void setArmyNo(String armyNo) {
		this.armyNo = armyNo;
	}
	public String getArmyRank() {
		return armyRank;
	}
	public void setArmyRank(String armyRank) {
		this.armyRank = armyRank;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getTrade() {
		return trade;
	}
	public void setTrade(String trade) {
		this.trade = trade;
	}
	@Override
	public String toString() {
		return "Trainee [id=" + id + ", fullName=" + fullName + ", armyNo=" + armyNo + ", armyRank=" + armyRank + ", unit="
				+ unit + ", trade=" + trade + "]";
	}

	
	
	
}
