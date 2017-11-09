package openwoz.rpi.dataobjects;

/**
 * Class which defines the data structure of a robot event
 * 
 * @author Amiraj Dhawan (amirajdhawan@gmail.com)
 */
public class RobotEvent {
	
	//Name of the event
	String name;
	String className;
	String methodName;
	long time;

	public RobotEvent(){
		
	}
	
	public String getName() {
		return name;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		RobotEvent that = (RobotEvent) o;

		if (time != that.time) return false;
		if (name != null ? !name.equals(that.name) : that.name != null) return false;
		if (className != null ? !className.equals(that.className) : that.className != null) return false;
		return methodName != null ? methodName.equals(that.methodName) : that.methodName == null;
	}

	@Override
	public int hashCode() {
		int result = name != null ? name.hashCode() : 0;
		result = 31 * result + (className != null ? className.hashCode() : 0);
		result = 31 * result + (methodName != null ? methodName.hashCode() : 0);
		result = 31 * result + (int) (time ^ (time >>> 32));
		return result;
	}

}