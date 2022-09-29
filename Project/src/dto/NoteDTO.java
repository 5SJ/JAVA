package dto;

import java.text.SimpleDateFormat;
import java.util.Date;

public class NoteDTO {
	int n_index;
	String n_sid;
	String n_rid;
	String n_subject;
	String n_contents;
	Date n_date;
	String n_sort;
	SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
	public int getN_index() {
		return n_index;
	}
	public void setN_index(int n_index) {
		this.n_index = n_index;
	}
	public String getN_sid() {
		return n_sid;
	}
	public void setN_sid(String n_sid) {
		this.n_sid = n_sid;
	}
	public String getN_rid() {
		return n_rid;
	}
	public void setN_rid(String n_rid) {
		this.n_rid = n_rid;
	}
	public String getN_subject() {
		return n_subject;
	}
	public void setN_subject(String n_subject) {
		this.n_subject = n_subject;
	}
	public String getN_contents() {
		return n_contents;
	}
	public void setN_contents(String n_contents) {
		this.n_contents = n_contents;
	}
	public String getN_dateStr() {
		return sdf.format(n_date);
	}
	public Date getN_date() {
		return n_date;
	}
	public void setN_date(Date n_date) {
		this.n_date = n_date;
	}
	public String getN_sort() {
		return n_sort;
	}
	public void setN_sort(String n_sort) {
		this.n_sort = n_sort;
	}
}
