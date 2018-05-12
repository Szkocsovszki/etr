package dao.course;

public class Course {

	protected String code;
	protected String name;
	protected String weekday;
	protected String start;
	protected String end;
	protected Integer credit;
	protected String place;
	protected Integer onIt;
	protected Integer capacity;
	protected String lecture;
	protected String professor;

	public Course(String code, String name, String weekday, String start, String end, Integer credit, String place,
			Integer onIt, Integer capacity, String lecture, String professor) {
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
	}

	@Override
	public String toString() {
		return "Course [code=" + code + ", name=" + name + ", weekday=" + weekday + ", start=" + start + ", end=" + end
				+ ", credit=" + credit + ", place=" + place + ", onIt=" + onIt + ", capacity=" + capacity + ", lecture="
				+ lecture + ", professor=" + professor + "]\n";
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

	public String getLecture() {
		return lecture;
	}

}
