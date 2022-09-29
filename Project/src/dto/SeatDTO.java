package dto;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SeatDTO {
	Integer s_index;
	Integer s_num;
	String s_used;
	String s_id;
	Date s_date;
	Date s_sdate;
	Date s_edate;
	SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
	public int getS_index() {
		return s_index;
	}
	public void setS_index(int s_index) {
		this.s_index = s_index;
	}
	public int getS_num() {
		return s_num;
	}
	public void setS_num(int s_num) {
		this.s_num = s_num;
	}
	public String getS_used() {
		return s_used;
	}
	public void setS_used(String s_used) {
		this.s_used = s_used;
	}
	public String getS_id() {
		return s_id;
	}
	public void setS_id(String s_id) {
		this.s_id = s_id;
	}
	public Date getS_date() {
		return s_date;
	}
	public void setS_date(Date s_date) {
		this.s_date = s_date;
	}
	public String getS_sdate() {
		return sdf.format(s_sdate);
	}
	public void setS_sdate(Date s_sdate) {
		this.s_sdate = s_sdate;
	}
	public String getS_edate() {
		return sdf.format(s_edate);
	}
	public void setS_edate(Date s_edate) {
		this.s_edate = s_edate;
	}
}
