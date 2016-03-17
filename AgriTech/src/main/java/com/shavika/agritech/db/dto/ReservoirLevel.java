package com.shavika.agritech.db.dto;

import java.io.Serializable;
import java.util.Date;

import com.shavika.agritech.utils.DateTimeUtil;

public class ReservoirLevel implements Serializable {

	private static final long serialVersionUID = 461978388326094554L;

	private long id;

	private String reservoir_name;
	private Date reported_date;

	/* this are in feet. */
	private String max_level;
	private String present_level;
	private String lastyear_level;
	private String balance_to_reach_max;

	/* this are in TMC. */
	private String gross_storage_capacity;
	private String present_storage;
	private String lastyear_storage;

	/* this are in percentage. */
	private String present_capacity_percntage;
	private String previousday_capacity_percntage;
	private String balance_storage_toreach_capacity;

	/* this are in Avg. */
	private String inflow;
	private String outflow;
	private String cumulative_inflow;
	private String cumulative_outflow;

	private boolean is_deleted;
	private long created_on;
	private long modified_on;

	private String[] dataValues = null;
	private String present_date = null;

	/**
	 * 
	 */
	public ReservoirLevel() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param reservoir_name
	 * @param reported_date
	 * @param max_level
	 * @param present_level
	 * @param lastyear_level
	 * @param balance_to_reach_max
	 * @param gross_storage_capacity
	 * @param present_storage
	 * @param lastyear_storage
	 * @param present_capacity_percntage
	 * @param previousday_capacity_percntage
	 * @param balance_storage_toreach_capacity
	 * @param inflow
	 * @param outflow
	 * @param cumulative_inflow
	 * @param cumulative_outflow
	 * @param is_deleted
	 * @param created_on
	 * @param modified_on
	 */
	public ReservoirLevel(String reservoir_name, Date reported_date, String max_level, String present_level, String lastyear_level, String balance_to_reach_max,
			String gross_storage_capacity, String present_storage, String lastyear_storage, String present_capacity_percntage, String previousday_capacity_percntage,
			String balance_storage_toreach_capacity, String inflow, String outflow, String cumulative_inflow, String cumulative_outflow, boolean is_deleted,
			long created_on, long modified_on) {
		super();
		this.reservoir_name = reservoir_name;
		this.reported_date = reported_date;
		this.max_level = max_level;
		this.present_level = present_level;
		this.lastyear_level = lastyear_level;
		this.balance_to_reach_max = balance_to_reach_max;
		this.gross_storage_capacity = gross_storage_capacity;
		this.present_storage = present_storage;
		this.lastyear_storage = lastyear_storage;
		this.present_capacity_percntage = present_capacity_percntage;
		this.previousday_capacity_percntage = previousday_capacity_percntage;
		this.balance_storage_toreach_capacity = balance_storage_toreach_capacity;
		this.inflow = inflow;
		this.outflow = outflow;
		this.cumulative_inflow = cumulative_inflow;
		this.cumulative_outflow = cumulative_outflow;
		this.is_deleted = is_deleted;
		this.created_on = created_on;
		this.modified_on = modified_on;
	}

	/**
	 * 
	 */
	public ReservoirLevel(String[] dataValues, String present_date) {
		this.dataValues = dataValues;
		this.present_date = present_date;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getReservoir_name() {
		return reservoir_name;
	}

	public void setReservoir_name(String reservoir_name) {
		this.reservoir_name = reservoir_name;
	}

	public Date getReported_date() {
		return reported_date;
	}

	public void setReported_date(Date reported_date) {
		this.reported_date = reported_date;
	}

	public String getMax_level() {
		return max_level;
	}

	public void setMax_level(String max_level) {
		this.max_level = max_level;
	}

	public String getPresent_level() {
		return present_level;
	}

	public void setPresent_level(String present_level) {
		this.present_level = present_level;
	}

	public String getLastyear_level() {
		return lastyear_level;
	}

	public void setLastyear_level(String lastyear_level) {
		this.lastyear_level = lastyear_level;
	}

	public String getBalance_to_reach_max() {
		return balance_to_reach_max;
	}

	public void setBalance_to_reach_max(String balance_to_reach_max) {
		this.balance_to_reach_max = balance_to_reach_max;
	}

	public String getGross_storage_capacity() {
		return gross_storage_capacity;
	}

	public void setGross_storage_capacity(String gross_storage_capacity) {
		this.gross_storage_capacity = gross_storage_capacity;
	}

	public String getPresent_storage() {
		return present_storage;
	}

	public void setPresent_storage(String present_storage) {
		this.present_storage = present_storage;
	}

	public String getLastyear_storage() {
		return lastyear_storage;
	}

	public void setLastyear_storage(String lastyear_storage) {
		this.lastyear_storage = lastyear_storage;
	}

	public String getPresent_capacity_percntage() {
		return present_capacity_percntage;
	}

	public void setPresent_capacity_percntage(String present_capacity_percntage) {
		this.present_capacity_percntage = present_capacity_percntage;
	}

	public String getPreviousday_capacity_percntage() {
		return previousday_capacity_percntage;
	}

	public void setPreviousday_capacity_percntage(String previousday_capacity_percntage) {
		this.previousday_capacity_percntage = previousday_capacity_percntage;
	}

	public String getBalance_storage_toreach_capacity() {
		return balance_storage_toreach_capacity;
	}

	public void setBalance_storage_toreach_capacity(String balance_storage_toreach_capacity) {
		this.balance_storage_toreach_capacity = balance_storage_toreach_capacity;
	}

	public String getInflow() {
		return inflow;
	}

	public void setInflow(String inflow) {
		this.inflow = inflow;
	}

	public String getOutflow() {
		return outflow;
	}

	public void setOutflow(String outflow) {
		this.outflow = outflow;
	}

	public String getCumulative_inflow() {
		return cumulative_inflow;
	}

	public void setCumulative_inflow(String cumulative_inflow) {
		this.cumulative_inflow = cumulative_inflow;
	}

	public String getCumulative_outflow() {
		return cumulative_outflow;
	}

	public void setCumulative_outflow(String cumulative_outflow) {
		this.cumulative_outflow = cumulative_outflow;
	}

	public boolean isIs_deleted() {
		return is_deleted;
	}

	public void setIs_deleted(boolean is_deleted) {
		this.is_deleted = is_deleted;
	}

	public long getCreated_on() {
		return created_on;
	}

	public void setCreated_on(long created_on) {
		this.created_on = created_on;
	}

	public long getModified_on() {
		return modified_on;
	}

	public void setModified_on(long modified_on) {
		this.modified_on = modified_on;
	}

	@Override
	public String toString() {
		return "ReservoirLevel [id=" + id + ", reservoir_name=" + reservoir_name + ", reported_date=" + reported_date + ", max_level=" + max_level + ", present_level="
				+ present_level + ", lastyear_level=" + lastyear_level + ", balance_to_reach_max=" + balance_to_reach_max + ", gross_storage_capacity="
				+ gross_storage_capacity + ", present_storage=" + present_storage + ", lastyear_storage=" + lastyear_storage + ", present_capacity_percntage="
				+ present_capacity_percntage + ", previousday_capacity_percntage=" + previousday_capacity_percntage + ", balance_storage_toreach_capacity="
				+ balance_storage_toreach_capacity + ", inflow=" + inflow + ", outflow=" + outflow + ", cumulative_inflow=" + cumulative_inflow + ", cumulative_outflow="
				+ cumulative_outflow + ", is_deleted=" + is_deleted + ", created_on=" + created_on + ", modified_on=" + modified_on + "]";
	}

	public ReservoirLevel dataParse() {
		System.out.println("Parse data===>" + dataValues.length);
		ReservoirLevel rl = null;
		try {
			rl = new ReservoirLevel();
			rl.setReservoir_name(dataValues[1].toString());
			rl.setReported_date(DateTimeUtil.dateWithFormat(present_date, DateTimeUtil.DATEFORMAT_DD_DOT_MM_DOT_YYYY));
			rl.setMax_level(dataValues[2].toString());
			rl.setPresent_level(dataValues[3].toString());
			rl.setLastyear_level(dataValues[4].toString());
			rl.setBalance_to_reach_max(dataValues[5].toString());
			rl.setGross_storage_capacity(dataValues[6].toString());
			rl.setPresent_storage(dataValues[7].toString());
			rl.setLastyear_storage(dataValues[8].toString());
			rl.setPresent_capacity_percntage(dataValues[9].toString());
			rl.setPreviousday_capacity_percntage(dataValues[10].toString());
			rl.setBalance_storage_toreach_capacity(dataValues[11].toString());
			rl.setInflow(dataValues[12].toString());
			rl.setOutflow(dataValues[13].toString());
			rl.setCumulative_inflow(dataValues[14].toString());
			rl.setCumulative_outflow(dataValues[15].toString());
			rl.setIs_deleted(false);
			rl.setCreated_on(DateTimeUtil.getMillis());
			rl.setModified_on(DateTimeUtil.getMillis());
		} catch (Exception e) {
			e.printStackTrace();
			rl = null;
		}
		return rl;
	}

}
