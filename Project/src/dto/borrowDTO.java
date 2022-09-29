package dto;

import java.text.SimpleDateFormat;
import java.util.Date;

public class borrowDTO {
	private int index;
	private String id;
	private String name;
	private String bookname;
	private int BOOK_CODE;
	private String tel;
	private Date bdate;
	private Date rdate;
	private String state;
	SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
	
	public borrowDTO() {

	}

	public borrowDTO(String id, int BOOK_CODE, String borrow_DATE, String return_DATE) {
		if (id == null)
			id = "";
		if (BOOK_CODE == 0)
			BOOK_CODE = 0;
		if (borrow_DATE == null)
			borrow_DATE = "";
		if (return_DATE == null)
			return_DATE = "";
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBookname() {
		return bookname;
	}

	public void setBookname(String bookname) {
		this.bookname = bookname;
	}

	public int getBOOK_CODE() {
		return BOOK_CODE;
	}

	public void setBOOK_CODE(int bOOK_CODE) {
		BOOK_CODE = bOOK_CODE;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}
	
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	
	public Date getBdate() {
		return bdate;
	}

	public void setBdate(Date bdate) {
		this.bdate = bdate;
	}

	public Date getRdate() {
		return rdate;
	}

	public void setRdate(Date rdate) {
		this.rdate = rdate;
	}

	public String getBorrow_DATE() {
		return sdf.format(bdate);
	}
	
	public String getReturn_DATE() {
		return sdf.format(rdate);
	}
}