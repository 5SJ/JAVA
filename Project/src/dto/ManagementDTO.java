package dto;

import java.util.Date;

public class ManagementDTO {
	private int m_no;
	private String m_id;
	private String m_pw;
	private String m_name;
	private String m_gender;
	private String m_email;
	private String m_tel;
	private Date m_date;
	private int m_state;
	
	public int getM_no() {
		return m_no;
	}
	public void setM_no(int m_no) {
		this.m_no = m_no;
	}
	public String getM_id() {
		return m_id;
	}
	public void setM_id(String m_id) {
		this.m_id = m_id;
	}
	public String getM_pw() {
		return m_pw;
	}
	public void setM_pw(String m_pw) {
		this.m_pw = m_pw;
	}
	public String getM_name() {
		return m_name;
	}
	public void setM_name(String m_name) {
		this.m_name = m_name;
	}
	public String getM_gender() {
		return m_gender;
	}
	public void setM_gender(String m_gender) {
		this.m_gender = m_gender;
	}
	public String getM_email() {
		return m_email;
	}
	
	public void setM_email(String m_email) {
		if (m_email == null)
			m_email = "";
		this.m_email = m_email;
	}
	
	public String getM_tel() {
		return m_tel;
	}
	
	public void setM_tel(String m_tel) {
		if (m_tel == null)
			m_tel = "";
		this.m_tel = m_tel;
	}
	
	public Date getM_date() {
		return m_date;
	}
	public void setM_date(Date m_date) {
		this.m_date = m_date;
	}
	public int getM_state() {
		return m_state;
	}
	public void setM_state(int m_state) {
		this.m_state = m_state;
	}
}