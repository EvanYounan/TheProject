import java.io.Serializable;
import java.util.Date;

public class Log implements Serializable {
	String status;
	Date creationTime;
	
	public Log(boolean add) {
		this.creationTime = new Date();
		
		if (add) {
			this.status = "Adding tag";
		} else {
			this.status = "Removing tag";
		}
	}
	
	public Log() {}
	
	public Date getCreationTime() {
		return this.creationTime;
	}
	
	public String getStatus() {
		return this.status;
	}
	
	public String toString() {
		return "Status: " + this.status + ", at: " + 
				this.creationTime.toString(); 
	}
} 
