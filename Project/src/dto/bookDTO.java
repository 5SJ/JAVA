package dto;

public class bookDTO {
	private String BOOK_CATEGORY;
	private String BOOK_NAME;
	private int BOOK_CODE;
	private String BOOK_AUTHOR;
	private String BOOK_COMPANY;

	public bookDTO() {

	}

	public bookDTO(int BOOK_CODE, String BOOK_CATEGORY, String BOOK_NAME, String BOOK_AUTHOR, String BOOK_COMPANY) {
		if (BOOK_CATEGORY == null)
			BOOK_CATEGORY = "";
		if (BOOK_NAME == null)
			BOOK_NAME = "";
		if (BOOK_AUTHOR == null)
			BOOK_AUTHOR = "";
		if (BOOK_COMPANY == null)
			BOOK_COMPANY = "";

		this.BOOK_CATEGORY = BOOK_CATEGORY;
		this.BOOK_NAME = BOOK_NAME;
		this.BOOK_AUTHOR = BOOK_AUTHOR;
		this.BOOK_COMPANY = BOOK_COMPANY;
		this.BOOK_CODE = BOOK_CODE;
	}

	public String getBOOK_CATEGORY() {
		return BOOK_CATEGORY;
	}

	public void setBOOK_CATEGORY(String BOOK_CATEGORY) {
		if (BOOK_CATEGORY == null)
			BOOK_CATEGORY = "";
		this.BOOK_CATEGORY = BOOK_CATEGORY;
	}

	public String getBOOK_NAME() {
		return BOOK_NAME;
	}

	public void setBOOK_NAME(String BOOK_NAME) {
		if (BOOK_NAME == null)
			BOOK_NAME = "";
		this.BOOK_NAME = BOOK_NAME;
	}

	public int getBOOK_CODE() {
		return BOOK_CODE;
	}

	public void setBOOK_CODE(int bOOK_CODE) {
		this.BOOK_CODE = bOOK_CODE;
	}

	public String getBOOK_AUTHOR() {
		return BOOK_AUTHOR;
	}

	public void setBOOK_AUTHOR(String BOOK_AUTHOR) {
		if (BOOK_AUTHOR == null)
			BOOK_AUTHOR = "";
		this.BOOK_AUTHOR = BOOK_AUTHOR;
	}

	public String getBOOK_COMPANY() {
		return BOOK_COMPANY;
	}

	public void setBOOK_COMPANY(String BOOK_COMPANY) {
		if (BOOK_COMPANY == null)
			BOOK_COMPANY = "";
		this.BOOK_COMPANY = BOOK_COMPANY;
	}
}
