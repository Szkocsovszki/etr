package dao.course;

public class Course {

	private String code;
	private String name;
	private String weekday;
	private String start;
	private String end;
	private Integer credit;
	private String place;
	private Integer onIt;
	private Integer capacity;
	private String lecture;
	private String professor;
	private Integer mark;

	public Course(String code, String name, String weekday, String start, String end, Integer credit, String place,
			Integer onIt, Integer capacity, String lecture, String professor, Integer mark) {
		this.name = name;
		this.code = code;
		this.weekday = weekday;
		this.start = start;
		this.end = end;
		this.credit = credit;
		this.place = place;
		this.onIt = onIt;
		this.capacity = capacity;
		this.lecture = lecture;
		this.professor = professor;
		this.mark = mark;
	}

	@Override
	public String toString() {
		return "Course [code=" + code + ", name=" + name + ", weekday=" + weekday + ", start=" + start + ", end=" + end
				+ ", credit=" + credit + ", place=" + place + ", onIt=" + onIt + ", capacity=" + capacity + ", lecture="
				+ lecture + ", professor=" + professor + ", mark=" + mark + "]\n";
	}

	public String getName() {
		return name;
	}

	public String getCode() {
		return code;
	}

	public String getWeekday() {
		return weekday;
	}

	public String getStart() {
		return start;
	}

	public String getEnd() {
		return end;
	}

	public Integer getCredit() {
		return credit;
	}

	public String getPlace() {
		return place;
	}

	public String getProfessor() {
		return professor;
	}

	public String getLecture() {
		return lecture;
	}
	
	public Integer getOnIt() {
		return onIt;
	}

	public Integer getCapacity() {
		return capacity;
	}

	public Integer getMark() {
		return mark;
	}

	
}
