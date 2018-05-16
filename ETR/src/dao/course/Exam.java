package dao.course;

public class Exam {
	
	private String code;
	private String course;
	private String time;
	private String place;
	private Integer price;
	private Integer mark;
	
	public Exam(String code, String course, String time, String place, Integer price, Integer mark) {
		super();
		this.code = code;
		this.course = course;
		this.time = time;
		this.place = place;
		this.price = price;
		this.mark = mark;
	}
	
	public String getCode() {
		return code;
	}
	
	public String getCourse() {
		return course;
	}
	
	public String getTime() {
		return time;
	}
	
	public String getPlace() {
		return place;
	}
	
	public Integer getPrice() {
		return price;
	}

	public Integer getMark() {
		return mark;
	}

	@Override
	public String toString() {
		return "Exam [code=" + code + ", course=" + course + ", time=" + time + ", place=" + place + ", price=" + price
				+ ", mark=" + mark + "]\n";
	}
	
}
