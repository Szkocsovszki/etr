package dao.course;

public class Course {

	protected String code;
	protected String name;
	protected String weekday;
	protected String start;
	protected String end;
	protected Integer credit;
	protected String place;
	protected String lecture;
	
	public Course(String code, String name, String weekday, String start, String end, Integer credit, String place,
			String lecture) {
		this.name = name;
		this.code = code;
		this.weekday = weekday;
		this.start = start;
		this.end = end;
		this.credit = credit;
		this.place = place;
		this.lecture = lecture;
	}

	@Override
	public String toString() {
		return "Course [code=" + code + ", name=" + name + ", weekday=" + weekday + ", start=" + start + ", end=" + end
				+ ", credit=" + credit + ", place=" + place + ", lecture=" + lecture + "]";
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
