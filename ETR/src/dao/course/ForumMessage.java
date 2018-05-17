package dao.course;

public class ForumMessage {
	private String course;
	private String name;
	private String time;
	private String message;
	
	public ForumMessage(String course, String name, String time, String message) {
		super();
		this.course = course;
		this.name = name;
		this.time = time;
		this.message = message;
	}

	public String getCourse() {
		return course;
	}

	public String getName() {
		return name;
	}

	public String getTime() {
		return time;
	}

	public String getMessage() {
		return message;
	}

	@Override
	public String toString() {
		return name + ", " +time + "\n" + message;
	}
	
	
	
}
