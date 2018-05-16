package dao.course;

public class Exam {
	
	private String code;
	private String name;
	private String time;
	private String place;
	private Integer price;
	private Integer mark;
	private Integer onIt;
	private Integer capacity;
	
	public Exam(String code, String name, String time, String place, Integer price, Integer mark, Integer onIt,
			Integer capacity) {
		super();
		this.code = code;
		this.name = name;
		this.time = time;
		this.place = place;
		this.price = price;
		this.mark = mark;
		this.onIt = onIt;
		this.capacity = capacity;
	}
	
	public String getCode() {
		return code;
	}
	
	public String getName() {
		return name;
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
	
	public Integer getOnIt() {
		return onIt;
	}

	public Integer getCapacity() {
		return capacity;
	}

	@Override
	public String toString() {
		return "Exam [code=" + code + ", name=" + name + ", time=" + time + ", place=" + place + ", price=" + price
				+ ", mark=" + mark + ", onIt=" + onIt + ", capacity=" + capacity + "]\n";
	}
	
}
